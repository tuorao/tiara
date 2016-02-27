package kr.co.makeit.tiara;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import kr.co.makeit.tiara.app.App;
import android.os.Handler;
import android.os.Message;

public class TIARAThreadGET extends Thread{
		
		String url = "ShowNotice.tiara";
		Handler handler;
		String result ="";
		
		public TIARAThreadGET(Handler handler) {
			// TODO Auto-generated constructor stub
			this.handler = handler;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			BufferedReader reader;
			URL url1;
			String str1;
			
			try {
				url1 = new URL(App.getHost()+url);
				reader = new BufferedReader(new InputStreamReader(url1.openStream()));
				
				while((str1 = reader.readLine()) != null)
					result += str1;
				
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message msg = handler.obtainMessage();
						msg.obj = result;
						handler.sendMessage(msg);
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
