package com.lisi.docviewer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lisi.docviewer.dao.DocViewerDao;
import com.lisi.docviewer.model.DocViewer;

/**
 * @author Venkat Gonepudi
 *
 */
@Service("DocViewerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DocViewerServiceImpl implements DocViewerService {

	@Autowired
	private DocViewerDao docViewerDao;
		
	public List<DocViewer> getLisiIndexerByName(String empName) {
		return docViewerDao.getLisiIndexerByName(empName);
	}

	public List<DocViewer> getLisiIndexerById(int id) {
		return docViewerDao.getLisiIndexerById(id);
	}
	
}
