package com.gavs.hishear.m3interface.dto;

import java.util.ArrayList;
import java.util.List;

public class Program extends M3Entity {
	private List subPrograms = new ArrayList();
	private String m3Function;
	private String readOrWrite;

	public List getSubPrograms() {
		return subPrograms;
	}

	public void setSubPrograms(List subPrograms) {
		this.subPrograms = subPrograms;
	}

	public String getM3Function() {
		return m3Function;
	}

	public void setM3Function(String function) {
		m3Function = function;
	}

	public String getReadOrWrite() {
		return readOrWrite;
	}

	public void setReadOrWrite(String readOrWrite) {
		this.readOrWrite = readOrWrite;
	}
}
