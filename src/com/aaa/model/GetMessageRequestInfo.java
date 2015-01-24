package com.aaa.model;

public class GetMessageRequestInfo
{
  private String ErrInfo;
  private String UID;
  private String PW;
  private String PublicMessageID;

  public GetMessageRequestInfo()
  {
  }

  public GetMessageRequestInfo(String errInfo, String uID, String pW, String publicMessageID)
  {
    this.ErrInfo = errInfo;
    this.UID = uID;
    this.PW = pW;
    this.PublicMessageID = publicMessageID;
  }
  public String getErrInfo() {
    return this.ErrInfo;
  }
  public void setErrInfo(String errInfo) {
    this.ErrInfo = errInfo;
  }
  public String getUID() {
    return this.UID;
  }
  public void setUID(String uID) {
    this.UID = uID;
  }
  public String getPW() {
    return this.PW;
  }
  public void setPW(String pW) {
    this.PW = pW;
  }
  public String getPublicMessageID() {
    return this.PublicMessageID;
  }
  public void setPublicMessageID(String publicMessageID) {
    this.PublicMessageID = publicMessageID;
  }
}