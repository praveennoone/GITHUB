package com.gavs.hishear.web.ajax;

import javax.servlet.http.HttpServletRequest;

import org.springmodules.xt.ajax.AbstractAjaxHandler;
import org.springmodules.xt.ajax.AjaxActionEvent;
import org.springmodules.xt.ajax.AjaxResponse;
import org.springmodules.xt.ajax.AjaxResponseImpl;
import org.springmodules.xt.ajax.action.ReplaceContentAction;
import org.springmodules.xt.ajax.component.SimpleText;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.DeptWCAssetReportDAO;

public class QtyCaptureRequiredActionsHandler extends AbstractAjaxHandler {
	private DeptWCAssetReportDAO dao;
	private UserContext userContext;

	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}

	public AjaxResponse changeQtyCaptureRequired(AjaxActionEvent event) {
		HttpServletRequest request = event.getHttpRequest();
		String deptNumber = request.getParameter("deptNumber");
		String asset = request.getParameter("asset");
		String qtyCaptureRequired = request.getParameter("qtyCaptureRequired");

		String query = (String) userContext.getQueries().get("SHOP_083");

		int updatedRows = dao.updateQtyCaptureRequiredFlag(deptNumber, asset,
				qtyCaptureRequired, query);

		AjaxResponse response = new AjaxResponseImpl();

		String strMessage = "";

		if ("Y".equals(qtyCaptureRequired)) {
			strMessage = "Quantity Required Flag Set For Asset Number ";
		} else {
			strMessage = "Quantity Required Flag Cleared For Asset Number ";
		}
		SimpleText message = new SimpleText(strMessage + asset);
		response.addAction(new ReplaceContentAction("message", message));

		// TODO audit log code should come here
		System.out.println("Log " + userContext.getUserName() + " Into DB");

		return response;
	}

	public void setDao(DeptWCAssetReportDAO dao) {
		this.dao = dao;
	}
}
