package com.aaa.clientpc;

import com.aaa.gui.Client;
import com.aaa.model.LogoutRequestInfo;
import com.aaa.model.LogoutResult;
import com.aaa.util.StreamTool;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Logout
{
  private LogoutRequestInfo logoutRequestInfo;

  public Logout(LogoutRequestInfo logoutRequestInfo)
  {
    this.logoutRequestInfo = logoutRequestInfo;
  }

  public LogoutResult logout()
  {
    try
    {
      InputStream xmlStream = Client.class.getClassLoader()
        .getResourceAsStream("xml/Logout.xml");
      byte[] xmldata = StreamTool.getbyte(xmlStream);
      String xmlString = new String(xmldata, "UTF-8");
      xmlString = xmlString.replaceAll("\\$UserID", this.logoutRequestInfo.getUserID())
        .replaceAll("\\$UserIP", this.logoutRequestInfo.getUserIP())
        .replaceAll("\\$Token", this.logoutRequestInfo.getToken());
      byte[] data = xmlString.getBytes();
      String path = "http://100.0.0.10/NSUAAAWS/Default.asmx";
      HttpURLConnection conn = (HttpURLConnection)new URL(path)
        .openConnection();
      conn.setConnectTimeout(5000);
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);
      conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
      conn.setRequestProperty("Content-Length", 
        String.valueOf(data.length));
      conn.setRequestProperty("SOAPAction", 
        "\"http://tempuri.org/Logout\"");
      conn.getOutputStream().write(data);
      if (conn.getResponseCode() == 200) {
        String result = StreamTool.getString(conn.getInputStream());
        return parseLogout(result);
      }

      JOptionPane.showMessageDialog(null, "连接服务器超时，注销失败！", "注销失败", 
        0);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (DocumentException e) {
      e.printStackTrace();
    }

    return null;
  }

  private LogoutResult parseLogout(String xml) throws DocumentException {
    Document document = DocumentHelper.parseText(xml);
    Element root = document.getRootElement();
    Element target = root.element("Body").element("LogoutResponse")
      .element("LogoutResult");
    LogoutResult logoutResult = new LogoutResult();
    logoutResult.setLogoutResult(target.getText());
    return logoutResult;
  }
}