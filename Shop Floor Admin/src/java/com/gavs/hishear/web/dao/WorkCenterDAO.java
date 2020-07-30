/**
 * WorkCenterDAO.java
 */
package com.gavs.hishear.web.dao;

import java.util.ArrayList;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.domain.AssetConfig;
import com.gavs.hishear.web.domain.ConfigDivision;
import com.gavs.hishear.web.domain.ReasonCode;
import com.gavs.hishear.web.domain.ReasonType;
import com.gavs.hishear.web.domain.WorkCenterConfig;

/**
 * @author Ambrish_V
 * 
 */

// Begin WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr 2011
public interface WorkCenterDAO {

	/**
	 * Method to get the Work Center Configuration Details
	 * 
	 * @param workCenter
	 * @param query
	 * @return
	 */
	public ArrayList<WorkCenterConfig> getWorkCenterConfigDetails(
			WorkCenterConfig workCenter, String query);

	/**
	 * Method to Update the Work Center Configuration Details
	 * 
	 * @param workCenter
	 * @param query
	 * @return
	 */
	public void updateWorkCenterConfigDetails(WorkCenterConfig workCenter,
			String query);

	/**
	 * Method to Insert the Work Center Configuration Details
	 * 
	 * @param workCenter
	 * @param query
	 * @return
	 */
	public void insertNewWorkCenterDetails(WorkCenterConfig workCenter,
			String query);

	/**
	 * Method to Insert the Asset Configuration Details
	 * 
	 * @param asset
	 * @param query
	 * @return
	 */
	public void insertNewAssetDetails(AssetConfig asset, String query);

	/**
	 * Method to insert the Work Center Configuration Log Details
	 * 
	 * @param workCenter
	 * @param userContext
	 * @param query
	 * @return
	 */
	public void insertWorkCenterLogDetails(WorkCenterConfig workCenter,
			UserContext userContext, String query);

	/**
	 * Method to insert the Asset Configuration Log Details
	 * 
	 * @param asset
	 * @param userContext
	 * @param query
	 * @return
	 */
	public void insertAssetLogDetails(AssetConfig asset,
			UserContext userContext, String query);

	/**
	 * Method to get the Asset Configuration Details
	 * 
	 * @param asset
	 * @param query
	 * @return
	 */
	public ArrayList<AssetConfig> getAssetConfigDetails(AssetConfig asset,
			String query);

	/**
	 * Method to get the Variance Details
	 * 
	 * @param configDivision
	 * @param query
	 * @return
	 */
	public ConfigDivision getVarianceDetails(ConfigDivision configDivision,
			String query);

	/**
	 * Method to update the Variance Details
	 * 
	 * @param variance
	 * @param operation
	 * @param type
	 * @param division
	 * @param query
	 * @return
	 */
	public void updateVarianceDetails(String variance, String operation,
			String type, String division, String query);

	/**
	 * Method to update the Asset Configuration Details
	 * 
	 * @param asset
	 * @param query
	 * @return
	 */
	public void updateAssetConfigDetails(AssetConfig asset, String query);

	/**
	 * Method to get the Reason Master Details
	 * 
	 * @param dto
	 * @param query
	 * @return
	 */
	public ArrayList<ReasonType> getReasonDetails(ReasonCode dto, String query);

	/**
	 * Method to get the Reason Master Details
	 * 
	 * @param dto
	 * @param query
	 * @return
	 */
	public ArrayList<ReasonType> getReasonFilterDetails(ReasonCode dto,
			String query);

	/**
	 * Method to insert the Reason Master Details
	 * 
	 * @param reasonType
	 * @param reasonDesc
	 * @param query
	 * @return
	 */
	public void insertNewReason(String reasonType, String reasonDesc,
			String query);

	/**
	 * Method to insert the Variance Log Details
	 * 
	 * @param user
	 * @param variance
	 * @param operation
	 * @param type
	 * @param division
	 * @param query
	 * @return
	 */
	public void insertVarianceLogDetails(UserContext user, String variance,
			String operation, String type, String division, String query);
	// End WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr
	// 2011


}
