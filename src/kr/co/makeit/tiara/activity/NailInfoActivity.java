package kr.co.makeit.tiara.activity;

import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.app.App;
import kr.co.makeit.tiara.fragment.NailGallaryFragment;
import kr.co.makeit.tiara.fragment.NailTipFragment;
import kr.co.makeit.util.SharedPreferencesControler;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * 네일정보 액티비티
 * @author leekangsan
 *
 */
public class NailInfoActivity extends SherlockFragmentActivity {
	private Button gallButton, tipButton;
	private SharedPreferencesControler pref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_nail_info);

		pref = App.getSharedPreferences();

		setupActionBar(); 
		setupView();

		
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.nailInfoContainer, new NailGallaryFragment(),
						"gallary").commit();
	}
	
	public void onGallClick(View v) {
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.nailInfoContainer, new NailGallaryFragment(),
						"gallary").commit();
		gallButton.setSelected(true);
		tipButton.setSelected(false);
		
	}

	public void onTipClick(View v) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.nailInfoContainer, new NailTipFragment(), "tip")
				.commit();
		gallButton.setSelected(false);
		tipButton.setSelected(true);
	}
	public void onMenuClick(View v){
//		if(getSupportFragmentManager().findFragmentByTag("menu")==null){
//		getSupportFragmentManager().beginTransaction()
//		.replace(R.id.nailGallContainer, new NailGallaryMenuFragment(), "menu").addToBackStack(null)
//		.commit();
//		}else{
//			//Toast.makeText(getApplicationContext(), "널아냐생성안해", Toast.LENGTH_SHORT).show();
//		}
	}
	public void onBackgroundClick(View v){
		 getSupportFragmentManager().popBackStack();
	}
	
	public void setupActionBar() {

		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.actionbar_normal);

		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00000000")));
		getSupportActionBar().setStackedBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00000000")));
		((ImageView) findViewById(R.id.actionBarLeftImage))
				.setImageResource(R.drawable.ic_tiara_info);
		if(pref.get("lang", "ko").equals("ko")){
			((TextView) findViewById(R.id.actionBarLeftText)).setText("네일정보");
		}else if(pref.get("lang", "ko").equals("zh")){
			((TextView) findViewById(R.id.actionBarLeftText)).setText("美甲讯息");
		}else if(pref.get("lang", "ko").equals("ru")){
			((TextView) findViewById(R.id.actionBarLeftText)).setText("Nail Info");
		}else{
			((TextView) findViewById(R.id.actionBarLeftText)).setText("Nail Info");
		}

	}
	
	public void setupView(){
		gallButton = (Button) findViewById(R.id.gallButton);
		tipButton = (Button) findViewById(R.id.tipButton);
		gallButton.setSelected(true);
	}

	public void onBackClick(View v){
		onBackPressed();
	}
	

}
