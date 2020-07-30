// File:         ApplicationPropertyBean.java
// Created:      Feb 23, 2011
// Author:       rahjeshd
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that holds the Application properties.
 * 
 */
public class ApplicationPropertyBean implements Serializable {

	/** Default Serial Version ID. */
	private static final long serialVersionUID = 1L;

	// WSDL URL for the M3 Web service */

	/** The m3 end point url. */
	private String m3EndPointUrl;

	/** The m3 username. */
	private String m3Username;

	/** The m3 password. */
	private String m3Password;

	/** The m3 namespace. */
	private String m3Namespace;

	/** The m3 header prefix. */
	private String m3HeaderPrefix;

	/** The m3 header uri. */
	private String m3HeaderURI;

	/** The company. */
	private String company;

	/** The morv location. */
	// private String morvLocation;
	// // Properties used to do Physical Allocation
	// /** The shipping days. */
	// private String shippingDays;
	//
	// /** The minimum stock allocation percentage. */
	// private String minimumStockAllocationPercentage;
	//
	// /** The ignorable shipped quantity percentage. */
	// private String ignorableShippedQuantityPercentage;
	//
	// /** The allocation engine service path. */
	// private String allocationEngineServicePath;
	/** The priority for pm s050 mi write. */
	private String priorityForPMS050MIWrite;

	/** The priority for li s200 write. */
	private String priorityForLIS200Write;

	/** The priority for pm s130 write. */
	private String priorityForPMS130Write;

	/** The priority for pm s070 write. */
	private String priorityForPMS070Write;
	
	private String dynamicMessagePath;

	// /** The threshold price. */
	// private String thresholdPrice;
	//
	// /** The ignored locations. */
	// private String ignoredLocations;
	//
	// /** The least allocation priority location. */
	// private String leastAllocationPriorityLocation;
	//
	// /** The customers dont accept partial shipment. */
	// private String customersDontAcceptPartialShipment;
	//
	// /** The consider customer preferences for hsc. */
	// private String considerCustomerPreferencesForHSC;
	//
	// /** The consider customer preferences for mdk. */
	// private String considerCustomerPreferencesForMDK;

	/** The web services with trans type. */
	private String webServicesWithTransType;

	/** The key fields mh s850 mi bolt on. */
	private String keyFieldsMHS850MIBoltOn;

	/** The key fields mm z428 mi bolt on. */
	private String keyFieldsMMZ428MIBoltOn;

	/** The key fields mw z422 mi bolt on. */
	private String keyFieldsMWZ422MIBoltOn;

	/** The key fields pm s050 mi bolt on. */
	private String keyFieldsPMS050MIBoltOn;

	/** The key fields pm s130 bolt on. */
	private String keyFieldsPMS130BoltOn;

	

	/** The key fields pp s320 bolt on. */
	private String keyFieldsPPS320BoltOn;

	/** The key fields pp y001 mi bolt on. */
	private String keyFieldsPPY001MIBoltOn;

	/** The key fields mh s850 mi m3. */
	private String keyFieldsMHS850MIM3;

	/** The key fields mm z428 mi m3. */
	private String keyFieldsMMZ428MIM3;

	/** The key fields mw z422 mi m3. */
	private String keyFieldsMWZ422MIM3;

	/** The key fields pm s050 mi m3. */
	private String keyFieldsPMS050MIM3;

	/** The key fields pm s130 m3. */
	private String keyFieldsPMS130M3;

	/** The key fields pp s320 m3. */
	private String keyFieldsPPS320M3;

	/** The key fields pp y001 mi m3. */
	private String keyFieldsPPY001MIM3;

	/** The trans type mh s850 mi. */
	private String transTypeMHS850MI;

	/** The trans type mm z428 mi. */
	private String transTypeMMZ428MI;

	/** The trans type mw z422 mi. */
	private String transTypeMWZ422MI;

	/** The trans type pm s050 mi. */
	private String transTypePMS050MI;

	/** The trans type pm s130. */
	private String transTypePMS130;

	/** The trans type pp s320. */
	private String transTypePPS320;

	/** The trans type pp y001 mi. */
	private String transTypePPY001MI;

	/** The web services comparable fields. */
	private String webServicesComparableFields;

	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	private HashMap<String, ArrayList<String>> activeDirectoryMap;

	private HashMap<String, ArrayList<String>> activeMenuDirectoryMap;

	private boolean m3Connectionflag = false;

	private String ldapUrl;
	
	private String ldapUrlSecond;

	private String ldapSecurity;

	private ArrayList<String> distinctActiveDirectoryGroup;
	
	private String pabDC;
    private String m3Server;
    private String m3Port;

    private WelcomeMessage welcomeMessage = new WelcomeMessage();

    // End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011

	/**
	 * Gets the m3 end point url.
	 * 
	 * @return the m3EndPointUrl
	 */
	public String getM3EndPointUrl() {
		return m3EndPointUrl;
	}

	/**
	 * Sets the m3 end point url.
	 * 
	 * @param endPointUrl
	 *            the m3EndPointUrl to set
	 */
	public void setM3EndPointUrl(String endPointUrl) {
		m3EndPointUrl = endPointUrl;
	}

	/**
	 * Gets the m3 username.
	 * 
	 * @return the m3Username
	 */
	public String getM3Username() {
		return m3Username;
	}

	/**
	 * Sets the m3 username.
	 * 
	 * @param username
	 *            the m3Username to set
	 */
	public void setM3Username(String username) {
		m3Username = username;
	}

	/**
	 * Gets the m3 password.
	 * 
	 * @return the m3Password
	 */
	public String getM3Password() {
		return m3Password;
	}

	/**
	 * Sets the m3 password.
	 * 
	 * @param password
	 *            the m3Password to set
	 */
	public void setM3Password(String password) {
		m3Password = password;
	}

	/**
	 * Gets the m3 namespace.
	 * 
	 * @return the m3Namespace
	 */
	public String getM3Namespace() {
		return m3Namespace;
	}

	/**
	 * Sets the m3 namespace.
	 * 
	 * @param namespace
	 *            the m3Namespace to set
	 */
	public void setM3Namespace(String namespace) {
		m3Namespace = namespace;
	}

	/**
	 * Gets the m3 header prefix.
	 * 
	 * @return the m3HeaderPrefix
	 */
	public String getM3HeaderPrefix() {
		return m3HeaderPrefix;
	}

	/**
	 * Sets the m3 header prefix.
	 * 
	 * @param headerPrefix
	 *            the m3HeaderPrefix to set
	 */
	public void setM3HeaderPrefix(String headerPrefix) {
		m3HeaderPrefix = headerPrefix;
	}

	/**
	 * Gets the m3 header uri.
	 * 
	 * @return the m3HeaderURI
	 */
	public String getM3HeaderURI() {
		return m3HeaderURI;
	}

	/**
	 * Sets the m3 header uri.
	 * 
	 * @param headerURI
	 *            the m3HeaderURI to set
	 */
	public void setM3HeaderURI(String headerURI) {
		m3HeaderURI = headerURI;
	}

	/**
	 * Gets the company.
	 * 
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 * 
	 * @param company
	 *            the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * Gets the priority for pm s050 mi write.
	 * 
	 * @return the priorityForPMS050MIWrite
	 */
	public String getPriorityForPMS050MIWrite() {
		return priorityForPMS050MIWrite;
	}

	/**
	 * Sets the priority for pm s050 mi write.
	 * 
	 * @param priorityForPMS050MIWrite
	 *            the priorityForPMS050MIWrite to set
	 */
	public void setPriorityForPMS050MIWrite(String priorityForPMS050MIWrite) {
		this.priorityForPMS050MIWrite = priorityForPMS050MIWrite;
	}

	/**
	 * Gets the priority for li s200 write.
	 * 
	 * @return the priorityForLIS200Write
	 */
	public String getPriorityForLIS200Write() {
		return priorityForLIS200Write;
	}

	/**
	 * Sets the priority for li s200 write.
	 * 
	 * @param priorityForLIS200Write
	 *            the priorityForLIS200Write to set
	 */
	public void setPriorityForLIS200Write(String priorityForLIS200Write) {
		this.priorityForLIS200Write = priorityForLIS200Write;
	}

	/**
	 * Gets the priority for pm s130 write.
	 * 
	 * @return the priorityForPMS130Write
	 */
	public String getPriorityForPMS130Write() {
		return priorityForPMS130Write;
	}

	/**
	 * Sets the priority for pm s130 write.
	 * 
	 * @param priorityForPMS130Write
	 *            the priorityForPMS130Write to set
	 */
	public void setPriorityForPMS130Write(String priorityForPMS130Write) {
		this.priorityForPMS130Write = priorityForPMS130Write;
	}

	/**
	 * Gets the priority for pm s070 write.
	 * 
	 * @return the priorityForPMS070Write
	 */
	public String getPriorityForPMS070Write() {
		return priorityForPMS070Write;
	}

	/**
	 * Sets the priority for pm s070 write.
	 * 
	 * @param priorityForPMS070Write
	 *            the priorityForPMS070Write to set
	 */
	public void setPriorityForPMS070Write(String priorityForPMS070Write) {
		this.priorityForPMS070Write = priorityForPMS070Write;
	}

	/**
	 * Gets the web services with trans type.
	 * 
	 * @return the webServicesWithTransType
	 */
	public String getWebServicesWithTransType() {
		return webServicesWithTransType;
	}

	/**
	 * Sets the web services with trans type.
	 * 
	 * @param webServicesWithTransType
	 *            the webServicesWithTransType to set
	 */
	public void setWebServicesWithTransType(String webServicesWithTransType) {
		this.webServicesWithTransType = webServicesWithTransType;
	}

	/**
	 * Gets the key fields mh s850 mi bolt on.
	 * 
	 * @return the keyFieldsMHS850MIBoltOn
	 */
	public String getKeyFieldsMHS850MIBoltOn() {
		return keyFieldsMHS850MIBoltOn;
	}

	/**
	 * Sets the key fields mh s850 mi bolt on.
	 * 
	 * @param keyFieldsMHS850MIBoltOn
	 *            the keyFieldsMHS850MIBoltOn to set
	 */
	public void setKeyFieldsMHS850MIBoltOn(String keyFieldsMHS850MIBoltOn) {
		this.keyFieldsMHS850MIBoltOn = keyFieldsMHS850MIBoltOn;
	}

	/**
	 * Gets the key fields mm z428 mi bolt on.
	 * 
	 * @return the keyFieldsMMZ428MIBoltOn
	 */
	public String getKeyFieldsMMZ428MIBoltOn() {
		return keyFieldsMMZ428MIBoltOn;
	}

	/**
	 * Sets the key fields mm z428 mi bolt on.
	 * 
	 * @param keyFieldsMMZ428MIBoltOn
	 *            the keyFieldsMMZ428MIBoltOn to set
	 */
	public void setKeyFieldsMMZ428MIBoltOn(String keyFieldsMMZ428MIBoltOn) {
		this.keyFieldsMMZ428MIBoltOn = keyFieldsMMZ428MIBoltOn;
	}

	/**
	 * Gets the key fields mw z422 mi bolt on.
	 * 
	 * @return the keyFieldsMWZ422MIBoltOn
	 */
	public String getKeyFieldsMWZ422MIBoltOn() {
		return keyFieldsMWZ422MIBoltOn;
	}

	/**
	 * Sets the key fields mw z422 mi bolt on.
	 * 
	 * @param keyFieldsMWZ422MIBoltOn
	 *            the keyFieldsMWZ422MIBoltOn to set
	 */
	public void setKeyFieldsMWZ422MIBoltOn(String keyFieldsMWZ422MIBoltOn) {
		this.keyFieldsMWZ422MIBoltOn = keyFieldsMWZ422MIBoltOn;
	}

	/**
	 * Gets the key fields pm s050 mi bolt on.
	 * 
	 * @return the keyFieldsPMS050MIBoltOn
	 */
	public String getKeyFieldsPMS050MIBoltOn() {
		return keyFieldsPMS050MIBoltOn;
	}

	/**
	 * Sets the key fields pm s050 mi bolt on.
	 * 
	 * @param keyFieldsPMS050MIBoltOn
	 *            the keyFieldsPMS050MIBoltOn to set
	 */
	public void setKeyFieldsPMS050MIBoltOn(String keyFieldsPMS050MIBoltOn) {
		this.keyFieldsPMS050MIBoltOn = keyFieldsPMS050MIBoltOn;
	}

	/**
	 * Gets the key fields pm s130 bolt on.
	 * 
	 * @return the keyFieldsPMS130BoltOn
	 */
	public String getKeyFieldsPMS130BoltOn() {
		return keyFieldsPMS130BoltOn;
	}

	/**
	 * Sets the key fields pm s130 bolt on.
	 * 
	 * @param keyFieldsPMS130BoltOn
	 *            the keyFieldsPMS130BoltOn to set
	 */
	public void setKeyFieldsPMS130BoltOn(String keyFieldsPMS130BoltOn) {
		this.keyFieldsPMS130BoltOn = keyFieldsPMS130BoltOn;
	}

	/**
	 * Gets the key fields pp s320 bolt on.
	 * 
	 * @return the keyFieldsPPS320BoltOn
	 */
	public String getKeyFieldsPPS320BoltOn() {
		return keyFieldsPPS320BoltOn;
	}

	/**
	 * Sets the key fields pp s320 bolt on.
	 * 
	 * @param keyFieldsPPS320BoltOn
	 *            the keyFieldsPPS320BoltOn to set
	 */
	public void setKeyFieldsPPS320BoltOn(String keyFieldsPPS320BoltOn) {
		this.keyFieldsPPS320BoltOn = keyFieldsPPS320BoltOn;
	}

	/**
	 * Gets the key fields pp y001 mi bolt on.
	 * 
	 * @return the keyFieldsPPY001MIBoltOn
	 */
	public String getKeyFieldsPPY001MIBoltOn() {
		return keyFieldsPPY001MIBoltOn;
	}

	/**
	 * Sets the key fields pp y001 mi bolt on.
	 * 
	 * @param keyFieldsPPY001MIBoltOn
	 *            the keyFieldsPPY001MIBoltOn to set
	 */
	public void setKeyFieldsPPY001MIBoltOn(String keyFieldsPPY001MIBoltOn) {
		this.keyFieldsPPY001MIBoltOn = keyFieldsPPY001MIBoltOn;
	}

	/**
	 * Gets the key fields mh s850 mi m3.
	 * 
	 * @return the keyFieldsMHS850MIM3
	 */
	public String getKeyFieldsMHS850MIM3() {
		return keyFieldsMHS850MIM3;
	}

	/**
	 * Sets the key fields mh s850 mi m3.
	 * 
	 * @param keyFieldsMHS850MIM3
	 *            the keyFieldsMHS850MIM3 to set
	 */
	public void setKeyFieldsMHS850MIM3(String keyFieldsMHS850MIM3) {
		this.keyFieldsMHS850MIM3 = keyFieldsMHS850MIM3;
	}

	/**
	 * Gets the key fields mm z428 mi m3.
	 * 
	 * @return the keyFieldsMMZ428MIM3
	 */
	public String getKeyFieldsMMZ428MIM3() {
		return keyFieldsMMZ428MIM3;
	}

	/**
	 * Sets the key fields mm z428 mi m3.
	 * 
	 * @param keyFieldsMMZ428MIM3
	 *            the keyFieldsMMZ428MIM3 to set
	 */
	public void setKeyFieldsMMZ428MIM3(String keyFieldsMMZ428MIM3) {
		this.keyFieldsMMZ428MIM3 = keyFieldsMMZ428MIM3;
	}

	/**
	 * Gets the key fields mw z422 mi m3.
	 * 
	 * @return the keyFieldsMWZ422MIM3
	 */
	public String getKeyFieldsMWZ422MIM3() {
		return keyFieldsMWZ422MIM3;
	}

	/**
	 * Sets the key fields mw z422 mi m3.
	 * 
	 * @param keyFieldsMWZ422MIM3
	 *            the keyFieldsMWZ422MIM3 to set
	 */
	public void setKeyFieldsMWZ422MIM3(String keyFieldsMWZ422MIM3) {
		this.keyFieldsMWZ422MIM3 = keyFieldsMWZ422MIM3;
	}

	/**
	 * Gets the key fields pm s050 mi m3.
	 * 
	 * @return the keyFieldsPMS050MIM3
	 */
	public String getKeyFieldsPMS050MIM3() {
		return keyFieldsPMS050MIM3;
	}

	/**
	 * Sets the key fields pm s050 mi m3.
	 * 
	 * @param keyFieldsPMS050MIM3
	 *            the keyFieldsPMS050MIM3 to set
	 */
	public void setKeyFieldsPMS050MIM3(String keyFieldsPMS050MIM3) {
		this.keyFieldsPMS050MIM3 = keyFieldsPMS050MIM3;
	}

	/**
	 * Gets the key fields pm s130 m3.
	 * 
	 * @return the keyFieldsPMS130M3
	 */
	public String getKeyFieldsPMS130M3() {
		return keyFieldsPMS130M3;
	}

	/**
	 * Sets the key fields pm s130 m3.
	 * 
	 * @param keyFieldsPMS130M3
	 *            the keyFieldsPMS130M3 to set
	 */
	public void setKeyFieldsPMS130M3(String keyFieldsPMS130M3) {
		this.keyFieldsPMS130M3 = keyFieldsPMS130M3;
	}

	/**
	 * Gets the key fields pp s320 m3.
	 * 
	 * @return the keyFieldsPPS320M3
	 */
	public String getKeyFieldsPPS320M3() {
		return keyFieldsPPS320M3;
	}

	/**
	 * Sets the key fields pp s320 m3.
	 * 
	 * @param keyFieldsPPS320M3
	 *            the keyFieldsPPS320M3 to set
	 */
	public void setKeyFieldsPPS320M3(String keyFieldsPPS320M3) {
		this.keyFieldsPPS320M3 = keyFieldsPPS320M3;
	}

	/**
	 * Gets the key fields pp y001 mi m3.
	 * 
	 * @return the keyFieldsPPY001MIM3
	 */
	public String getKeyFieldsPPY001MIM3() {
		return keyFieldsPPY001MIM3;
	}

	/**
	 * Sets the key fields pp y001 mi m3.
	 * 
	 * @param keyFieldsPPY001MIM3
	 *            the keyFieldsPPY001MIM3 to set
	 */
	public void setKeyFieldsPPY001MIM3(String keyFieldsPPY001MIM3) {
		this.keyFieldsPPY001MIM3 = keyFieldsPPY001MIM3;
	}

	/**
	 * Gets the trans type mh s850 mi.
	 * 
	 * @return the transTypeMHS850MI
	 */
	public String getTransTypeMHS850MI() {
		return transTypeMHS850MI;
	}

	/**
	 * Sets the trans type mh s850 mi.
	 * 
	 * @param transTypeMHS850MI
	 *            the transTypeMHS850MI to set
	 */
	public void setTransTypeMHS850MI(String transTypeMHS850MI) {
		this.transTypeMHS850MI = transTypeMHS850MI;
	}

	/**
	 * Gets the trans type mm z428 mi.
	 * 
	 * @return the transTypeMMZ428MI
	 */
	public String getTransTypeMMZ428MI() {
		return transTypeMMZ428MI;
	}

	/**
	 * Sets the trans type mm z428 mi.
	 * 
	 * @param transTypeMMZ428MI
	 *            the transTypeMMZ428MI to set
	 */
	public void setTransTypeMMZ428MI(String transTypeMMZ428MI) {
		this.transTypeMMZ428MI = transTypeMMZ428MI;
	}

	/**
	 * Gets the trans type mw z422 mi.
	 * 
	 * @return the transTypeMWZ422MI
	 */
	public String getTransTypeMWZ422MI() {
		return transTypeMWZ422MI;
	}

	/**
	 * Sets the trans type mw z422 mi.
	 * 
	 * @param transTypeMWZ422MI
	 *            the transTypeMWZ422MI to set
	 */
	public void setTransTypeMWZ422MI(String transTypeMWZ422MI) {
		this.transTypeMWZ422MI = transTypeMWZ422MI;
	}

	/**
	 * Gets the trans type pm s050 mi.
	 * 
	 * @return the transTypePMS050MI
	 */
	public String getTransTypePMS050MI() {
		return transTypePMS050MI;
	}

	/**
	 * Sets the trans type pm s050 mi.
	 * 
	 * @param transTypePMS050MI
	 *            the transTypePMS050MI to set
	 */
	public void setTransTypePMS050MI(String transTypePMS050MI) {
		this.transTypePMS050MI = transTypePMS050MI;
	}

	/**
	 * Gets the trans type pm s130.
	 * 
	 * @return the transTypePMS130
	 */
	public String getTransTypePMS130() {
		return transTypePMS130;
	}

	/**
	 * Sets the trans type pm s130.
	 * 
	 * @param transTypePMS130
	 *            the transTypePMS130 to set
	 */
	public void setTransTypePMS130(String transTypePMS130) {
		this.transTypePMS130 = transTypePMS130;
	}

	/**
	 * Gets the trans type pp s320.
	 * 
	 * @return the transTypePPS320
	 */
	public String getTransTypePPS320() {
		return transTypePPS320;
	}

	/**
	 * Sets the trans type pp s320.
	 * 
	 * @param transTypePPS320
	 *            the transTypePPS320 to set
	 */
	public void setTransTypePPS320(String transTypePPS320) {
		this.transTypePPS320 = transTypePPS320;
	}

	/**
	 * Gets the trans type pp y001 mi.
	 * 
	 * @return the transTypePPY001MI
	 */
	public String getTransTypePPY001MI() {
		return transTypePPY001MI;
	}

	/**
	 * Sets the trans type pp y001 mi.
	 * 
	 * @param transTypePPY001MI
	 *            the transTypePPY001MI to set
	 */
	public void setTransTypePPY001MI(String transTypePPY001MI) {
		this.transTypePPY001MI = transTypePPY001MI;
	}

	/**
	 * Gets the web services comparable fields.
	 * 
	 * @return the webServicesComparableFields
	 */
	public String getWebServicesComparableFields() {
		return webServicesComparableFields;
	}

	/**
	 * Sets the web services comparable fields.
	 * 
	 * @param webServicesComparableFields
	 *            the webServicesComparableFields to set
	 */
	public void setWebServicesComparableFields(
			String webServicesComparableFields) {
		this.webServicesComparableFields = webServicesComparableFields;
	}

	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/**
	 * @return the activeDirectoryMap
	 */
	public HashMap<String, ArrayList<String>> getActiveDirectoryMap() {
		return activeDirectoryMap;
	}

	/**
	 * @param activeDirectoryMap
	 *            the activeDirectoryMap to set
	 */
	public void setActiveDirectoryMap(
			HashMap<String, ArrayList<String>> activeDirectoryMap) {
		this.activeDirectoryMap = activeDirectoryMap;
	}

	/**
	 * @return the activeMenuDirectoryMap
	 */
	public HashMap<String, ArrayList<String>> getActiveMenuDirectoryMap() {
		return activeMenuDirectoryMap;
	}

	/**
	 * @param activeMenuDirectoryMap
	 *            the activeMenuDirectoryMap to set
	 */
	public void setActiveMenuDirectoryMap(
			HashMap<String, ArrayList<String>> activeMenuDirectoryMap) {
		this.activeMenuDirectoryMap = activeMenuDirectoryMap;
	}

	/**
	 * @return the m3Connectionflag
	 */
	public boolean isM3Connectionflag() {
		return m3Connectionflag;
	}

	/**
	 * @param connectionflag
	 *            the m3Connectionflag to set
	 */
	public void setM3Connectionflag(boolean connectionflag) {
		m3Connectionflag = connectionflag;
	}

	/**
	 * @return the distinctActiveDirectoryGroup
	 */
	public ArrayList<String> getDistinctActiveDirectoryGroup() {
		return distinctActiveDirectoryGroup;
	}

	/**
	 * @param distinctActiveDirectoryGroup
	 *            the distinctActiveDirectoryGroup to set
	 */
	public void setDistinctActiveDirectoryGroup(
			ArrayList<String> distinctActiveDirectoryGroup) {
		this.distinctActiveDirectoryGroup = distinctActiveDirectoryGroup;
	}

	/**
	 * @return ldapUrl
	 */
	public String getLdapUrl() {
		return ldapUrl;
	}

	/**
	 * @param ldapUrl
	 */
	public void setLdapUrl(String ldapUrl) {
		this.ldapUrl = ldapUrl;
	}

	/**
	 * @return ldapSecurity
	 */
	public String getLdapSecurity() {
		return ldapSecurity;
	}

	/**
	 * @param ldapSecurity
	 */
	public void setLdapSecurity(String ldapSecurity) {
		this.ldapSecurity = ldapSecurity;
	}
	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011

	/**
	 * @return the pabDC
	 */
	public String getPabDC() {
		return pabDC;
	}

	/**
	 * @param pabDC the pabDC to set
	 */
	public void setPabDC(String pabDC) {
		this.pabDC = pabDC;
	}

	/**
	 * @return the ldapUrlSecond
	 */
	public String getLdapUrlSecond() {
		return ldapUrlSecond;
	}

	/**
	 * @param ldapUrlSecond the ldapUrlSecond to set
	 */
	public void setLdapUrlSecond(String ldapUrlSecond) {
		this.ldapUrlSecond = ldapUrlSecond;
	}

    public String getM3Server() {
        return m3Server;
    }

    public void setM3Server(String m3Server) {
        this.m3Server = m3Server;
    }

    public String getM3Port() {
        return m3Port;
    }

    public void setM3Port(String m3Port) {
        this.m3Port = m3Port;
    }

    public void setWelcomeMessageText(String text) {
        welcomeMessage.setText(text);
    }

    public void setWelcomeMessageType(String type) {
        welcomeMessage.setType(type);
    }

    public WelcomeMessage getWelcomeMessage() {
        return welcomeMessage;
    }
    public void setWelcomeMessage(WelcomeMessage welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	public String getDynamicMessagePath() {
		return dynamicMessagePath;
	}

	public void setDynamicMessagePath(String dynamicMessagePath) {
		this.dynamicMessagePath = dynamicMessagePath;
	}
}