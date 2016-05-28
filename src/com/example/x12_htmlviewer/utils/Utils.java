package com.example.x12_htmlviewer.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

	public static String getTextFromStream(InputStream is) {
		byte[] b = new byte[1024];
		int len = 0;
		// 创建字节数组输出流，读取输入流的文本数据时，同步把数据写入字节数组输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			while ((len = is.read(b)) != -1) {
				baos.write(b, 0, len);
			}
			// 把字节数组输出流里的数据转换成字节数组
			String text = new String(baos.toString());
			return text;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
