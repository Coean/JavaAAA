package com.aaa.model;

public class GetLogRecordResult {
	private String userid = null;
	private String logintime = null;
	private String loginmethod = null;
	private String logouttime = "               ";
	private String logoutmethod = null;
	private String onlinetime = "                 ";
	private String ip = null;
	private String mac = null;
	private String computername = null;
	private String softversion = null;
	private String osversion = null;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getLogintime() {
		return logintime;
	}
	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}
	public String getLoginmethod() {
		return loginmethod;
	}
	public void setLoginmethod(String loginmethod) {
		this.loginmethod = loginmethod;
	}
	public String getLogouttime() {
		return logouttime;
	}
	public void setLogouttime(String logouttime) {
		this.logouttime = logouttime;
	}
	public String getLogoutmethod() {
		return logoutmethod;
	}
	public void setLogoutmethod(String logoutmethod) {
		this.logoutmethod = logoutmethod;
	}
	public String getOnlinetime() {
		return onlinetime;
	}
	public void setOnlinetime(String onlinetime) {
		this.onlinetime = onlinetime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getComputername() {
		return computername;
	}
	public void setComputername(String computername) {
		this.computername = computername;
	}
	public String getSoftversion() {
		return softversion;
	}
	public void setSoftversion(String softversion) {
		this.softversion = softversion;
	}
	public String getOsversion() {
		return osversion;
	}
	public void setOsversion(String osversion) {
		this.osversion = osversion;
	}
	
	
}
