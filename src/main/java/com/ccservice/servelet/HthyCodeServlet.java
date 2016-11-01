package com.ccservice.servelet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ccservice.dama.FileUtil;
import com.ccservice.dama.HTHYCodeUtil;
import com.ccservice.dama.HTHYGetCode;
import com.ccservice.data.PropertyUtil;

@WebServlet(urlPatterns = "/HthyCodeServlet")
public class HthyCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HthyCodeServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out =null;
		try {
			String filePath = PropertyUtil.getValue("pictureDirPath");
			byte[] bs = HTHYCodeUtil.input2byte(request.getInputStream());
			String filename = System.currentTimeMillis() + new Random().nextInt(10) + ".jpg";
			String path =filePath+filename;
			buff2Image(bs, filename, filePath);
			byte[] byte1 = FileUtil.file2byte(new File(path));
			String rand_code = HTHYGetCode.getCode(byte1, 1);
			System.out.println("验证码结果:"+rand_code);
			out =response.getWriter();
			out.write(rand_code);
			out.flush();
			out.close();
		} catch (Exception e) {
			if(out!=null){
				out.flush();
				out.close();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String s = "D://08//1.jpg";
		byte[] byte1 = FileUtil.file2byte(new File(s));
		String rand_code = HTHYGetCode.getCode(byte1, 1);
		System.out.println(rand_code);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public static void buff2Image(byte[] b, String tagSrc, String imgpath) {
		try {
			File f = new File(imgpath);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fout = new FileOutputStream(imgpath + tagSrc);
			// 将字节写入文件
			fout.write(b);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
