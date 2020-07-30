/**
 * 
 */
package com.gavs.hishear.m3interface.dto;

import java.util.HashMap;

/**
 * @author saravanam
 * 
 */
public class M3APIParameter extends M3Entity {
	private String inputItem;
	private String outputItem;
	private String readOrWrite;

	public String getInputItem() {
		return inputItem;
	}

	public void setInputItem(String inputItem) {
		this.inputItem = inputItem;
	}

	public String getOutputItem() {
		return outputItem;
	}

	public void setOutputItem(String outputItem) {
		this.outputItem = outputItem;
	}

	/**
	 * @return the readOrWrite
	 */
	public String getReadOrWrite() {
		return readOrWrite;
	}

	/**
	 * @param readOrWrite
	 *            the readOrWrite to set
	 */
	public void setReadOrWrite(String readOrWrite) {
		this.readOrWrite = readOrWrite;
	}
}
