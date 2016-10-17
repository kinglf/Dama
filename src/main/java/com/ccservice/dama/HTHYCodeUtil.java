package com.ccservice.dama;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import org.apache.commons.codec.binary.Base64;


public class HTHYCodeUtil {

    /**
     * �ǿ�  
     * @param str
     * @return
     * @time 2015��1��18�� ����3:51:12
     * @author fiend
     */
    public static boolean isRealEnnull(String str) {
        if (str == null) {
            return false;
        }
        if ("".equals("str")) {
            return false;
        }
        return true;
    }

    public static String getDLLPATH() {
        String dllpath1 = HTHYCodeUtil.class.getResource("/").toString().replace("file:/", "");
        return dllpath1;
    }

    /** 
     * ���ļ����б��� 
     * @param file ��Ҫ������ʼ� 
     * @return ���ļ�����base64�������ַ��� 
     * @throws Exception 
     */
    public static String file2String(File file) throws Exception {
        StringBuffer sb = new StringBuffer();
        FileInputStream in = new FileInputStream(file);
        int b;
        char ch;
        while ((b = in.read()) != -1) {
            ch = (char) b;
            sb.append(ch);
        }
        in.close();
        //��bufferת��Ϊstring  
        String oldString = new String(sb);

        //ʹ��base64����  
        String newString = compressData(oldString);

        return newString;
    }

    /** 
     * ���ļ����н��� 
     * @param oldString ��Ҫ������ַ��� 
     * @param filePath  ���ַ������뵽filepath�ļ�·�� 
     * @return  ���ؽ����õ����ļ� 
     * @throws Exception 
     */
    public static File string2File(String oldString, String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("�ļ��Ѿ����ڣ����ܽ�base64����ת��Ϊ�ļ�");
            return null;
        }
        else {
            file.createNewFile();
        }
        FileOutputStream out = new FileOutputStream(file);

        //��oldString���н���  
        String newString = decompressData(oldString);

        //���ʼ�����תΪbyte[]  
        char[] str = newString.toCharArray();
        for (char ch : str) {
            byte b = (byte) ch;
            out.write(b);
        }
        out.close();
        return file;
    }

    /** 
     * base64--->byte[] ���� 
     * @param oldString ��Ҫ������ַ��� 
     * @return  byte[] 
     * @throws Exception 
     */
    public static byte[] base64tobyte(String oldString) throws Exception {
        //��oldString���н���  
        String newString = decompressData(oldString);
        byte[] bt = newString.getBytes("UTF-8");
        return bt;
    }

    /** 
     * ʹ��base64�����ַ��� 
     * @param data 
     * @return �������ַ��� 
     */
    public static String compressData(String data) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DeflaterOutputStream zos = new DeflaterOutputStream(bos);
            zos.write(data.getBytes());
            zos.close();
            return new String(getenBASE64inCodec(bos.toByteArray()));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "ZIP_ERR";
        }
    }

    /** 
     * ʹ��base64�����ַ��� 
     * @param encdata 
     * @return �������ַ��� 
     */
    public static String decompressData(String encdata) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            InflaterOutputStream zos = new InflaterOutputStream(bos);
            zos.write(getdeBASE64inCodec(encdata));
            zos.close();
            return new String(bos.toByteArray());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "UNZIP_ERR";
        }
    }

    /** 
     * ����apache�ı��뷽�� 
     */
    public static String getenBASE64inCodec(byte[] b) {
        if (b == null)
            return null;
        return new String((new Base64()).encode(b));
    }

    /** 
     * ����apache�Ľ��뷽�� 
     * @throws UnsupportedEncodingException 
     */
    public static byte[] getdeBASE64inCodec(String s) throws UnsupportedEncodingException {
        if (s == null)
            return null;
        return new Base64().decode(s.getBytes());
    }

    /**
     * inputstream  to  byte[] 
     * @param inStream
     * @return
     * @throws IOException
     * @time 2015��1��23�� ����3:35:27
     * @author fiend
     */
    public static byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    public static File byte2File(byte[] bytes, String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("�ļ��Ѿ�����");
            return null;
        }
        else {
            file.createNewFile();
        }
        FileOutputStream out = new FileOutputStream(file);
        out.write(bytes);
        out.close();
        return file;
    }

}
