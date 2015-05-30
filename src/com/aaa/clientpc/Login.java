package com.aaa.clientpc;

import com.aaa.gui.Client;
import com.aaa.model.LoginRequestInfo;
import com.aaa.model.LoginResult;
import com.aaa.util.CryptoTools;
import com.aaa.util.MD5;
import com.aaa.util.StreamTool;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Login
{
  private LoginRequestInfo loginRequestInfo;

  public Login(LoginRequestInfo loginRequestInfo)
  {
    this.loginRequestInfo = loginRequestInfo;
  }

  public LoginResult login() throws Exception
  {
    String UserID = this.loginRequestInfo.getUserID();
    String UserPW = this.loginRequestInfo.getUserPW();
    String keyMD5 = MD5.GetMD5Code(UserID);
    String key = keyMD5.substring(0, 8);
    byte[] desKey = key.getBytes();
    byte[] desIV = desKey;
    CryptoTools cryptoTools = new CryptoTools(desKey, desIV);
    UserPW = cryptoTools.encode(UserPW);

    // exceptional handling
    try
    {
      InputStream xmlStream = Client.class.getClassLoader()
        .getResourceAsStream("xml/Login.xml");
      byte[] xmldata = StreamTool.getbyte(xmlStream);
      String xmlString = new String(xmldata, "UTF-8");
      xmlString = xmlString.replaceAll("\\$ErrInfo", this.loginRequestInfo.getErrInfo())
        .replaceAll("\\$UserID", this.loginRequestInfo.getUserID())
        .replaceAll("\\$UserPW", UserPW)
        .replaceAll("\\$UserIP", this.loginRequestInfo.getUserIP())
        .replaceAll("\\$ComputerName", this.loginRequestInfo.getComputerName())
        .replaceAll("\\$MAC", this.loginRequestInfo.getMAC())
        .replaceAll("\\$IsAutoLogin", this.loginRequestInfo.getIsAutoLogin())
        .replaceAll("\\$ClientVersion", this.loginRequestInfo.getClientVersion())
        .replaceAll("\\$OSVersion", this.loginRequestInfo.getOSVersion());
      byte[] data = xmlString.getBytes();

//      System.setProperty("proxySet", "true");
//      System.setProperty("http.proxyHost", "127.0.0.1");
//      System.setProperty("http.proxyPort", "8888");

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
        "\"http://tempuri.org/Login\"");
      conn.getOutputStream().write(data);
      if (conn.getResponseCode() == 200) {
        String xmlResult = StreamTool.getString(conn.getInputStream());
        return parseLogin(xmlResult);
      }

      JOptionPane.showMessageDialog(null, "Á¬½Ó·þÎñÆ÷³¬Ê±£¬µÇÂ¼Ê§°Ü", "µÇÂ¼Ê§°Ü",
        0);
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    catch (MalformedURLException e) {
      e.printStackTrace();
    }
    catch (ProtocolException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (DocumentException e) {
      e.printStackTrace();
    }

    return null;
  }

  private LoginResult parseLogin(String xml) throws DocumentException {
    LoginResult loginResult = new LoginResult();
    Map maps = new HashMap();
    Document document = DocumentHelper.parseText(xml);
    Element root = document.getRootElement();
    Element target = root.element("Body").element("LoginResponse")
      .element("LoginResult");
    for (Iterator it = target.elementIterator(); it.hasNext(); ) {
      Element element = (Element)it.next();
      maps.put(element.getName(), element.getText());
    }
    loginResult.setName((String)maps.get("Name"));
    loginResult.setNetGroup((String)maps.get("NetGroup"));
    loginResult.setExpireTime((String)maps.get("ExpireTime"));
    loginResult.setToken((String)maps.get("Token"));
    loginResult.setIsLogin((String)maps.get("IsLogin"));
    loginResult.setIsExpire((String)maps.get("IsExpire"));
    loginResult.setIsIDPWWrong((String)maps.get("IsIDPWWrong"));
    loginResult.setIsNeedUpdate((String)maps.get("IsNeedUpdate"));
    loginResult.setIsIPInvalid((String)maps.get("IsIPInvalid"));
    loginResult.setIsDisable((String)maps.get("IsDisable"));
    loginResult.setPublicMessageID((String)maps.get("PublicMessageID"));
    loginResult.setUserId((String)maps.get("UserID"));
    loginResult.setNotice((String)maps.get("Notice"));
    loginResult.setAdminGroup((String)maps.get("AdminGroup"));
    return loginResult;
  }
}
