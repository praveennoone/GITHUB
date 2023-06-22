package com.lisi.docviewer.dao;

import java.util.List;

import com.lisi.docviewer.model.DocViewer;

/**
 * @author Dinesh Rajput
 *
 */
public interface DocViewerDao {
	/**
	 * 
	 * @param empName
	 * @return
	 */
	public List<DocViewer> getLisiIndexerByName(String empName);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<DocViewer> getLisiIndexerById(int id);
	
}
