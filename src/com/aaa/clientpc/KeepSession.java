package com.aaa.clientpc;

import com.aaa.gui.Client;
import com.aaa.model.KeepSessionRequestInfo;
import com.aaa.model.KeepSessionResult;
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

public class KeepSession
{
	private KeepSessionRequestInfo keepSessionRequestInfo;

	public KeepSession()
	{
	}

	public KeepSession(KeepSessionRequestInfo keepSessionRequestInfo)
	{
		this.keepSessionRequestInfo = keepSessionRequestInfo;
	}

	public KeepSessionResult keepsession()
	{
		try {
			InputStream xmlStream = Client.class.getClassLoader()
					.getResourceAsStream("xml/KeepSession.xml");
			byte[] xmldata = StreamTool.getbyte(xmlStream);
			String xmlString = new String(xmldata, "UTF-8");
			xmlString = xmlString.replaceAll("\\$UserID", this.keepSessionRequestInfo.getUserID())
					.replaceAll("\\$UserIP", this.keepSessionRequestInfo.getUserIP())
					.replaceAll("\\$Token", this.keepSessionRequestInfo.getToken())
					.replaceAll("\\$IsHaveNewMessage", this.keepSessionRequestInfo.getIsHaveNewMessage());
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
					"\"http://tempuri.org/KeepSession\"");
			conn.getOutputStream().write(data);
			if (conn.getResponseCode() == 200) {
				try {
					String result = StreamTool.getString(conn.getInputStream());
					return parseKeepSession(result);
				}
				catch (DocumentException e)
				{
					e.printStackTrace();
				}
			}
			else
				JOptionPane.showMessageDialog(null, "与服务器连接超时，请重新登录！", "连接超时", 0);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private KeepSessionResult parseKeepSession(String xml) throws DocumentException {
		Map maps = new HashMap();
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		Element target = root.element("Body").element("KeepSessionResponse");
		for (Iterator it = target.elementIterator(); it.hasNext(); )
		{
			Element element = (Element)it.next();
			maps.put(element.getName(), element.getText());
		}
		KeepSessionResult keepSessionResult = new KeepSessionResult();
		keepSessionResult.setIsHaveNewMessage((String)maps.get("IsHaveNewMessage"));
		keepSessionResult.setKeepSessionResult((String)maps.get("KeepSessionResult"));
		return keepSessionResult;
	}
}