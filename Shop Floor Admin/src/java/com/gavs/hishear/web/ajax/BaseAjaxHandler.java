/**
 * 
 */
package com.gavs.hishear.web.ajax;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.util.HtmlUtils;
import org.springmodules.xt.ajax.AbstractAjaxHandler;
import org.springmodules.xt.ajax.AjaxEvent;
import org.springmodules.xt.ajax.AjaxResponse;
import org.springmodules.xt.ajax.AjaxResponseImpl;
import org.springmodules.xt.ajax.AjaxSubmitEvent;
import org.springmodules.xt.ajax.action.ReplaceContentAction;
import org.springmodules.xt.ajax.component.Container;
import org.springmodules.xt.ajax.component.Image;
import org.springmodules.xt.ajax.component.SimpleText;
import org.springmodules.xt.ajax.support.EventHandlingException;
import org.springmodules.xt.ajax.support.UnsupportedEventException;

/**
 * @author ramananm
 * 
 */
public class BaseAjaxHandler extends AbstractAjaxHandler {
	private Logger logger = Logger.getLogger("BaseAjaxHandler");

	/**
	 * Dynamic template method for handling ajax requests depending on the event
	 * id.
	 * 
	 * @see AjaxHandler # handle(AjaxEvent)
	 */
	@Override
	public AjaxResponse handle(AjaxEvent event) {
		if (event == null || event.getEventId() == null) {
			logger.error("Event and event id cannot be null.");
			throw new IllegalArgumentException(
					"Event and event id cannot be null.");
		}

		String id = event.getEventId();
		AjaxResponse response = null;
		try {

			Method m = this.getMatchingMethod(event);
			if (m != null) {
				logger.info(new StringBuilder("Invoking method: ").append(m));

				if (event instanceof AjaxSubmitEvent) {
					AjaxSubmitEvent submitEvent = (AjaxSubmitEvent) event;
					Map modal = submitEvent.getModel();
					if (hasErrors(submitEvent.getModel())) {
						response = new AjaxResponseImpl();
						displayErrors(response, modal);
					} else {
						response = (AjaxResponse) m.invoke(this,
								new Object[] { event });
					}
				} else {
					response = (AjaxResponse) m.invoke(this,
							new Object[] { event });
				}
			} else {
				logger.error("You need to call the supports() method first!");
				throw new UnsupportedEventException(
						"You need to call the supports() method first!");
			}
		} catch (IllegalAccessException ex) {
			logger.error(ex.getMessage(), ex);
			logger.error("Cannot handle the given event with id: " + id);
			throw new UnsupportedEventException(
					"Cannot handle the given event with id: " + id, ex);
		} catch (InvocationTargetException ex) {
			logger.error(ex.getMessage(), ex);
			logger.error("Exception while handling the given event with id: "
					+ id);
			throw new EventHandlingException(
					"Exception while handling the given event with id: " + id,
					ex);
		}

		return response;
	}

	private Method getMatchingMethod(AjaxEvent event) {
		Class eventType = this.getEventType(event);
		Method[] methods = this.getClass().getMethods();
		Method ret = null;
		for (Method method : methods) {
			if (method.getName().equals(event.getEventId())
					&& method.getParameterTypes()[0]
							.isAssignableFrom(eventType)) {
				ret = method;
				break;
			}
		}
		return ret;
	}

	private Class getEventType(AjaxEvent event) {
		Class[] interfaces = event.getClass().getInterfaces();
		Class ret = event.getClass();
		for (Class intf : interfaces) {
			if (AjaxEvent.class.isAssignableFrom(intf)) {
				ret = intf;
				break;
			}
		}
		return ret;
	}

	/**
	 * Return Model contains Error or not
	 * 
	 * @param map
	 * @return
	 */
	protected boolean hasErrors(Map map) {
		return map != null && map.containsKey("ERRORS");
	}

	protected void displayErrors(AjaxResponse response, Map model) {
		if (model != null && model.containsKey("ERRORS")) {
			Container errorDetailsDiv = new Container(Container.Type.DIV);
			StringBuffer errorString = new StringBuffer(
					"<font color=\"red\" size=\"2px\">");
			List errors = (ArrayList) model.get("ERRORS");
			if (errors != null && errors.size() > 0) {
				for (int j = 0; j < errors.size(); j++) {
					errorString.append(errors.get(j));
				}
				errorString.append(" </font>");
				errorDetailsDiv.addComponent(new SimpleText(errorString
						.toString()));
				if (model != null && model.containsKey("SOURCE_EXCEPTION")) {
					Exception exception = (Exception) model
							.get("SOURCE_EXCEPTION");
					if (exception != null) {
						Map<String, Object> options = new HashMap<String, Object>();
						options.put("errorDetail", exception.toString());
						Image imageErrorDetails = new Image(
								"static/images/icons/ErrorDetails.gif", "Error");
						imageErrorDetails.addAttribute("onClick",
								"javascript:showErrorDetails();");
						imageErrorDetails.addAttribute("style",
								"cursor:pointer");
						errorDetailsDiv.addComponent(imageErrorDetails);
					}

				}
			}

			ReplaceContentAction action = new ReplaceContentAction(
					"statusMessage", errorDetailsDiv);

			response.addAction(action);
		}
	}

	public String validateDisplayField(String displayField) {
		if (displayField == null || displayField.trim().equals("")) {
			return " ";
		}
		return HtmlUtils.htmlEscape(displayField);
	}
}
