package com.aaa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class GetFileInfo {
	//计算文件的MD5值
	 public static String getMd5ByFile(File file) throws FileNotFoundException {
	        String value = null;
	        FileInputStream in = new FileInputStream(file);
		try {
		    MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		    MessageDigest md5 = MessageDigest.getInstance("MD5");
		    md5.update(byteBuffer);
		    BigInteger bi = new BigInteger(1, md5.digest());
		    value = bi.toString(16).toUpperCase();
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
	            if(null != in) {
		            try {
			        in.close();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
		    }
		}
		return value;
	    }
	 //获取文件的版本信息
	 public static String getFileVersion(File file) {
	        RandomAccessFile raf = null;
	        byte[] buffer;
	        String str;
	        try {
	            raf = new RandomAccessFile(file, "r");
	            buffer = new byte[64];
	            raf.read(buffer);
	            str = "" + (char)buffer[0] + (char)buffer[1];
	            if(!"MZ".equals(str)) {
	                return "false";
	            }

	            int peOffset = unpack(new byte[]{buffer[60], buffer[61], buffer[62], buffer[63]});
	            if(peOffset < 64) {
	                return "false";
	            }
	            
	            raf.seek(peOffset);
	            buffer = new byte[24];
	            raf.read(buffer);
	            str = "" + (char)buffer[0] + (char)buffer[1];
	            if(!"PE".equals(str)) {
	                return "false";
	            }
	            int machine = unpack(new byte[]{buffer[4], buffer[5]});    
	            if(machine != 332) {
	                return "false";
	            }
	            
	            int noSections = unpack(new byte[]{buffer[6], buffer[7]});
	            int optHdrSize = unpack(new byte[]{buffer[20], buffer[21]});
	            raf.seek(raf.getFilePointer() + optHdrSize);
	            boolean resFound = false;
	            for(int i=0; i < noSections; i++) {
	                buffer = new byte[40];
	                raf.read(buffer);
	                str = "" + (char)buffer[0] + (char)buffer[1] +
	                        (char)buffer[2] + (char)buffer[3] + (char)buffer[4];
	                if(".rsrc".equals(str)) {
	                    resFound = true;
	                    break;
	                }
	            }
	            if(!resFound) {
	                return "false";
	            }
	            
	            int infoVirt = unpack(new byte[]{buffer[12], buffer[13], buffer[14], buffer[15]});
	            int infoSize = unpack(new byte[]{buffer[16], buffer[17], buffer[18], buffer[19]});
	            int infoOff = unpack(new byte[]{buffer[20], buffer[21], buffer[22], buffer[23]});
	            raf.seek(infoOff);
	            buffer = new byte[infoSize];
	            raf.read(buffer);
	            int nameEntries = unpack(new byte[]{buffer[12], buffer[13]});
	            int idEntries = unpack(new byte[]{buffer[14], buffer[15]});
	            boolean infoFound = false;
	            int subOff = 0;
	            for(int i=0; i < (nameEntries+idEntries); i++) {
	                int type = unpack(new byte[]{buffer[i*8+16], buffer[i*8+17], buffer[i*8+18], buffer[i*8+19]});
	                if(type == 16) {                          //FILEINFO resource
	                    infoFound = true;
	                    subOff = unpack(new byte[]{buffer[i*8+20], buffer[i*8+21], buffer[i*8+22], buffer[i*8+23]});
	                    break;
	                }
	            }
	            if(!infoFound) {
	                return "false";
	            }
	            
	            subOff = subOff&0x7fffffff;
	            infoOff = unpack(new byte[]{buffer[subOff+20], buffer[subOff+21], buffer[subOff+22], buffer[subOff+23]}); //offset of first FILEINFO
	            infoOff = infoOff&0x7fffffff;
	            infoOff = unpack(new byte[]{buffer[infoOff+20], buffer[infoOff+21], buffer[infoOff+22], buffer[infoOff+23]});    //offset to data
	            int dataOff = unpack(new byte[]{buffer[infoOff], buffer[infoOff+1], buffer[infoOff+2], buffer[infoOff+3]});
	            dataOff = dataOff - infoVirt;
	            
	            int version1 = unpack(new byte[]{buffer[dataOff+48], buffer[dataOff+48+1]});
	            int version2 = unpack(new byte[]{buffer[dataOff+48+2], buffer[dataOff+48+3]});
	            int version3 = unpack(new byte[]{buffer[dataOff+48+4], buffer[dataOff+48+5]});
	            int version4 = unpack(new byte[]{buffer[dataOff+48+6], buffer[dataOff+48+7]});
	            String fileversion =version2 + "." + version1 + "." + version4 + "." + version3;    
	            return fileversion;
	            
	        } catch (FileNotFoundException e) {
	            return "false";
	        } catch (IOException e) {
	            return "false";
	        } finally {
	            if(raf != null) {
	                try {
	                    raf.close();
	                } catch (IOException e) {
	                }
	            }
	        }
	    }
	    
	    public static int unpack(byte[] b) {
	        int num = 0;
	        for(int i=0; i < b.length; i++) {
	            num = 256 * num + (b[b.length - 1 - i]&0xff);
	        }
	        return num;
	    }
}
