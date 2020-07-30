/**
 * CleanUpFile.java
 */
package com.gavs.hishear.configurations;

import java.io.File;

/**
 * @author Pinky S	 	  
 * 
 */
public class CleanUpFile {

	public void deletePropertyFile() {

		File file = new File("C:\\Servers\\ShopFloorAdmin.properties");

		try {

			//file.delete();

		} catch (Exception e) {

			System.out.println("File Not found");
			e.printStackTrace();

		}
	}
}
//PDZ010MI
