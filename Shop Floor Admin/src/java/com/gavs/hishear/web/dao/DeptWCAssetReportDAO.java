package com.gavs.hishear.web.dao;

import java.util.ArrayList;

import com.gavs.hishear.web.domain.Asset;
import com.gavs.hishear.web.domain.Department;
import com.gavs.hishear.web.domain.WorkCenter;
import com.gavs.hishear.web.reports.DeptWCAssetReport;

public interface DeptWCAssetReportDAO {
	public ArrayList<Department> getDepartments(String query);

	public ArrayList<Asset> getAssetDetails(DeptWCAssetReport dto);

	public ArrayList<WorkCenter> getWorkcenters(DeptWCAssetReport dto);

	public ArrayList<WorkCenter> getWorkcenters(String departmentNumber,
			String query);

	public void insertDepartment(DeptWCAssetReport dto);

	public String isValidWorkCentre(String wcCode, String query);

	public int updateQtyCaptureRequiredFlag(String deptNumber, String asset,
			String qtyCaptureRequired, String query);
}
