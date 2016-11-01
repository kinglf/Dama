import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.ccservice.dama.FileUtil;
import com.ccservice.dama.HTHYGetCode;
import com.ccservice.data.SendPostandGet;

public class ThreadTest {

	public static void main3(String[] args) {
		try {
			byte[] byte1 = FileUtil.file2byte(new File("D:\\08\\1.jpg"));
			String rand_code = HTHYGetCode.getCode(byte1, 1);
			System.out.println(rand_code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String results = "";
		try {
			File oldFile = new File("D://08//1.jpg");
			byte[] bytes = file2byte(oldFile);
			Long l1 = System.currentTimeMillis();
			results = SendPostandGet.submitPostDama("http://43.241.236.191:9016/Dama/HthyCodeServlet", bytes);
			System.out.println(results);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

}
