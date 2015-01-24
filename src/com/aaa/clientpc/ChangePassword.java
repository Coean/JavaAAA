package com.aaa.clientpc;

import com.aaa.gui.Client;
import com.aaa.model.ChangePasswordRequestInfo;
import com.aaa.model.ChangePasswordResult;
import com.aaa.util.CryptoTools;
import com.aaa.util.MD5;
import com.aaa.util.StreamTool;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ChangePassword
{
  private ChangePasswordRequestInfo changePasswordRequestInfo;

  public ChangePassword(ChangePasswordRequestInfo changePasswordRequestInfo)
  {
    this.changePasswordRequestInfo = changePasswordRequestInfo;
  }

  public ChangePasswordResult changepassword() throws Exception
  {
    String UID = this.changePasswordRequestInfo.getUID();
    String OPW = this.changePasswordRequestInfo.getOPW();
    String NPW = this.changePasswordRequestInfo.getNPW();
    byte[] desKey = MD5.GetMD5Code(UID.toLowerCase()).substring(0, 8).getBytes();
    byte[] desIV = desKey;
    CryptoTools cryptoTools = new CryptoTools(desKey, desIV);
    OPW = cryptoTools.encode(OPW);
    NPW = cryptoTools.encode(NPW);
    try {
      InputStream xmlStream = Client.class.getClassLoader()
        .getResourceAsStream("xml/ChangePassword.xml");
      byte[] xmldata = StreamTool.getbyte(xmlStream);
      String xmlString = new String(xmldata, "UTF-8");
      xmlString = xmlString.replaceAll("\\$UID", UID)
        .replaceAll("\\$OPW", OPW)
        .replaceAll("\\$NPW", NPW);
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
        "\"http://tempuri.org/ChangePassword\"");
      conn.getOutputStream().write(data);
      if (conn.getResponseCode() == 200) {
        String result = StreamTool.getString(conn.getInputStream());
        return parseChangePW(result);
      }

      JOptionPane.showMessageDialog(null, "连接服务器超时，修改密码失败！", "修改失败", 0);
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

  private ChangePasswordResult parseChangePW(String xml) throws DocumentException {
    Map maps = new HashMap();
    Document document = DocumentHelper.parseText(xml);
    Element root = document.getRootElement();
    Element target = root.element("Body").element("ChangePasswordResponse");
    for (Iterator it = target.elementIterator(); it.hasNext(); ) {
      Element element = (Element)it.next();
      maps.put(element.getName(), element.getText());
    }
    ChangePasswordResult changePasswordResult = new ChangePasswordResult();
    changePasswordResult.setChangePasswordResult((String)maps.get("ChangePasswordResult"));
    return changePasswordResult;
  }
}