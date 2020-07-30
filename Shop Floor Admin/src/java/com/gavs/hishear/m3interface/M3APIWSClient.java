// File:         M3APIWSClient.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.m3interface;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.OperationClient;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.MessageContext;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gavs.hishear.EventLog.LogTools;
import com.gavs.hishear.constant.M3Parameter;
import com.gavs.hishear.m3interface.dto.M3APIParameter;
import com.gavs.hishear.m3interface.dto.M3Entity;
import com.gavs.hishear.m3interface.dto.MVXDTAMI;
import com.gavs.hishear.m3interface.dto.POTransaction;
import com.gavs.hishear.m3interface.dto.Program;
import com.gavs.hishear.util.Util;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.CostCenter;
import com.gavs.hishear.web.domain.ErrorLog;
import com.gavs.hishear.web.domain.Facility;
import com.gavs.hishear.web.domain.Item;
import com.gavs.hishear.web.domain.Sequence;
import com.gavs.hishear.web.domain.WorkCenter;

/**
 * The Class M3APIWSClient.
 * 
 * 
 */
public class M3APIWSClient {

	/** The application property bean. */
	private ApplicationPropertyBean applicationPropertyBean;

	// /** The NAMESPACE. */
	// private static String NAMESPACE = "http://your.company.net";
	//
	// /** The Constant MWS_HEADER_NS_PREFIX. */
	// public static final String MWS_HEADER_NS_PREFIX = "mwsh";
	//
	// /** The Constant MWS_HEADER_NS_URI. */
	// public static final String MWS_HEADER_NS_URI =
	// "http://mws.intentia.net/mws2";

	private static final Logger logger = Logger.getLogger(M3APIWSClient.class);

	/**
	 * Gets the application property bean.
	 * 
	 * @return the applicationPropertyBean
	 */
	public ApplicationPropertyBean getApplicationPropertyBean() {
		return applicationPropertyBean;
	}

	/**
	 * Sets the application property bean.
	 * 
	 * @param applicationPropertyBean
	 *            the applicationPropertyBean to set
	 */
	public void setApplicationPropertyBean(
			ApplicationPropertyBean applicationPropertyBean) {
		this.applicationPropertyBean = applicationPropertyBean;
	}

	/**
	 * Call service.
	 * 
	 * @param object
	 *            the object
	 * @return the object
	 * @throws Exception
	 *             the exception
	 */
	public Object callService(Object object) throws Exception {
		if (object instanceof M3APIParameter) {
			M3APIParameter m3APIParameter = (M3APIParameter) object;
			return callM3API(m3APIParameter);
		} else if (object instanceof Program) {
			Program program = (Program) object;
			callDisplayProgram(program);
		}
		return null;
	}

	/**
	 * Call display program.
	 * 
	 * @param program
	 *            the program
	 * @throws Exception
	 *             the exception
	 */
	private void callDisplayProgram(Program program) throws Exception {
		HashMap<String, String> inputData = program.getInputData();

		ServiceClient serviceClient = new ServiceClient();
		// Fill options
		serviceClient.setOptions(initSOAPOptions(program.getWebServiceName()));

		// Create & add the SOAP Header
		SOAPFactory factory = OMAbstractFactory.getSOAP11Factory();
		serviceClient.addHeader(createSOAPEnveloperHeader(factory));

		// create the request payload
		OMNamespace omNs;
		// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys -
		// 23 June 2011
		omNs = factory.createOMNamespace(
				applicationPropertyBean.getM3Namespace()
						+ "/"
						// End WO# 27639 - Moving Static links to dynamic -
						// Pinky - Infosys - 23 June 2011
						+ program.getWebServiceName().trim() + "/"
						+ program.getFunctionName().trim(), "LWS");

		OMElement request = factory.createOMElement(program.getFunctionName()
				.trim(), omNs);

		final OMAttribute omAttribute = factory.createOMAttribute("maxRecords",
				null, null);
		omAttribute.setAttributeValue("0");
		request.addAttribute(omAttribute);

		addData(factory, request, program);

		// Invoke blocking
		OMElement response;
		try {
			response = callM3(program, request, serviceClient);
		} catch (AxisFault e) {
			String errorMsg = M3ErrorLogger.getLastM3Error();
			throw new Exception(errorMsg);

		}

	}

	/**
	 * Adds the data.
	 * 
	 * @param factory
	 *            the factory
	 * @param request
	 *            the request
	 * @param program
	 *            the program
	 * @throws AxisFault
	 *             the axis fault
	 */
	@SuppressWarnings("unchecked")
	private void addData(OMFactory factory, OMElement request, Program program)
			throws AxisFault {
		HashMap inputData = program.getInputData();
		OMElement body = factory.createOMElement(program.getM3Function(),
				request.getNamespace(), request);

		// add the input
		if (inputData != null) {
			Set keys = inputData.keySet();
			for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				OMElement omElement = factory.createOMElement(key,
						body.getNamespace(), body);
				omElement.setText((String) inputData.get(key));
			}
		}

		if (program.getSubPrograms() != null
				&& program.getSubPrograms().size() > 0) {
			for (Iterator iterator = program.getSubPrograms().iterator(); iterator
					.hasNext();) {
				Program subProgram = (Program) iterator.next();
				addData(factory, body, subProgram);
			}
		}
	}

	/**
	 * Call m3 api.
	 * 
	 * @param parameter
	 *            the parameter
	 * @return the object
	 * @throws Exception
	 *             the exception
	 */
	public Object callM3API(M3APIParameter parameter) throws Exception {
		HashMap<String, String> inputData = parameter.getInputData();

		ServiceClient serviceClient = new ServiceClient();
		// Fill options
		serviceClient
				.setOptions(initSOAPOptions(parameter.getWebServiceName()));

		// Create & add the SOAP Header
		SOAPFactory factory = OMAbstractFactory.getSOAP11Factory();
		serviceClient.addHeader(createSOAPEnveloperHeader(factory));

		// create the request payload
		OMNamespace omNs = factory.createOMNamespace(
				applicationPropertyBean.getM3Namespace() + "/"
						+ parameter.getWebServiceName() + "/"
						+ parameter.getFunctionName(), "");
		OMElement request = factory.createOMElement(
				parameter.getFunctionName(), omNs);

		final OMAttribute omAttribute = factory.createOMAttribute("maxRecords",
				null, null);
		omAttribute.setAttributeValue("0");
		request.addAttribute(omAttribute);

		OMElement body = factory.createOMElement(parameter.getInputItem(),
				request.getNamespace(), request);

		// add the input
		Set keys = inputData.keySet();
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			OMElement itemNumber = factory.createOMElement(key,
					body.getNamespace(), body);
			itemNumber.setText((String) inputData.get(key));
		}

		// Invoke blocking
		logger.info("Request:   " + parameter.getWebServiceName() + ":"
				+ parameter.getFunctionName() + " - "
				+ new SimpleDateFormat("hh:mm:ss.SSS").format(new Date())
				+ " : " + request);

		System.out.println("Request: " + parameter.getWebServiceName() + ":"
				+ parameter.getFunctionName() + " - "
				+ new SimpleDateFormat("hh:mm:ss.SSS").format(new Date())
				+ " : " + request);

		OMElement response;
		try {
			System.out.println("before responce");
			response = callM3(parameter, request, serviceClient);
		} catch (AxisFault e) {
			LogTools.windowsEventLogError(parameter.getWebServiceName());
			LogTools.windowsEventLogError(e.getMessage());
			String errorMsg = M3ErrorLogger.getLastM3Error();
			throw new Exception(errorMsg);

		}

		logger.info("Response:   " + parameter.getWebServiceName() + ":"
				+ parameter.getFunctionName() + " - "
				+ new SimpleDateFormat("hh:mm:ss.SSS").format(new Date())
				+ " : " + response);

		System.out.println("Response: " + parameter.getWebServiceName() + ":"
				+ parameter.getFunctionName() + " - "
				+ new SimpleDateFormat("hh:mm:ss.SSS").format(new Date())
				+ " : " + response);
		return parseResult(response, parameter);
	}

	/**
	 * Parses the result.
	 * 
	 * @param result
	 *            the result
	 * @param parameter
	 *            the parameter
	 * @return the object
	 */
	private Object parseResult(OMElement result, M3APIParameter parameter) {

		if ("getDetailsForMONumber".equals(parameter.getFunctionName())) {
			MVXDTAMI mvxdtami = new MVXDTAMI();
			Iterator it0 = result.getChildElements();
			while (it0.hasNext()) {
				OMElement el0 = (OMElement) it0.next();
				String locName = el0.getLocalName();
				if (locName.equals(parameter.getOutputItem())) {
					Iterator it1 = el0.getChildElements();

					while (it1.hasNext()) {
						OMElement el1 = (OMElement) it1.next();
						M3Parameter m3Parameter = M3Parameter.fromString(el1
								.getLocalName());
						switch (m3Parameter) {
						case FACI:
							mvxdtami.setFacility(el1.getText());
							break;
						case PRNO:
							mvxdtami.setProductNumber(el1.getText());
							break;

						default:
							break;
						}
					}
				}
			}

			return mvxdtami;
		} else if ("selOperations".equals(parameter.getFunctionName())) {

			List output = new ArrayList();
			Iterator it0 = result.getChildElements();
			while (it0.hasNext()) {
				OMElement el0 = (OMElement) it0.next();
				String locName = el0.getLocalName();
				if (locName.equals(parameter.getOutputItem())) {
					Iterator it1 = el0.getChildElements();
					Sequence activity = new Sequence();
					float runTime = 0;
					float setUpTime = 0;

					while (it1.hasNext()) {
						OMElement el1 = (OMElement) it1.next();
						M3Parameter m3Parameter = M3Parameter.fromString(el1
								.getLocalName());
						switch (m3Parameter) {
						case OPNO:
							activity.setSequence(el1.getText());
							break;
						case PLGR:
							activity.setWorkCenterCode(el1.getText());
							break;
						case ORQT:
							activity.setOrderQty((int) Float.parseFloat(el1
									.getText()));
							break;
						case WOST:
							activity.setStatus(el1.getText());
							break;
						case PRNO:
							activity.setPartNumber(el1.getText());
							break;
						case ITDS:
							activity.setPartDesc(el1.getText());
							break;
						case MFNO:
							activity.setMoNumber(el1.getText());
							break;
						case FACI:
							activity.setFacility(el1.getText());
							break;
						case OPDS:
							activity.setSequenceDescription(el1.getText());
							break;
						case FIDT:
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyyMMdd");
							try {
								activity.setRequiredDate(sdf.parse(el1
										.getText().trim()));
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case REAR:
							activity.setPlanningArea(el1.getText().trim());
							break;
						case SETI:
							setUpTime = Float.parseFloat(el1.getText());

							if (setUpTime > 0) {
								activity.setSetupTime(setUpTime);
							}
							break;
						case PITI:
							runTime = Float.parseFloat(el1.getText());

							if (runTime > 0) {
								activity.setRunTime(runTime);
							}
							break;
						case NNQT:
							activity.setCompletedQuantity((int) Float
									.parseFloat(el1.getText()));
							break;
						case MAQT:
							activity.setQtyCompleted((int) Float.parseFloat(el1
									.getText()));
							break;
						case CTCD:
							if (StringUtils.isNotBlank(el1.getText())) {
								activity.setPriceTimeQty(Double.parseDouble(el1
										.getText().trim()));
							}
							break;
						default:
							break;
						}
					}
					output.add(activity);
				}
			}
			return output;
		} else if ("getOperation".equals(parameter.getFunctionName())) {
			Sequence activity = new Sequence();
			Iterator it0 = result.getChildElements();
			ArrayList sequenceList = new ArrayList();
			while (it0.hasNext()) {
				OMElement el0 = (OMElement) it0.next();
				Sequence sequence = new Sequence();
				String locName = el0.getLocalName();
				if (locName.equals(parameter.getOutputItem())) {
					Iterator it1 = el0.getChildElements();
					while (it1.hasNext()) {
						OMElement el1 = (OMElement) it1.next();
						M3Parameter m3Parameter = M3Parameter.fromString(el1
								.getLocalName());
						switch (m3Parameter) {
						case PRNO:
							sequence.setPartNumber(el1.getText());
							break;
						case MAQ1:
							sequence.setManufacturedQty(Double.parseDouble(el1
									.getText()));
							break;
						case ITDS:
							sequence.setPartDesc(el1.getText());
							break;
						case PLG1:
							sequence.setWorkCenterCode(el1.getText());
							break;
						case WOS1:
							sequence.setStatus(el1.getText());
							break;
						case PLNM:
							sequence.setWorkCenterDesc(el1.getText());
							break;
						case UMA2:
							sequence.setUsedLabourRunTime(el1.getText());
							break;
						case CTC1:
							String priceTimeQty = el1.getText();
							if (StringUtils.isNotBlank(priceTimeQty)) {
								sequence.setPriceTimeQty(Double
										.parseDouble(priceTimeQty));
							}
							break;
						default:
							break;
						}
					}
					sequenceList.add(sequence);
				}
			}
			return sequenceList;
		} else if ("rptOperation".equals(parameter.getFunctionName())) {
			return null;
		} else if ("getItemBasic".equals(parameter.getFunctionName())) {
			Iterator it0 = result.getChildElements();
			ArrayList itemList = new ArrayList();
			while (it0.hasNext()) {
				OMElement el0 = (OMElement) it0.next();
				Item itm = new Item();
				String locName = el0.getLocalName();
				if (locName.equals(parameter.getOutputItem())) {
					Iterator it1 = el0.getChildElements();
					while (it1.hasNext()) {
						OMElement el1 = (OMElement) it1.next();
						M3Parameter m3Parameter = M3Parameter.fromString(el1
								.getText());
						switch (m3Parameter) {
						case ITNO:
							itm.setItemNumber(el1.getText());
							break;
						case ITDS:
							itm.setItemDescription(el1.getText());
							break;
						default:
							break;
						}
					}
					itemList.add(itm);
				}
			}
			return itemList;
		} else if ("getLSTMPDWCTX1".equals(parameter.getFunctionName())) {

			Iterator it0 = result.getChildElements();
			HashMap costCenters = new HashMap();
			while (it0.hasNext()) {
				OMElement el0 = (OMElement) it0.next();
				CostCenter costCenter = new CostCenter();
				String locName = el0.getLocalName();
				if (locName.equals(parameter.getOutputItem())) {
					Iterator it1 = el0.getChildElements();

					while (it1.hasNext()) {
						OMElement el1 = (OMElement) it1.next();
						M3Parameter m3Parameter = M3Parameter.fromString(el1
								.getLocalName());
						switch (m3Parameter) {
						case COCE:
							costCenter.setCostCenterCode(el1.getText());
							break;
						case PLNM:
							costCenter.setCostCenterDesc(el1.getText());
							break;
						default:
							break;
						}
					}

					costCenters.put(costCenter.getCostCenterCode(), costCenter);
				}
			}
			return new ArrayList(costCenters.values());
		} else if ("getLSEMPDWCTX1".equals(parameter.getFunctionName())) {

			Iterator it0 = result.getChildElements();
			ArrayList workCenters = new ArrayList();

			while (it0.hasNext()) {
				WorkCenter workCenter = new WorkCenter();
				OMElement el0 = (OMElement) it0.next();
				String locName = el0.getLocalName();
				if (locName.equals(parameter.getOutputItem())) {
					Iterator it1 = el0.getChildElements();
					while (it1.hasNext()) {
						OMElement el1 = (OMElement) it1.next();
						M3Parameter m3Parameter = M3Parameter.fromString(el1
								.getLocalName());
						switch (m3Parameter) {
						case PLGR:
							workCenter.setWorkcenterCode(el1.getText());
							break;
						case PLNM:
							workCenter.setWorkcenterDesc(el1.getText());
							break;
						default:
							break;
						}
					}
					workCenters.add(workCenter);
				}
			}
			return workCenters;
		} else if ("listFacility".equals(parameter.getFunctionName())) {
			ArrayList<Facility> facilities = new ArrayList<Facility>();
			Iterator it0 = result.getChildElements();
			while (it0.hasNext()) {
				Facility facility = new Facility();
				OMElement el0 = (OMElement) it0.next();
				String locName = el0.getLocalName();
				if (locName.equals(parameter.getOutputItem())) {
					Iterator it1 = el0.getChildElements();
					while (it1.hasNext()) {
						OMElement el1 = (OMElement) it1.next();
						M3Parameter m3Parameter = M3Parameter.fromString(el1
								.getLocalName());
						switch (m3Parameter) {
						case FACI:
							facility.setFacilityId(Util.decode(el1.getText()));
							break;
						case FACN:
							facility.setFacilityName(Util.decode(el1.getText()));
							break;
						case DIVI:
							facility.setDivision(Util.decode(el1.getText()));
							break;
						default:
							break;
						}
					}
				}
				facilities.add(facility);
			}
			return facilities;
		} else if ("getDEPTByPLGR".equals(parameter.getFunctionName())) {
			Iterator it0 = result.getChildElements();
			WorkCenter workCenter = new WorkCenter();
			while (it0.hasNext()) {
				OMElement el0 = (OMElement) it0.next();
				String locName = el0.getLocalName();
				if (locName.equals(parameter.getOutputItem())) {
					Iterator it1 = el0.getChildElements();

					while (it1.hasNext()) {
						OMElement el1 = (OMElement) it1.next();
						M3Parameter m3Parameter = M3Parameter.fromString(el1
								.getLocalName());
						switch (m3Parameter) {
						case DEPT:
							String department = el1.getText();
							if (StringUtils.isNotBlank(department)) {
								workCenter.setDepartment(department.trim());
							}
							break;
						case PCAP:
							String capacity = el1.getText();
							if (StringUtils.isNotBlank(capacity)) {
								workCenter.setCapacity(Integer
										.parseInt(capacity.trim()));
							}
							break;
						default:
							break;
						}
					}
				}
			}

			return workCenter;
		} else if ("selMaterials".equals(parameter.getFunctionName())) {
			List<Sequence> facilities = new ArrayList<Sequence>();
			Iterator it0 = result.getChildElements();
			while (it0.hasNext()) {
				Sequence sequence = new Sequence();
				OMElement el0 = (OMElement) it0.next();
				String locName = el0.getLocalName();
				if (locName.equals(parameter.getOutputItem())) {
					Iterator it1 = el0.getChildElements();
					while (it1.hasNext()) {
						OMElement el1 = (OMElement) it1.next();
						if (M3Parameter.OPNO.getValue().equals(
								el1.getLocalName())) {
							sequence.setSequence(el1.getText());
						}
					}
				}
				facilities.add(sequence);
			}
			return facilities;
		} else if ("lstTransByType".equals(parameter.getFunctionName())) {
			ArrayList<ErrorLog> m3Transactions = new ArrayList<ErrorLog>();
			Iterator it0 = result.getChildElements();
			String[] displayColumnFields = new String[] {
					M3Parameter.WHLO.getValue(), M3Parameter.ITNO.getValue(),
					M3Parameter.RIDN.getValue(), M3Parameter.RIDL.getValue(),
					M3Parameter.TTYP.getValue(), M3Parameter.RGDT.getValue(),
					M3Parameter.RGTM.getValue(), M3Parameter.TRDT.getValue(),
					M3Parameter.TRTM.getValue(), M3Parameter.TRQT.getValue(),
					M3Parameter.BANO.getValue(), M3Parameter.REPN.getValue(),
					M3Parameter.RESP.getValue(), M3Parameter.WHSL.getValue(),
					M3Parameter.RFTX.getValue(), M3Parameter.RIDI.getValue(),
					M3Parameter.FACI.getValue() };
			while (it0.hasNext()) {
				OMElement el0 = (OMElement) it0.next();
				String locName = el0.getLocalName();
				if (locName.equals(parameter.getOutputItem())) {
					ErrorLog errorLog = new ErrorLog();
					String columnHeaders = null;
					String columnValues = null;
					Iterator it1 = el0.getChildElements();
					while (it1.hasNext()) {
						OMElement el1 = (OMElement) it1.next();
						if (ArrayUtils.contains(displayColumnFields,
								el1.getLocalName())) {
							if (columnHeaders != null) {
								columnHeaders = columnHeaders + ","
										+ el1.getLocalName();
							} else {
								columnHeaders = el1.getLocalName();
							}

							if (columnValues != null) {
								columnValues = columnValues + ","
										+ el1.getText();
							} else {
								columnValues = el1.getText();
							}
						}
					}
					errorLog.setColumnHeaders(columnHeaders);
					errorLog.setColumnValues(columnValues);
					if (columnHeaders != null && columnValues != null) {
						m3Transactions.add(errorLog);
					}
				}
			}
			return m3Transactions;
		} else if ("getMPLinex3".equals(parameter.getFunctionName())) {
			MVXDTAMI mvxdtami = new MVXDTAMI();
			Iterator it0 = result.getChildElements();
			while (it0.hasNext()) {
				OMElement el0 = (OMElement) it0.next();
				String locName = el0.getLocalName();
				if (locName.equals(parameter.getOutputItem())) {
					Iterator it1 = el0.getChildElements();

					while (it1.hasNext()) {
						OMElement el1 = (OMElement) it1.next();
						M3Parameter m3Parameter = M3Parameter.fromString(el1
								.getLocalName());
						switch (m3Parameter) {
						case PUNO:
							mvxdtami.setPurchaseOrderNumber(el1.getText());
							break;
						case PNLI:
							mvxdtami.setPurchaseOrderLineNo(el1.getText());
							break;
						case PNLS:
							mvxdtami.setPurchaseOrderSubLineNo(el1.getText());
							break;
						case SUNO:
							mvxdtami.setSupplierNumber(el1.getText());
							break;
						case WHLO:
							mvxdtami.setWareHouse(el1.getText());
							break;
						case ITNO:
							mvxdtami.setProductNumber(el1.getText());
							break;

						default:
							break;
						}
					}
				}
			}

			return mvxdtami;
		} else if ("getLSEMPLINDX1".equals(parameter.getFunctionName())) {
			List<POTransaction> poTransactions = new ArrayList<POTransaction>();
			Iterator it0 = result.getChildElements();
			while (it0.hasNext()) {
				POTransaction poTransaction = new POTransaction();
				OMElement el0 = (OMElement) it0.next();
				String locName = el0.getLocalName();
				if (locName.equals(parameter.getOutputItem())) {
					Iterator it1 = el0.getChildElements();
					while (it1.hasNext()) {
						OMElement el1 = (OMElement) it1.next();
						M3Parameter m3Parameter = M3Parameter.fromString(el1
								.getLocalName());
						switch (m3Parameter) {
						case PUNO:
							poTransaction.setPoNumber(el1.getText());
							break;
						case REPN:
							poTransaction.setReceivingNumber(el1.getText());
							break;
						case RPQA:
							int transactionQuantity = 0;
							if (StringUtils.isNotBlank(el1.getText())) {
								transactionQuantity = Double.valueOf(
										el1.getText()).intValue();
							}
							poTransaction
									.setTransactionQuantity(transactionQuantity);
							break;
						case PNLI:
							poTransaction.setPoLineNumber(el1.getText());
							break;
						case PNLS:
							poTransaction.setSubLineNumber(el1.getText());
							break;
						case PNLX:
							poTransaction.setPoLineSuffix(el1.getText());
							break;
						case WHLO:
							poTransaction.setWarehouse(el1.getText());
							break;
						case PUOS:
							if (StringUtils.isNotBlank(el1.getText())) {
								poTransaction.setStatus(Integer.parseInt(el1
										.getText()));
							}
							break;
						case BANO:
							poTransaction.setLotNumber(el1.getText());
							break;

						default:
							break;
						}
					}
				}
				poTransactions.add(poTransaction);
			}
			return poTransactions;
		} /*else if ("LstByCostCenter".equals(parameter.getFunctionName())) {

			Iterator it0 = result.getChildElements();
			HashMap costCenters = new HashMap();
			while (it0.hasNext()) {
				OMElement el0 = (OMElement) it0.next();
				CostCenter costCenter = new CostCenter();
				String locName = el0.getLocalName();
				if (locName.equals(parameter.getOutputItem())) {
					Iterator it1 = el0.getChildElements();

					while (it1.hasNext()) {
						OMElement el1 = (OMElement) it1.next();
						M3Parameter m3Parameter = M3Parameter.fromString(el1
								.getLocalName());
						switch (m3Parameter) {
						case COCE:
							costCenter.setCostCenterCode(el1.getText());
							break;
						case TX40:
							costCenter.setCostCenterDesc(el1.getText());
							break;
						default:
							break;
						}
					}

					costCenters.put(costCenter.getCostCenterCode(), costCenter);
				}
			}
			return new ArrayList(costCenters.values());

		}*/
		
		
		else if("LstByCostCenter".equals(parameter.getFunctionName())){
			System.out.println("LstByCostCentercalled");
			Iterator it0 = result.getChildElements();
			HashMap costCenters = new HashMap();
			while (it0.hasNext()) {
				OMElement el0 = (OMElement) it0.next();
				CostCenter costCenter = new CostCenter();
				String locName = el0.getLocalName();
				if (locName.equals(parameter.getOutputItem())) {
					Iterator it1 = el0.getChildElements();

					while (it1.hasNext()) {
						OMElement el1 = (OMElement) it1.next();
						M3Parameter m3Parameter = M3Parameter.fromString(el1
								.getLocalName());
						switch (m3Parameter) {
						case CostCenter:
							costCenter.setCostCenterCode(el1.getText());
							break;
						case Description:
							costCenter.setCostCenterDesc(el1.getText());
							break;
						default:
							break;
						}
					}
					costCenters.put(costCenter.getCostCenterCode(), costCenter);					
				}
			}
			return new ArrayList(costCenters.values());
			
		}
		
		return null;
	}

	/**
	 * Debugging method used to serialise the payload.
	 * 
	 * @param payload
	 *            the payload
	 */
	private void showPayload(OMElement payload) {
		try {
			StringWriter writer = new StringWriter();
			payload.serialize(XMLOutputFactory.newInstance()
					.createXMLStreamWriter(writer));
			writer.flush();

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	private void logInvalidM3ParameterError(String functionName) {
		StringBuilder errorMessage = new StringBuilder(
				"Invalid M3 Parameter in ");
		errorMessage.append(functionName).append(" M3 API");
		logger.error(errorMessage.toString());
	}

	private <T extends M3Entity> OMElement callM3(T parameter,
			OMElement request, ServiceClient serviceClient) throws AxisFault {
		try {
			return getM3Response(request, serviceClient);
		} catch (AxisFault e) {
			M3ErrorLogger.logM3Error(e, parameter);
			throw e;
		}
	}

	private OMElement getM3Response(OMElement request,
			ServiceClient serviceClient) throws AxisFault {
		return serviceClient.sendReceive(request);
	}

	private OMElement createSOAPEnveloperHeader(SOAPFactory factory) {
		QName MWS_HEADER_ELEMENT = new QName(
				applicationPropertyBean.getM3HeaderURI(), "mws");
		QName USERNAME_ELEMENT = new QName(
				applicationPropertyBean.getM3HeaderURI(), "user");
		QName PASSWORD_ELEMENT = new QName(
				applicationPropertyBean.getM3HeaderURI(), "password");

		// Create & add the SOAP Header
		OMNamespace ns = factory.createOMNamespace(
				applicationPropertyBean.getM3HeaderURI(),
				applicationPropertyBean.getM3HeaderPrefix());
		OMElement header = factory.createOMElement(
				MWS_HEADER_ELEMENT.getLocalPart(), ns);
		// user
		OMElement userElement = factory.createOMElement(
				USERNAME_ELEMENT.getLocalPart(), ns);
		userElement.setText(applicationPropertyBean.getM3Username());
		header.addChild(userElement);
		// password
		OMElement passwordElement = factory.createOMElement(
				PASSWORD_ELEMENT.getLocalPart(), ns);
		passwordElement.setText(applicationPropertyBean.getM3Password());
		header.addChild(passwordElement);
		return header;
	}

	private Options initSOAPOptions(String webServiceName) {
		String ENDPOINT = applicationPropertyBean.getM3EndPointUrl() + "/"
				+ StringUtils.trimToEmpty(webServiceName);

		// Create the service client
		Options opt = new Options();
		opt.setTo(new EndpointReference(ENDPOINT));
		opt.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		opt.setUseSeparateListener(false);
		return opt;
	}

}
