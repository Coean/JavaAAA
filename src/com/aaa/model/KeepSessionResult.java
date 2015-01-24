package com.aaa.model;

public class KeepSessionResult
{
  private String KeepSessionResult;
  private String IsHaveNewMessage;

  public KeepSessionResult()
  {
  }

  public KeepSessionResult(String keepSessionResult, String isHaveNewMessage)
  {
    this.KeepSessionResult = keepSessionResult;
    this.IsHaveNewMessage = isHaveNewMessage;
  }
  public String getKeepSessionResult() {
    return this.KeepSessionResult;
  }
  public void setKeepSessionResult(String keepSessionResult) {
    this.KeepSessionResult = keepSessionResult;
  }
  public String getIsHaveNewMessage() {
    return this.IsHaveNewMessage;
  }
  public void setIsHaveNewMessage(String isHaveNewMessage) {
    this.IsHaveNewMessage = isHaveNewMessage;
  }
}