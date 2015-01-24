package com.aaa.util;

import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5
{
  private static final String[] strDigits = { "0", "1", "2", "3", "4", "5", 
    "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

  private static String byteToArrayString(byte bByte)
  {
    int iRet = bByte;

    if (iRet < 0) {
      iRet += 256;
    }
    int iD1 = iRet / 16;
    int iD2 = iRet % 16;
    return strDigits[iD1] + strDigits[iD2]; } 
  // ERROR //
//  private static String byteToNum(byte bByte) { // Byte code:
//       0: iload_0
//       1: istore_1
//       2: getstatic 80	java/lang/System:out	Ljava/io/PrintStream;
//       5: new 55	java/lang/StringBuilder
//       8: dup
//       9: ldc 86
//       11: invokespecial 61	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
//       14: iload_1
//       15: invokevirtual 88	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
//       18: invokevirtual 68	java/lang/StringBuilder:toString	()Ljava/lang/String;
//       21: invokevirtual 91	java/io/PrintStream:println	(Ljava/lang/String;)V
//       24: iload_1
//       25: ifge +9 -> 34
//       28: wide
//       34: iload_1
//       35: invokestatic 96	java/lang/String:valueOf	(I)Ljava/lang/String;
//       38: areturn 
//	  } 
  private static String byteToString(byte[] bByte) { StringBuffer sBuffer = new StringBuffer();
    for (int i = 0; i < bByte.length; i++) {
      sBuffer.append(byteToArrayString(bByte[i]));
    }
    return sBuffer.toString(); }

  public static String GetMD5Code(String strObj)
  {
    String resultString = null;
    try {
      resultString = new String(strObj);
      MessageDigest md = MessageDigest.getInstance("MD5");

      resultString = byteToString(md.digest(strObj.getBytes()));
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
    }
    return resultString.toUpperCase();
  }

  public static void main(String[] args)
  {
    String value = "A1D11538BD93D2D71E12125B7A97ED58";
    String result = GetMD5Code(value);
    //System.out.println(result);
  }
}