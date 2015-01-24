package com.aaa.util;

import java.io.PrintStream;
import java.util.Map;

public class SystemInfo
{

	public static boolean isWindows()
	{
		if (System.getProperty("os.name").toLowerCase().indexOf("window") > -1) {
			return true;
		}
		return false;
	}

	public static String getcomputerName()
	{
		Map map = System.getenv();
		//    System.out.println((String)map.get("USERNAME"));
		//    System.out.println((String)map.get("COMPUTERNAME"));
		//    System.out.println((String)map.get("USERDOMAIN"));
		if (isWindows()) {
			return (String)map.get("COMPUTERNAME");
		}else {
			return System.getProperty("os.name")+"\\"+System.getProperty("user.name");
		}
		
	}
}