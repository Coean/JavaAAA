package com.aaa.model;

public class LogoutRequestInfo
{
  private String UserID;
  private String UserIP;
  private String Token;

  public LogoutRequestInfo(String userID, String userIP, String token)
  {
    this.UserID = userID;
    this.UserIP = userIP;
    this.Token = token;
  }
  public String getUserID() {
    return this.UserID;
  }
  public void setUserID(String userID) {
    this.UserID = userID;
  }
  public String getUserIP() {
    return this.UserIP;
  }
  public void setUserIP(String userIP) {
    this.UserIP = userIP;
  }
  public String getToken() {
    return this.Token;
  }
  public void setToken(String token) {
    this.Token = token;
  }
}