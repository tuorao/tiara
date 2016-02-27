package kr.co.makeit.tiara.activity;

import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.app.App;
import kr.co.makeit.tiara.fragment.SettingAccountFragment;
import kr.co.makeit.tiara.fragment.SettingFuncFragment;
import kr.co.makeit.util.ChangeLocale;
import kr.co.makeit.util.SharedPreferencesControler;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * 설정 액티비티 
 * @author leekangsan
 *
 */
public class SettingActivity extends SherlockFragmentActivity {
	private Button accountBtn;
	private Button funcBtn;
	private boolean isReceivePush = false;
	private boolean isModifing = false;

	private SharedPreferencesControler pref;
	private Intent parentIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_setting);
		pref = App.getSharedPreferences();

		setupActionBar();
		setupViews();
		
		parentIntent = getIntent();
		if(parentIntent.getExtras().getBoolean("refresh")){
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.settingContainer, new SettingFuncFragment(),
					"func").commit();
			funcBtn.setSelected(true);
			accountBtn.setSelected(false);
		}else{
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.settingContainer, new SettingAccountFragment(),
					"account").commit();			
		}
	}
	
	public void onPushClick(View v){
		if(isReceivePush){
			v.setSelected(false);
			isReceivePush = false;
			pref.put("push", "off");
		}else{
			v.setSelected(true);
			isReceivePush = true;
			pref.put("push", "on");
		}
	}

	public void onFuncClick(View v){
		funcBtn.setSelected(true);
		accountBtn.setSelected(false);
		
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.settingContainer, new SettingFuncFragment(),
				"func").commit();
		
	}
	public void onAccountClick(View v){
		funcBtn.setSelected(false);
		accountBtn.setSelected(true);
		
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.settingContainer, new SettingAccountFragment(),
				"account").commit();
		
	}
	
	public void onModifyClick(View v){
		if(isModifing){	// 수정모드인 경우 
			isModifing = false;
			((LinearLayout)findViewById(R.id.setAccountText)).setVisibility(View.VISIBLE);
			((LinearLayout)findViewById(R.id.setAccountEdit)).setVisibility(View.INVISIBLE);
			
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.settingContainer, new SettingAccountFragment(),
					"account").commit();
			
		}else{
			isModifing = true;
			((LinearLayout)findViewById(R.id.setAccountText)).setVisibility(View.INVISIBLE);
			((LinearLayout)findViewById(R.id.setAccountEdit)).setVisibility(View.VISIBLE);
		}
	}
	public void onLogoutClick(View v){
		App.logout();
		Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtra("result", "finish");
		setResult(0, intent);
		
		finish();
		startActivity(intent);
	}

	public void setupActionBar() {

		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.actionbar_normal);

		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00000000")));
		getSupportActionBar().setStackedBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00000000")));
		((ImageView) findViewById(R.id.actionBarLeftImage))
				.setImageResource(R.drawable.ic_tiara_setting);
		if(pref.get("lang", "ko").equals("ko")){
			((TextView) findViewById(R.id.actionBarLeftText)).setText("설정");
		}else if(pref.get("lang", "ko").equals("zh")){
			((TextView) findViewById(R.id.actionBarLeftText)).setText("設定");
		}else if(pref.get("lang", "ko").equals("en")){
			((TextView) findViewById(R.id.actionBarLeftText)).setText("Setting");
		}else{
			((TextView) findViewById(R.id.actionBarLeftText)).setText("Thiết lập");
		}
	}
	
	public void setupViews(){
		accountBtn = (Button) findViewById(R.id.setting_account_button);
		funcBtn = (Button) findViewById(R.id.setting_func_button);
		accountBtn.setSelected(true);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ChangeLocale.refreshThis(this, pref.get("lang", "ko"));
	}
	
}
