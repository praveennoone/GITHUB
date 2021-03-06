/*
 * DynamicInvoker.java
 */
package com.gavs.hishear.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.wsdl.Binding;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.encoding.Deserializer;
import javax.xml.rpc.encoding.DeserializerFactory;

import org.apache.axis.Constants;
import org.apache.axis.encoding.ser.ElementDeserializer;
import org.apache.axis.encoding.ser.ElementDeserializerFactory;
import org.apache.axis.encoding.ser.ElementSerializerFactory;
import org.apache.axis.encoding.ser.SimpleDeserializer;
import org.apache.axis.wsdl.gen.Parser;
import org.apache.axis.wsdl.symbolTable.BaseType;
import org.apache.axis.wsdl.symbolTable.BindingEntry;
import org.apache.axis.wsdl.symbolTable.Parameter;
import org.apache.axis.wsdl.symbolTable.Parameters;
import org.apache.axis.wsdl.symbolTable.ServiceEntry;
import org.apache.axis.wsdl.symbolTable.SymTabEntry;
import org.apache.axis.wsdl.symbolTable.SymbolTable;
import org.apache.axis.wsdl.symbolTable.TypeEntry;

/**
 * Class to make Web Services calls
 * 
 * @author rahjeshd
 * 
 */
public class DynamicInvoker {

	private static final int MILS_PER_SECOND = 1000;
	/** Field wsdlParser */
	private Parser wsdlParser = null;

	/**
	 * Constructor DynamicInvoker
	 * 
	 * @param wsdlURL
	 * 
	 * @throws Exception
	 */
	public DynamicInvoker(String wsdlURL) throws Exception {
		// Start by reading in the WSDL using Parser
		wsdlParser = new Parser();
		System.out.println("Reading WSDL document from '" + wsdlURL + "'");
		wsdlParser.run(wsdlURL);
	}

	/**
	 * Method invokeMethod
	 * 
	 * @param wsdlLocation
	 * @param operationName
	 * @param inputName
	 * @param outputName
	 * @param portName
	 * @param args
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public HashMap invokeMethod(String operationName, String portName,
			String[] args) throws Exception {
		String serviceNS = null;
		String serviceName = null;
		String operationQName = null;

		Service service = selectService(serviceNS, serviceName);
		Operation operation = null;
		org.apache.axis.client.Service dpf = new org.apache.axis.client.Service(
				wsdlParser, service.getQName());

		Vector inputs = new Vector();
		Port port = selectPort(service.getPorts(), portName);
		if (portName == null) {
			portName = port.getName();
		}
		Binding binding = port.getBinding();
		Call call = dpf.createCall(QName.valueOf(portName), QName
				.valueOf(operationName));
		((org.apache.axis.client.Call) call).setTimeout(new Integer(
				30 * MILS_PER_SECOND));
		((org.apache.axis.client.Call) call).setProperty(
				ElementDeserializer.DESERIALIZE_CURRENT_ELEMENT, Boolean.TRUE);

		// Output types and names
		Vector outNames = new Vector();

		// Input types and names
		Vector inNames = new Vector();
		Vector inTypes = new Vector();
		SymbolTable symbolTable = wsdlParser.getSymbolTable();
		BindingEntry bEntry = symbolTable.getBindingEntry(binding.getQName());
		Parameters parameters = null;
		Iterator i = bEntry.getParameters().keySet().iterator();

		while (i.hasNext()) {
			Operation o = (Operation) i.next();
			if (o.getName().equals(operationName)) {
				operation = o;
				parameters = (Parameters) bEntry.getParameters().get(o);
				break;
			}
		}
		if ((operation == null) || (parameters == null)) {
			throw new RuntimeException(operationName + " was not found.");
		}

		// loop over paramters and set up in/out params
		for (int j = 0; j < parameters.list.size(); ++j) {
			Parameter p = (Parameter) parameters.list.get(j);

			if (p.getMode() == 1) { // IN
				inNames.add(p.getQName().getLocalPart());
				inTypes.add(p);
			} else if (p.getMode() == 2) { // OUT
				outNames.add(p.getQName().getLocalPart());
			} else if (p.getMode() == 3) { // INOUT
				inNames.add(p.getQName().getLocalPart());
				inTypes.add(p);
				outNames.add(p.getQName().getLocalPart());
			}
		}

		// set output type
		if (parameters.returnParam != null) {

			if (!parameters.returnParam.getType().isBaseType()) {
				((org.apache.axis.client.Call) call).registerTypeMapping(
						org.w3c.dom.Element.class, parameters.returnParam
								.getType().getQName(),
						new ElementSerializerFactory(),
						new ElementDeserializerFactory());
			}

			// Get the QName for the return Type
			QName returnType = org.apache.axis.wsdl.toJava.Utils
					.getXSIType(parameters.returnParam);
			QName returnQName = parameters.returnParam.getQName();

			outNames.add(returnQName.getLocalPart());
		}

		if (inNames.size() != args.length) {
			throw new RuntimeException("Need " + inNames.size()
					+ " arguments!!!");
		}
		for (int pos = 0; pos < inNames.size(); ++pos) {
			String arg = args[pos];
			Parameter p = (Parameter) inTypes.get(pos);
			inputs
					.add(getParamData((org.apache.axis.client.Call) call, p,
							arg));
		}

		for (int j = 0; j < inputs.size(); j++) {
			System.out.println(inNames.get(j) + "=" + inputs.get(j));
		}
		Object ret = call.invoke(inputs.toArray());
		Map outputs = call.getOutputParams();
		HashMap map = new HashMap();

		for (int pos = 0; pos < outNames.size(); ++pos) {
			String name = (String) outNames.get(pos);
			Object value = outputs.get(name);

			if ((value == null) && (pos == 0)) {
				map.put(name, ret);
			} else {
				map.put(name, value);
			}
		}
		return map;
	}

	/**
	 * Method getParamData
	 * 
	 * @param c
	 * @param arg
	 */
	private Object getParamData(org.apache.axis.client.Call c, Parameter p,
			String arg) throws Exception {
		// Get the QName representing the parameter type
		QName paramType = org.apache.axis.wsdl.toJava.Utils.getXSIType(p);

		TypeEntry type = p.getType();
		if (type instanceof BaseType && ((BaseType) type).isBaseType()) {
			DeserializerFactory factory = c.getTypeMapping().getDeserializer(
					paramType);
			Deserializer deserializer = factory
					.getDeserializerAs(Constants.AXIS_SAX);
			if (deserializer instanceof SimpleDeserializer) {
				return ((SimpleDeserializer) deserializer).makeValue(arg);
			}
		}
		throw new RuntimeException("not know how to convert '" + arg
				+ "' into " + c);
	}

	/**
	 * Method selectService
	 * 
	 * @param def
	 * @param serviceNS
	 * @param serviceName
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public Service selectService(String serviceNS, String serviceName)
			throws Exception {
		QName serviceQName = null;
		if (serviceNS != null && serviceName != null) {
			serviceQName = new QName(serviceNS, serviceName);
		}
		ServiceEntry serviceEntry = (ServiceEntry) getSymTabEntry(serviceQName,
				ServiceEntry.class);
		return serviceEntry.getService();
	}

	/**
	 * Method getSymTabEntry
	 * 
	 * @param qname
	 * @param cls
	 * 
	 * @return
	 */
	public SymTabEntry getSymTabEntry(QName qname, Class cls) {
		HashMap map = wsdlParser.getSymbolTable().getHashMap();
		Iterator iterator = map.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			QName key = (QName) entry.getKey();
			Vector v = (Vector) entry.getValue();

			if ((qname == null) || qname.equals(qname)) {
				for (int i = 0; i < v.size(); ++i) {
					SymTabEntry symTabEntry = (SymTabEntry) v.elementAt(i);

					if (cls.isInstance(symTabEntry)) {
						return symTabEntry;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Method selectPort
	 * 
	 * @param ports
	 * @param portName
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public Port selectPort(Map ports, String portName) throws Exception {
		Iterator valueIterator = ports.keySet().iterator();
		while (valueIterator.hasNext()) {
			String name = (String) valueIterator.next();

			if ((portName == null) || (portName.length() == 0)) {
				Port port = (Port) ports.get(name);
				List list = port.getExtensibilityElements();

				for (int i = 0; (list != null) && (i < list.size()); i++) {
					Object obj = list.get(i);
					if (obj instanceof SOAPAddress) {
						return port;
					}
				}
			} else if ((name != null) && name.equals(portName)) {
				return (Port) ports.get(name);
			}
		}
		return null;
	}
}
