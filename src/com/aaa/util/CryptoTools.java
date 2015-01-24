package com.aaa.util;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CryptoTools
{
  private byte[] desKey;
  private byte[] desIV;
  private AlgorithmParameterSpec iv = null;
  private Key key;

  public CryptoTools(byte[] desKey, byte[] desIV)
    throws Exception
  {
    this.desKey = desKey;
    this.desIV = desIV;
    DESKeySpec keySpec = new DESKeySpec(desKey);
    this.iv = new IvParameterSpec(desIV);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    this.key = keyFactory.generateSecret(keySpec);
  }

  public String encode(String data) throws Exception
  {
    Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    enCipher.init(1, this.key, this.iv);
    byte[] pasByte = enCipher.doFinal(data.getBytes("UTF-8"));
    BASE64Encoder base64Encoder = new BASE64Encoder();
    return base64Encoder.encode(pasByte);
  }

  public String decode(String data) throws Exception
  {
    Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    deCipher.init(2, this.key, this.iv);
    BASE64Decoder base64Decoder = new BASE64Decoder();
    byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data));
    return new String(pasByte, "UTF-8");
  }
}