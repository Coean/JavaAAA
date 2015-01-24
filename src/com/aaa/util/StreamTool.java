package com.aaa.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamTool
{
  public static byte[] getbyte(InputStream in)
    throws IOException
  {
    byte[] buffer = new byte[1024];
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    int len;
    while ((len = in.read(buffer)) != -1)
    {
      //int len;
      bos.write(buffer, 0, len);
    }
    bos.close();
    return bos.toByteArray();
  }

  public static String getString(InputStream in)
    throws IOException
  {
    byte[] buffer = new byte[1024];
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    int len;
    while ((len = in.read(buffer)) != -1)
    {
      //int len;
      bos.write(buffer, 0, len);
    }
    bos.close();
    return new String(bos.toByteArray(), "UTF-8");
  }
}