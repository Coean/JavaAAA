package com.aaa.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class ExecuteCommond {
	public String ExecuteCommond(String cmd) {
		String result = "";
		try {
            Process p = Runtime.getRuntime().exec(cmd);
//            InputStreamReader ins = new InputStreamReader(p.getInputStream());
//            LineNumberReader input = new LineNumberReader(ins);
//            String line;
//            while ((line = input.readLine()) != null) {
//                result += line;
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return result;
	}
}
