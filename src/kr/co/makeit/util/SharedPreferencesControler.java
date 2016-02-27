package kr.co.makeit.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @author 이강산
 * @since 2014-05-31
 * @version 1.0.0
 * @see >> SharedPreferences를 깔끔하고 쉽고 빠르게 사용할 수 있는 클래스
 * 		현재 String을 많이 사용하므로 String put, set만 구현함
 */
public class SharedPreferencesControler{
	private SharedPreferences pref = null;
	private SharedPreferences.Editor editor = null;

	/**
	 * 
	 * @param context
	 * @param preferencesName
	 * @see >> preferencesName은 sharedPreferences에 사용 될 xml명을 지정한다.
	 * 		ex) SharedPreferencesControler(this, "myPref"); 
	 */
	public SharedPreferencesControler(Context context, String preferencesName){
		pref = context.getSharedPreferences(preferencesName, Activity.MODE_PRIVATE);
	}
	/**
	 * 
	 * @param key 값을 저장하고 불러오는 데 사용할 값
	 * @param value 실제 저장되는 값
	 * @see 데이터 삽입 함수
	 */
	public void put(String key, String value){
		editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}
	/**
	 * 
	 * @param key 저장 된 키값을 불러온다.
	 * @param defValue 해당 키의 값이 없으면 defValue를 출력한다.(기본값)
	 * @see 데이터 가져오는 함수
	 */
	public String get(String key, String defValue){
		return pref.getString(key, defValue); 
	}
	
	/**
	 * @since 2014-05-31
	 * @param key
	 * @see 데이터 제거 함수
	 */
	public void remove(String key){
		editor = pref.edit();
		editor.remove(key);
		editor.commit();
	}
}
