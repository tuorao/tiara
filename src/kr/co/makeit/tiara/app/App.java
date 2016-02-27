package kr.co.makeit.tiara.app;

import kr.co.makeit.tiara.model.NailGallLists;
import kr.co.makeit.tiara.model.NailTipLists;
import kr.co.makeit.tiara.model.PayLists;
import kr.co.makeit.tiara.model.User;
import kr.co.makeit.util.SharedPreferencesControler;

import org.codehaus.jackson.map.ObjectMapper;

import android.app.Application;
import android.content.Context;

/**
 * 공유 변수 관리 클래스
 * 공유하지 않아도 되지만 임시로 넣은 부분도 있음.
 * @author leekangsan
 *
 */
public class App extends Application {
	private static Context context;
	private static SharedPreferencesControler pref;
	private static User user;
	private static PayLists[] payList;
	private static NailTipLists[] nailTipList;
	private static NailGallLists[] nailGallList;
	private static ObjectMapper mapper;
	private static String pushKey;
	private static String host = "http://54.69.198.243/";
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		pref = new SharedPreferencesControler(context, "myPref");
		mapper = new ObjectMapper();
	}
	
	public static Context getContext(){
		return context;
	}
	
	public static SharedPreferencesControler getSharedPreferences(){
		return pref;
	}
	
	public static String getHost(){
		return host;
	}
	
	public static void setUser(String result){
		try{
		user = mapper.readValue(result, User.class);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static User getUser(){
		return user;
	}
	public static void setPayList(String result){
		try{
			payList = mapper.readValue(result, PayLists[].class);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void setNailGallList(String result){
		try{
			nailGallList = mapper.readValue(result, NailGallLists[].class);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static NailGallLists[] getNailGallLists(){
		return nailGallList;
	}
	public static void setNailTipList(String result){
		try{
			nailTipList = mapper.readValue(result, NailTipLists[].class);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static NailTipLists[] getNailTipLists(){
		return nailTipList;
	}
	
	public static PayLists[] getPayLists(){
		return payList;
	}
	public static void logout(){
		pref.remove("autoLogin");
		pref.remove("loginId");
		pref.remove("loginPw");
	}
	public static void login(String id, String pw, String isAutoCheck){
		pref.put("autoLogin", isAutoCheck);
		pref.put("loginId", id);
		pref.put("loginPw", pw);
	}
	public static void setPushKey(String pushKey){
		App.pushKey = pushKey;
	}
	public static String getPushKey(){
		return pushKey;
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}
	
}
