package com.aaa.model;

public class LoginResult
{
	private String NetGroup;
	private String ExpireTime;
	private String Name;
	private String Token;
	private String IsLogin;
	private String IsExpire;
	private String IsIDPWWrong;
	private String IsNeedUpdate;
	private String IsIPInvalid;
	private String IsDisable;
	private String PublicMessageID;
	private String AdminGroup;
	private String Notice;
	private String UserId;

	public LoginResult()
	{
	}

	public LoginResult(String AdminGroup,String Notice, String UserId, String netGroup, String expireTime, String name, String token, String isLogin, String isExpire, String isIDPWWrong, String isNeedUpdate, String isIPInvalid, String isDisable, String publicMessageID)
	{
		this.AdminGroup = AdminGroup;
		this.Notice = Notice;
		this.UserId = UserId;
		this.NetGroup = netGroup;
		this.ExpireTime = expireTime;
		this.Name = name;
		this.Token = token;
		this.IsLogin = isLogin;
		this.IsExpire = isExpire;
		this.IsIDPWWrong = isIDPWWrong;
		this.IsNeedUpdate = isNeedUpdate;
		this.IsIPInvalid = isIPInvalid;
		this.IsDisable = isDisable;
		this.PublicMessageID = publicMessageID;
	}

	public String getAdminGroup() {
		return AdminGroup;
	}

	public void setAdminGroup(String adminGroup) {
		AdminGroup = adminGroup;
	}

	public String getNotice() {
		return Notice;
	}

	public void setNotice(String notice) {
		Notice = notice;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getNetGroup() {
		return this.NetGroup;
	}
	public void setNetGroup(String netGroup) {
		this.NetGroup = netGroup;
	}
	public String getExpireTime() {
		return this.ExpireTime;
	}
	public void setExpireTime(String expireTime) {
		this.ExpireTime = expireTime;
	}
	public String getName() {
		return this.Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public String getToken() {
		return this.Token;
	}
	public void setToken(String token) {
		this.Token = token;
	}
	public String getIsLogin() {
		return this.IsLogin;
	}
	public void setIsLogin(String isLogin) {
		this.IsLogin = isLogin;
	}
	public String getIsExpire() {
		return this.IsExpire;
	}
	public void setIsExpire(String isExpire) {
		this.IsExpire = isExpire;
	}
	public String getIsIDPWWrong() {
		return this.IsIDPWWrong;
	}
	public void setIsIDPWWrong(String isIDPWWrong) {
		this.IsIDPWWrong = isIDPWWrong;
	}
	public String getIsNeedUpdate() {
		return this.IsNeedUpdate;
	}
	public void setIsNeedUpdate(String isNeedUpdate) {
		this.IsNeedUpdate = isNeedUpdate;
	}
	public String getIsIPInvalid() {
		return this.IsIPInvalid;
	}
	public void setIsIPInvalid(String isIPInvalid) {
		this.IsIPInvalid = isIPInvalid;
	}
	public String getIsDisable() {
		return this.IsDisable;
	}
	public void setIsDisable(String isDisable) {
		this.IsDisable = isDisable;
	}
	public String getPublicMessageID() {
		return this.PublicMessageID;
	}
	public void setPublicMessageID(String publicMessageID) {
		this.PublicMessageID = publicMessageID;
	}
}