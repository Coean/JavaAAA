package com.aaa.clientpc;

import com.aaa.gui.Client;
import com.aaa.model.GetMessageRequestInfo;
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

public class GetMessage
{
  private GetMessageRequestInfo getMessageRequestInfo;

  public GetMessage(GetMessageRequestInfo getMessageRequestInfo)
  {
    this.getMessageRequestInfo = getMessageRequestInfo;
  }

  public Map<String, String> getmessage() {
    try {
      String UID = this.getMessageRequestInfo.getUID().toLowerCase();
      String Key = MD5.GetMD5Code(UID).substring(0, 8);
      byte[] desKey = Key.getBytes();
      byte[] desIV = desKey;
      CryptoTools cryptoTools = new CryptoTools(desKey, desIV);
      String PW = this.getMessageRequestInfo.getPW();
      PW = cryptoTools.encode(PW);
      InputStream xmlStream = Client.class.getClassLoader()
        .getResourceAsStream("xml/GetMessage.xml");
      byte[] xmldata = StreamTool.getbyte(xmlStream);
      String xmlString = new String(xmldata, "UTF-8");
      xmlString = xmlString.replaceAll("\\$UID", this.getMessageRequestInfo.getUID())
        .replaceAll("\\$PW", PW)
        .replaceAll("\\$PublicMessageID", this.getMessageRequestInfo.getPublicMessageID());
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
        "\"http://tempuri.org/GetMessage\"");
      conn.getOutputStream().write(data);
      if (conn.getResponseCode() == 200) {
        String xmlResult = StreamTool.getString(conn.getInputStream());
        return parseGetMessage(xmlResult);
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
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  private Map<String, String> parseGetMessage(String xml) throws DocumentException {
    Map maps = new HashMap();
    Document document = DocumentHelper.parseText(xml);
    Element root = document.getRootElement();
    Element target = root.element("Body").element("GetMessageResponse")
      .element("GetMessageResult");
    for (Iterator it = target.elementIterator(); it.hasNext(); ) {
      Element element = (Element)it.next();
      if (element.getName().equals("MessageInfo"))
      {
        for (Iterator child = element.elementIterator(); child.hasNext(); )
        {
          Element childElement = (Element)child.next();
          maps.put(childElement.getName(), childElement.getText());
        }
      }
    }

    target = root.element("Body").element("GetMessageResponse");
    for (Iterator it = target.elementIterator(); it.hasNext(); )
    {
      Element element = (Element)it.next();
      maps.put(element.getName(), element.getText());
    }

    return maps;
  }
}