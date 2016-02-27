package kr.co.makeit.tiara.activity;

import java.util.ArrayList;
import java.util.List;

import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.SoftKeyboardHandledLinearLayout;
import kr.co.makeit.tiara.app.App;
import kr.co.makeit.util.ChangeLocale;
import kr.co.makeit.util.ConnectHttp;
import kr.co.makeit.util.CustomProgress;
import kr.co.makeit.util.SharedPreferencesControler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.capricorn.RayMenu;

/**
 * 로그인 액티비티
 * @author leekangsan
 *
 */
public class LoginActivity extends SherlockActivity {
	private static final int[] ITEM_DRAWABLES = { R.drawable.lang_korea, R.drawable.lang_china, R.drawable.lang_vietnam, R.drawable.lang_usa };
	private final String loginUrl = "appLogin.tiara?";

	private SoftKeyboardHandledLinearLayout mainView;
	private Handler mHandler = new Handler();
	private View titleView;
	private View bottomView;
	private boolean isProcessing = false;
	private boolean isBackClick = false;
	
	private EditText inputId;
	private EditText inputPw;
	private String id;
	private String pw;
	private CheckBox autoCheckBox;
	private SharedPreferencesControler pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		pref = App.getSharedPreferences();
		inputId = (EditText) findViewById(R.id.loginId);
		inputPw = (EditText) findViewById(R.id.loginPw);
		autoCheckBox = (CheckBox) findViewById(R.id.autoCheckBox);
		titleView = findViewById(R.id.titleFrameLayout);
		bottomView = findViewById(R.id.bottom);

		getSupportActionBar().hide();
		
		RayMenu rayMenu = (RayMenu) findViewById(R.id.ray_menu);
		
        final int itemCount = ITEM_DRAWABLES.length;
		for (int i = 0; i < itemCount; i++) {
			ImageView item = new ImageView(this);
			item.setImageResource(ITEM_DRAWABLES[i]);

			final int position = i;
			rayMenu.addItem(item, new OnClickListener() {

				@Override
				public void onClick(View v) {
					switch(position){
					case 0:
						ChangeLocale.setLocale(LoginActivity.this, "ko");
						pref.put("lang", "ko");
						break;
					case 1:
						ChangeLocale.setLocale(LoginActivity.this, "zh");
						pref.put("lang", "zh");
						break;
					case 2:
						ChangeLocale.setLocale(LoginActivity.this, "ru");
						pref.put("lang", "ru");
						break;
					case 3:
						ChangeLocale.setLocale(LoginActivity.this, "en");
						pref.put("lang", "en");
						break;
					}
					startActivity(new Intent(LoginActivity.this, LoginActivity.class));
					finish();
				}
			}); 
		}
		
		((EditText) findViewById(R.id.loginPw))
				.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView tv, int value,
							KeyEvent event) {
						onLoginClick(null);
						return false;
					}
				});

		mainView = ((SoftKeyboardHandledLinearLayout) findViewById(R.id.main_view));
		mainView.setOnSoftKeyboardVisibilityChangeListener(new SoftKeyboardHandledLinearLayout.SoftKeyboardVisibilityChangeListener() {
			public void onSoftKeyboardHide() {
				mHandler.postDelayed(new Runnable() {
					public void run() {
						titleView.setVisibility(View.VISIBLE);
						bottomView.setVisibility(View.VISIBLE);
					}
				}, 300L);
			}

			public void onSoftKeyboardShow() {
				titleView.setVisibility(View.GONE);
				bottomView.setVisibility(View.GONE);
			}
		});
		
		if(pref.get("autoLogin", "false").equals("true")){
			id = pref.get("loginId", "noData");
			pw = pref.get("loginPw", "noData");
			new LoginProcess().execute();
		}
	}
	
	public void onPause() {
	     super.onPause();
	     overridePendingTransition(0, 0);
	 }
	
	public void onIdClick(View v){
		inputId.setError(null);
	}
	public void onPwClick(View v){
		inputId.setError(null);
	}
	
	public void onLoginClick(View v) {
		id = inputId.getText().toString();
		pw = inputPw.getText().toString();
		
		if(!isProcessing){
			new LoginProcess().execute();
		}

	}

	public void onJoinClick(View v) {
		Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
		startActivity(intent);
	}

	public void onFindClick(View v) {

	}
	
	@Override
	public void onBackPressed() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() { isBackClick = false; } }, 2000);
		if(!isBackClick) {
			Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
			isBackClick = true;
		}else{
			super.onBackPressed();
		}
	}
	
	private class LoginProcess extends AsyncTask<String, String, String> {
		private String result;
		private CustomProgress customProgress;
		private ConnectHttp connectHttp = new ConnectHttp();
		private List<NameValuePair> paramList = new ArrayList<NameValuePair>();

		@Override
		protected String doInBackground(String... params) {
			paramList.add(new BasicNameValuePair("id", id.trim()));
			paramList.add(new BasicNameValuePair("pw", pw.trim()));
			
			isProcessing = true; // 처리중에 접근 방지 
			result = connectHttp.getHttpPostContents(loginUrl, paramList);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			String state;
			ObjectMapper mapper = new ObjectMapper();
			try{
				JsonNode node = mapper.readTree(result);
				state = node.path("state").getTextValue();
				if(state.equals("flase")){
					inputId.setError("ID&PW를 확인해주세요.");
				}else{
					pref.put("user", result);
					if(pref.get("autoLogin", "false").equals("true")){
						App.login(id, pw, "true");
					}else{
							App.login(id, pw, autoCheckBox.isChecked()?"true":"false");
					}
					Intent intent = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(intent);
					finish(); 
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			isProcessing = false;
			customProgress.dismiss();
			super.onPostExecute(result);
		}
		
		protected void onPreExecute() {
			// 로딩.. 필요하면 구현
			customProgress = new CustomProgress(LoginActivity.this, R.layout.layout_progress);
			super.onPreExecute();
		}
	}
	
	
}
