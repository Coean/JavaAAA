package com.aaa.clientpc;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.aaa.gui.Client;
import com.aaa.model.GetLogRecordResult;
import com.aaa.util.StreamTool;

public class GetLogRecord {
	public List loginlog(String uid,String pw) throws Exception, IOException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS"+"+08:00");
		
		InputStream xmlStream = Client.class.getClassLoader()
		        .getResourceAsStream("xml/GetLogRecord.xml");
		byte[] xmldata = StreamTool.getbyte(xmlStream);
	    String xmlString = new String(xmldata, "UTF-8");
	    xmlString = xmlString.replaceAll("\\$UID", uid)
	    		 .replaceAll("\\$PW", pw)
	    		 .replaceAll("\\$D", format.format(System.currentTimeMillis()));
	    byte[] data = xmlString.getBytes();
	    
//	    System.setProperty("proxySet", "true");
//	    System.setProperty("http.proxyHost", "127.0.0.1");
//	    System.setProperty("http.proxyPort", "8888");
	    
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
	        "\"http://tempuri.org/GetLogRecord\"");
	      conn.getOutputStream().write(data);
	      if (conn.getResponseCode() == 200) {
	        //String xmlResult = parselog(StreamTool.getString(conn.getInputStream()));
	    	  List<GetLogRecordResult> loglist = parsedom4j(StreamTool.getString(conn.getInputStream()));
	    	  return loglist;
	      }
	      else
	    	  JOptionPane.showMessageDialog(null, "连接服务器超时，查询失败", "查询失败",0);
		return null;
	}
	
	private String parselog(String xml) throws DocumentException{
		int logbeginindex = xml.indexOf("rowOrder=\"0\"");
		int logendindex = xml.indexOf("</LogRecord>");
		String log1 = xml.substring(logbeginindex+13, logendindex);
		return log1;
	}
	private List parsedom4j(String xml) throws DocumentException{
		Document document = DocumentHelper.parseText(xml);
		//System.out.println(document.asXML());
		Element root = document.getRootElement();
		Element targer = root.element("Body").element("GetLogRecordResponse")
				.element("GetLogRecordResult").element("diffgram").element("DocumentElement");
		Iterator iterator = targer.elementIterator("LogRecord");
		List<GetLogRecordResult> loglist = new ArrayList<GetLogRecordResult>();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"+"+08:00");
		SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"+"+08:00");
		SimpleDateFormat format2 = new SimpleDateFormat("MM-dd HH:mm:ss");
		while (iterator.hasNext()) {
			Element elementlog = (Element) iterator.next();
			GetLogRecordResult logresult = new GetLogRecordResult();
			logresult.setUserid(elementlog.elementTextTrim("用户名"));
			try {
				logresult.setLogintime(format2.format(format3.parse(elementlog.elementTextTrim("登录时间"))));
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				try {
					logresult.setLogintime(format2.format(format1.parse(elementlog.elementTextTrim("登录时间"))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			logresult.setLoginmethod(elementlog.elementTextTrim("登录方式"));
			
			String logouttime = elementlog.elementTextTrim("注销时间");
			if (logouttime!=null) {
				try {
					logresult.setLogouttime(format2.format(format3.parse(logouttime)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					try {
						logresult.setLogouttime(format2.format(format1.parse(logouttime)));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
			logresult.setLogoutmethod(elementlog.elementTextTrim("注销方式"));
			logresult.setOnlinetime(elementlog.elementTextTrim("在线秒数"));
			logresult.setIp(elementlog.elementTextTrim("IP地址"));
			logresult.setMac(elementlog.elementTextTrim("MAC地址"));
			logresult.setComputername(elementlog.elementTextTrim("计算机名"));
			logresult.setSoftversion(elementlog.elementTextTrim("软件版本"));
			logresult.setOsversion(elementlog.elementTextTrim("系统版本"));
			loglist.add(logresult);
		}
		return loglist;
	}
}
