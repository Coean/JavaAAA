package com.aaa.model;

public class KeepSessionRequestInfo
{
  private String UserID;
  private String UserIP;
  private String Token;
  private String IsHaveNewMessage;

  public KeepSessionRequestInfo(String userID, String userIP, String token, String isHaveNewMessage)
  {
    this.UserID = userID;
    this.UserIP = userIP;
    this.Token = token;
    this.IsHaveNewMessage = isHaveNewMessage;
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
  public String getIsHaveNewMessage() {
    return this.IsHaveNewMessage;
  }
  public void setIsHaveNewMessage(String isHaveNewMessage) {
    this.IsHaveNewMessage = isHaveNewMessage;
  }
}