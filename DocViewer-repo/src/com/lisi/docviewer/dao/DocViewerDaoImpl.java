package com.lisi.docviewer.dao;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lisi.docviewer.model.DocViewer;

/**
 * @author Venkat Gonepudi
 *
 */
@Repository("DocViewerDao")
public class DocViewerDaoImpl implements DocViewerDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Query query=null;
	
	@SuppressWarnings("unchecked")
	public List<DocViewer> getLisiIndexerByName(String empName) {
		
		String getQuery = "from DocViewer docViewer where docViewer.metadataValue like'%"+empName+"%' or docViewer.filePath like '%"+empName+"%'";
		query = sessionFactory.getCurrentSession().createQuery(getQuery);		
		return (List<DocViewer>)query.list();
		/*return (List<Employee>) sessionFactory.getCurrentSession().createCriteria(Employee.class).list();*/
	}

	@SuppressWarnings("unchecked")
	public List<DocViewer> getLisiIndexerById(int id) {
		
		String getQuery = "from DocViewer docViewer where docViewer.indexId='"+id+"'";
		query = sessionFactory.getCurrentSession().createQuery(getQuery);		
		return (List<DocViewer>)query.list();
	}

}
