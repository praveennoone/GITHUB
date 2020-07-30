/**
 * 
 */
package com.gavs.hishear.web.reports.exceptional.dao;

import java.util.ArrayList;

import com.gavs.hishear.web.domain.Sequence;
import com.gavs.hishear.web.reports.exceptional.domain.ExceptionalDto;

/**
 * @author sundarrajanr
 * 
 */
public interface ExceptionalDao {
	public ArrayList getLogonLogoffReport(ExceptionalDto exceptionalDto);

	public ArrayList getNonProductivityReport(ExceptionalDto exceptionalDto);

	public ArrayList getEmployeeDetailsReport(ExceptionalDto exceptionalDto);

	public ArrayList getActivityRejectedReport(ExceptionalDto exceptionalDto);

	public ArrayList getActivityRejection(ExceptionalDto dto);

	public ArrayList getActivityAdjustment(String moNumber, String lineNumber,
			String sequence);

	public ArrayList getDepartments(String quey);

	public void updateRejectedRecord(ExceptionalDto dto);

	public void updatePauseTime(ExceptionalDto dto);

	public ArrayList getLogonLogoffDetails(ExceptionalDto dto);

	public void rejectActivity(ExceptionalDto dto, String rejectedBy);

	public ArrayList<Sequence> getReasons(String query);
}
