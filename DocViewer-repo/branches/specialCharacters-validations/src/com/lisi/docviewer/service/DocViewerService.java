package com.lisi.docviewer.service;

import java.util.List;

import com.lisi.docviewer.model.DocViewer;

/**
 * @author Venkat Gonepudi
 *
 */
public interface DocViewerService {
	
	public List<DocViewer> getLisiIndexerByName(String empName);
	
	public List<DocViewer> getLisiIndexerById(int empid);
	
	
}
