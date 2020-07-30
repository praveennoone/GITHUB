package com.gavs.hishear.web.reports.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.DeptWCAssetReportDAO;
import com.gavs.hishear.web.reports.DeptWCAssetReport;

public class DepartmentValidator implements Validator {
	private DeptWCAssetReportDAO deptWCAssetReportDAO;

	private UserContext userContext;

	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}

	public boolean supports(Class arg0) {
		return arg0.equals(DeptWCAssetReport.class);
	}

	public void validate(Object arg0, Errors errors) {
		DeptWCAssetReport assetReport = (DeptWCAssetReport) arg0;

		if (assetReport.getNewDeptNumber() != null
				&& !"".equals(assetReport.getNewDeptNumber())) {
			try {

				int rowCount = deptWCAssetReportDAO.getWorkcenters(
						assetReport.getNewDeptNumber(),
						(String) userContext.getQueries().get("SHOPR_057"))
						.size();
				System.out.println("rowCount--------------------" + rowCount);
				if (rowCount <= 0) {
					errors.rejectValue("deptNumber", "invalidDepartment",
							"Invalid Department "
									+ assetReport.getNewDeptNumber());
				}
			} catch (Exception e) {
				errors.rejectValue("deptNumber", "someThingWentWrong",
						"Operation Cannot Be Performed Now. Try Again Later!!");
			}
		}
	}

	public DeptWCAssetReportDAO getDeptWCAssetReportDAO() {
		return deptWCAssetReportDAO;
	}

	public void setDeptWCAssetReportDAO(
			DeptWCAssetReportDAO deptWCAssetReportDAO) {
		this.deptWCAssetReportDAO = deptWCAssetReportDAO;
	}
}
