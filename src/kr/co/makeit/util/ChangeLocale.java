package kr.co.makeit.util;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 루트부터 뷰그룹을 새로고침해줌 
 * 
 * 2014.10.28 
 * @author leekangsan
 * 
 */
public class ChangeLocale {

	public static void setRefreshViewGroup(Context context, ViewGroup root)
			throws Exception {
		for (int i = 0; i < root.getChildCount(); i++) {
			View child = root.getChildAt(i);

			if (child instanceof TextView) {
				if (child.getTag() != null) {

					if (((TextView) child).getText() != null
							&& ((TextView) child).getText().toString().length() > 0) {
						int stringId = getResourceId(context, child.getTag());
						((TextView) child).setText(stringId);
						// Log.i(TAG, "getText:" + ((TextView)
						// child).getText());
					}

					if (((TextView) child).getHint() != null
							&& ((TextView) child).getHint().toString().length() > 0) {
						int hintId = getResourceId(context, child.getTag());
						((TextView) child).setHint(hintId);

						// Log.i(TAG, "getHint:" + ((TextView)
						// child).getHint());
					}
				}
			} else if (child instanceof ViewGroup)
				setRefreshViewGroup(context, (ViewGroup) child);
		}
	}

	public static int getResourceId(Context context, Object tag) {
		return context.getResources().getIdentifier((String) tag, "string",
				context.getPackageName());
	}

	// 언어 설정 메소드
	public static void setLocale(final Activity activity, String character) {
		Locale locale = new Locale(character);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		activity.getResources().updateConfiguration(config,
				activity.getResources().getDisplayMetrics());

		final ViewGroup vg = (ViewGroup) activity.getWindow().getDecorView()
				.getRootView();

		try {
			setRefreshViewGroup(activity, vg);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.i("TAG", "getLocale : " + Locale.getDefault().toString());
	}
	
	public static void refreshThis(final Activity activity, String locale){
		if(locale.equals("ko")){
			setLocale(activity, "ko");
		}else if(locale.equals("zh")){
			setLocale(activity, "zh");
		}else if(locale.equals("ru")){
			setLocale(activity, "ru");
		}else if(locale.equals("en")){
			setLocale(activity, "en");
		}
	}

}
