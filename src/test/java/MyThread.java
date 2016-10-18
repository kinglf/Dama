import java.io.File;
import java.io.IOException;

import com.ccservice.dama.FileUtil;
import com.ccservice.dama.HTHYGetCode;
import com.ccservice.data.HttpGet12306Image;

public class MyThread extends Thread{
	
	@Override
	public void run() {
		try {
			long l1 = System.currentTimeMillis();
			String cookies =HttpGet12306Image.get12306cookie();
			String imageUrl =HttpGet12306Image.downloadimgbyhttpclient(cookies);
			long l2 = System.currentTimeMillis();
			System.out.println("下载图片："+(l2-l1));
			byte[] byte1=FileUtil.file2byte(new File(imageUrl));
			String rand_code = HTHYGetCode.getCode(byte1, 1);
			System.out.println(rand_code);
			long l3 = System.currentTimeMillis();
			System.out.println("打码："+(l3-l2));
			boolean isTrue =HttpGet12306Image.codeIsRight(rand_code, cookies);
			long l4 = System.currentTimeMillis();
			System.out.println("验证："+(l4-l3));
			long l5 = System.currentTimeMillis();
			String res =HttpGet12306Image.queryPrice(rand_code, cookies);
			System.out.println(res);
			System.out.println("查询："+(l5-l4));
			System.out.println("验证码："+rand_code+"--->isTrue:"+isTrue+"--->all time:"+(l5-l1));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
