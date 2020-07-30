/**
 * 
 */
package com.gavs.hishear.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gavs.hishear.constant.M3Parameter;
import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.m3interface.dto.M3APIParameter;
import com.gavs.hishear.web.domain.Item;

/**
 * @author saravanam
 * 
 */
public final class MMS200MIReadUtil {

	private static final String EMPTY_STRING = "";

	// Utility classes should not have a public or default constructor.
	private MMS200MIReadUtil() {
		super();
	}

	public static ArrayList getItemBasic(Item dto, M3APIWSClient client) {

		M3APIParameter parameter = new M3APIParameter();
		parameter.setWebServiceName("MMS200MIRead");
		parameter.setInputItem("GetItmBasicItem");
		parameter.setOutputItem("GetItmBasicResponseItem");
		parameter.setFunctionName("getItemBasic");

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(M3Parameter.CONO.getValue(), dto.getCompany());
		map.put(M3Parameter.ITNO.getValue(), dto.getItemNumber());
		map.put(M3Parameter.LNCD.getValue(), EMPTY_STRING);

		parameter.setInputData(map);
		List list = null;
		try {
			list = (List) client.callM3API(parameter);
		} catch (Exception e) {
			list = new ArrayList();
			Item seq = new Item();
			seq.setErrorMessage(e.getMessage());
			list.add(seq);
			System.out.println("Message-->" + seq.getErrorMessage());
		}
		return (ArrayList) list;
	}

}
