package com.aaa.model;

public class GetMessageResult
{
  private String IsPublic;
  private String IsURL;
  private String Content;
  private String MessageID;
  private String Time;
  private String ErrInfo;
  private String PublicMessageID;

  public GetMessageResult()
  {
  }

  public GetMessageResult(String isPublic, String isURL, String content, String messageID, String time, String errInfo, String publicMessageID)
  {
    this.IsPublic = isPublic;
    this.IsURL = isURL;
    this.Content = content;
    this.MessageID = messageID;
    this.Time = time;
    this.ErrInfo = errInfo;
    this.PublicMessageID = publicMessageID;
  }
  public String getIsPublic() {
    return this.IsPublic;
  }
  public void setIsPublic(String isPublic) {
    this.IsPublic = isPublic;
  }
  public String getIsURL() {
    return this.IsURL;
  }
  public void setIsURL(String isURL) {
    this.IsURL = isURL;
  }
  public String getContent() {
    return this.Content;
  }
  public void setContent(String content) {
    this.Content = content;
  }
  public String getMessageID() {
    return this.MessageID;
  }
  public void setMessageID(String messageID) {
    this.MessageID = messageID;
  }
  public String getTime() {
    return this.Time;
  }
  public void setTime(String time) {
    this.Time = time;
  }
  public String getErrInfo() {
    return this.ErrInfo;
  }
  public void setErrInfo(String errInfo) {
    this.ErrInfo = errInfo;
  }
  public String getPublicMessageID() {
    return this.PublicMessageID;
  }
  public void setPublicMessageID(String publicMessageID) {
    this.PublicMessageID = publicMessageID;
  }
}