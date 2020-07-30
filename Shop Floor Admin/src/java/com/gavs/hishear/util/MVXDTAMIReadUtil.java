///**
// * 
// */
//package com.gavs.hishear.web.util;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Set;
//
//
//import com.gavs.hishear.web.domain.Sequence;
//
///**
// * @author saravanam
// *
// */
//public class MVXDTAMIReadUtil {
//	public static ArrayList getOperation(Sequence dto){
//		System.out.println("PMS070MIReadUtil::Company--->"+dto.getCompany());
//		System.out.println("PMS070MIReadUtil::MO number--->"+dto.getMoNumber());
//
//		ArrayList sequenceList= new ArrayList();
//
//		String[] inputParamValues = new String[2];
//		inputParamValues[0] = dto.getCompany();
//		inputParamValues[1] = dto.getMoNumber();
//		
//		String outputParamFields[]={"PRNO","FACI","DWNO"};
//
//		System.out.println("Before creating instance Accessor");
////		MVXDTAMIReadAccessor wsAccessor = new MVXDTAMIReadAccessor(inputParamValues, outputParamFields);
////		ArrayList<HashMap<String, String>> result = wsAccessor.readMVXDTAMI();
//		if(result!=null)
//			System.out.println("After returning the arraylist Size--->"+result.size());
//		//	System.out.println("result===="+result);
//		if(result != null) {
//			for (HashMap<String, String> hashMap : result) {
//				Set<String> keys = hashMap.keySet();
//				for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
//					String key = iterator.next();
//					String value = hashMap.get(key);
//					if(key.equals("FACI")){
//						dto.setFacility(value);					
//					}else if(key.equals("PRNO")){
//						dto.setPartNumber(value);
//					}else if(key.equals("error"))	{
//						dto.setErrorMessage(value);
//					}
//					System.out.print(key +" : "+ value + "\t");
//				}
//				sequenceList.add(dto);
//			}				
//		}				
//		return sequenceList;			
//	}	
//}

