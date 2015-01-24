package com.aaa.model;

public class ChangePasswordRequestInfo
{
  private String UID;
  private String OPW;
  private String NPW;

  public ChangePasswordRequestInfo(String uID, String oPW, String nPW)
  {
    this.UID = uID;
    this.OPW = oPW;
    this.NPW = nPW;
  }
  public String getUID() {
    return this.UID;
  }
  public void setUID(String uID) {
    this.UID = uID;
  }
  public String getOPW() {
    return this.OPW;
  }
  public void setOPW(String oPW) {
    this.OPW = oPW;
  }
  public String getNPW() {
    return this.NPW;
  }
  public void setNPW(String nPW) {
    this.NPW = nPW;
  }
}