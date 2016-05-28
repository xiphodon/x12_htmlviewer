package com.example.x12_htmlviewer;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.x12_htmlviewer.utils.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			TextView tv = (TextView) findViewById(R.id.tv);
			tv.setText((String)msg.obj);
		}
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void click(View v){
    	Thread thread = new Thread(){
    		public void run() {
    			String path = "http://192.168.1.101:8080/index.html";
    	    	try {
    				URL url = new URL(path);
    				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    				conn.setRequestMethod("GET");
    				conn.setConnectTimeout(5000);
    				conn.setReadTimeout(5000);
    				//先建立连接，再获取响应码
    				conn.connect();
    				if(conn.getResponseCode() == 200){
    					//拿到服务器返回的输入流，流里的数据就是html源文件
    					InputStream is = conn.getInputStream();
    					//把流里的文本读取出来
    					String text = Utils.getTextFromStream(is);
    					//发送消息，让主线程刷新UI，显示源文件
    					Message msg = handler.obtainMessage();
    					msg.obj = text;
    					handler.sendMessage(msg);
    				}
    				
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    	};
    	thread.start();
    }
    
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
