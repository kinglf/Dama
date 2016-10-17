package com.ccservice.dama;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Calendar;

import com.alibaba.fastjson.JSONObject;

public class FileUtil {

    /** 
     * ���ļ����б��� 
     * @param file ��Ҫ������ʼ� 
     * @return ���ļ�����base64�������ַ��� 
     * @throws Exception 
     */
    public static byte[] file2byte(File file) throws Exception {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void main(String[] args) throws Exception {
    	byte[] byte1=file2byte(new File("D:\\08\\1.jpg"));
        String str = HTHYGetCode.getCode(byte1, 1);
        System.out.println(str);
    }

    //��������IP
    public String browserip;

    //��ǰ����������˿�
    public int serverport;


    private void mkdir(String path) {
        File fileParentDir = new File(path);// �ж�logĿ¼�Ƿ����
        if (!fileParentDir.exists()) {
            fileParentDir.mkdirs();

        }
    }

    public static byte[] hexToBytes(String hexString) {
        char[] hex = hexString.toCharArray();
        // תrawData���ȼ���  
        int length = hex.length / 2;
        byte[] rawData = new byte[length];
        for (int i = 0; i < length; i++) {
            // �Ƚ�hexת10��λ��ֵ  
            int high = Character.digit(hex[i * 2], 16);
            int low = Character.digit(hex[i * 2 + 1], 16);
            // ����һ��ֵ�Ķ��Mλֵ��ƽ��4λ,ex: 00001000 => 10000000 (8=>128)  
            // Ȼ����ڶ���ֵ�Ķ���λֵ������ex: 10000000 | 00001100 => 10001100 (137)  
            int value = (high << 4) | low;
            // ��FFFFFFFF������  
            if (value > 127) {
                value -= 256;
            }
            // ���ת��byte��OK  
            rawData[i] = (byte) value;
        }
        return rawData;
    }

    public static final String bytesToHexString(byte[] buf) {
        StringBuilder sb = new StringBuilder(buf.length * 2);
        String tmp = "";
        // ���ֽ�������ÿ���ֽڲ���2λ16��������  
        for (int i = 0; i < buf.length; i++) {
            // 1.  
            // sb.append(Integer.toHexString((buf[i] & 0xf0) >> 4));  
            // sb.append(Integer.toHexString((buf[i] & 0x0f) >> 0));  
            // //////////////////////////////////////////////////////////////////  
            // 2.sodino��ϲ���ķ�ʽ���ٺ�...  
            tmp = Integer.toHexString(0xff & buf[i]);
            tmp = tmp.length() == 1 ? "0" + tmp : tmp;
            sb.append(tmp);
        }
        return sb.toString();
    }

    private String getDateString() {
        Calendar cd = Calendar.getInstance();
        int year = cd.get(Calendar.YEAR);
        String month = addZero(cd.get(Calendar.MONTH) + 1);
        String day = addZero(cd.get(Calendar.DAY_OF_MONTH));
        String hour = addZero(cd.get(Calendar.HOUR_OF_DAY));
        String min = addZero(cd.get(Calendar.MINUTE));
        String sec = addZero(cd.get(Calendar.SECOND));
        String mill = addZero(cd.get(Calendar.MILLISECOND));
        String path = year + month + "/" + day + "/" + hour;
        return path;
    }

    /**
     * ����iС��10��ǰ�油0
     * @param i
     * @return
     * @author tower
     * 
     */
    public static String addZero(int i) {
        if (i < 10) {
            String tmpString = "0" + i;
            return tmpString;
        }
        else {
            return String.valueOf(i);
        }
    }

    private byte[] getBytes(char[] chars) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);

        return bb.array();
    }

    private char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);

        return cb.array();
    }

    /**
     * �������
     * @param result
     * @return
     * @time 2015��1��18�� ����3:56:41
     * @author fiend
     */
    public static JSONObject resultHandling(String result) {
        JSONObject obj = new JSONObject();
        if (!HTHYCodeUtil.isRealEnnull(result)) {//����ʧ��
            obj.put("errCode", "102");
            obj.put("ret", false);
            obj.put("errMsg", "����ʧ��");
            obj.put("coderesult", "");
        }
        else {//����ɹ�
            obj.put("errCode", "100");
            obj.put("ret", true);
            obj.put("errMsg", "");
            obj.put("coderesult", result);

        }
        return obj;
    }

}
