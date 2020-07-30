package com.gavs.hishear.web.reports.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.DeptWCAssetReportDAO;
import com.gavs.hishear.web.domain.Sequence;

public class WCReportValidator implements Validator {
	private DeptWCAssetReportDAO deptWCAssetReportDAO;

	private UserContext userContext;

	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}

	public boolean supports(Class arg0) {
		return arg0.equals(Sequence.class);
	}

	public void validate(Object arg0, Errors errors) {
		Sequence sequence = (Sequence) arg0;
		String wcCode = sequence.getWorkCenterCode();
		try {
			String wcDesc = getDeptWCAssetReportDAO().isValidWorkCentre(wcCode,
					(String) userContext.getQueries().get("SHOPR_054"));
			if (wcDesc == null) {
				errors.rejectValue("workCenterCode", "invalidWorkCentre",
						"Invalid Work Centre!!");
			}
			sequence.setWorkCenterDesc(wcDesc);
		} catch (Exception e) {
			errors.rejectValue("workCenterCode", "someThingWentWrong",
					"Operation Cannot Be Performed Now. Try Again Later!!");
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
