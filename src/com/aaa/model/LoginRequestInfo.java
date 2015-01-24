package com.aaa.model;

public class LoginRequestInfo
{
  private String ErrInfo;
  private String UserID;
  private String UserPW;
  private String UserIP;
  private String ComputerName;
  private String MAC;
  private String IsAutoLogin;
  private String ClientVersion;
  private String OSVersion;

  public LoginRequestInfo(String errInfo, String userID, String userPW, String userIP, String computerName, String mAC, String isAutoLogin, String clientVersion, String oSVersion)
  {
    this.ErrInfo = errInfo;
    this.UserID = userID;
    this.UserPW = userPW;
    this.UserIP = userIP;
    this.ComputerName = computerName;
    this.MAC = mAC;
    this.IsAutoLogin = isAutoLogin;
    this.ClientVersion = clientVersion;
    this.OSVersion = oSVersion;
  }
  public String getErrInfo() {
    return this.ErrInfo;
  }
  public void setErrInfo(String errInfo) {
    this.ErrInfo = errInfo;
  }
  public String getUserID() {
    return this.UserID;
  }
  public void setUserID(String userID) {
    this.UserID = userID;
  }
  public String getUserPW() {
    return this.UserPW;
  }
  public void setUserPW(String userPW) {
    this.UserPW = userPW;
  }
  public String getUserIP() {
    return this.UserIP;
  }
  public void setUserIP(String userIP) {
    this.UserIP = userIP;
  }
  public String getComputerName() {
    return this.ComputerName;
  }
  public void setComputerName(String computerName) {
    this.ComputerName = computerName;
  }
  public String getMAC() {
    return this.MAC;
  }
  public void setMAC(String mAC) {
    this.MAC = mAC;
  }
  public String getIsAutoLogin() {
    return this.IsAutoLogin;
  }
  public void setIsAutoLogin(String isAutoLogin) {
    this.IsAutoLogin = isAutoLogin;
  }
  public String getClientVersion() {
    return this.ClientVersion;
  }
  public void setClientVersion(String clientVersion) {
    this.ClientVersion = clientVersion;
  }
  public String getOSVersion() {
    return this.OSVersion;
  }
  public void setOSVersion(String oSVersion) {
    this.OSVersion = oSVersion;
  }
}