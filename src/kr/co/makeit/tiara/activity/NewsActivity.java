package kr.co.makeit.tiara.activity;

import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.TIARAThreadGET;
import kr.co.makeit.tiara.app.App;
import kr.co.makeit.tiara.fragment.NewsEventFragment;
import kr.co.makeit.tiara.fragment.NewsNoticeFragment;
import kr.co.makeit.tiara.fragment.NewsServiceFragment;
import kr.co.makeit.util.CustomProgress;
import kr.co.makeit.util.SharedPreferencesControler;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * 티아라 소식 액티비티 
 * @author leekangsan
 *
 */
public class NewsActivity extends SherlockFragmentActivity {
	Button noticeButton, serviceButton, eventButton;
	private Handler handler;
	private TIARAThreadGET thread;
	private String result;
	private SharedPreferencesControler pref;
	private CustomProgress customProgress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_news);
		customProgress = new CustomProgress(NewsActivity.this, R.layout.layout_progress);
		pref = App.getSharedPreferences();

		setupActionBar();
		setupViews();
		HandlerUI();

		thread = new TIARAThreadGET(handler);
		thread.start();
		
	}
	
	void HandlerUI(){
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Aauto-generated method stub
				super.handleMessage(msg);
				result = (String) msg.obj;

				getSupportFragmentManager().beginTransaction()
						.replace(R.id.newsContainer, new NewsNoticeFragment(result), "notice")
						.commit();
				customProgress.dismiss();
				
			}
		};
		
		
	}

	public void onNoticeClick(View v) {
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.newsContainer, new NewsNoticeFragment(result), "notice")
				.commit();
		noticeButton.setSelected(true);
		serviceButton.setSelected(false);
		eventButton.setSelected(false);
	}

	public void onServiceClick(View v) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.newsContainer, new NewsServiceFragment(), "service")
				.commit();
		noticeButton.setSelected(false);
		serviceButton.setSelected(true);
		eventButton.setSelected(false);
	}

	public void onEventClick(View v) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.newsContainer, new NewsEventFragment(), "event")
				.commit();
		noticeButton.setSelected(false);
		serviceButton.setSelected(false);
		eventButton.setSelected(true);
	}

	public void setupActionBar() {

		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.actionbar_normal);

		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00000000")));
		getSupportActionBar().setStackedBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00000000")));
		((ImageView) findViewById(R.id.actionBarLeftImage))
				.setImageResource(R.drawable.ic_tiara_logo);
		if(pref.get("lang", "ko").equals("ko")){
			((TextView) findViewById(R.id.actionBarLeftText)).setText("티아라 소식");
		}else if(pref.get("lang", "ko").equals("zh")){
			((TextView) findViewById(R.id.actionBarLeftText)).setText("消息");
		}else if(pref.get("lang", "ko").equals("en")){
			((TextView) findViewById(R.id.actionBarLeftText)).setText("Tiara news");
		}else{
			((TextView) findViewById(R.id.actionBarLeftText)).setText("Tin tức");
		}
	}

	public void setupViews() {
		noticeButton = (Button) findViewById(R.id.noticeButton);
		serviceButton = (Button) findViewById(R.id.serviceButton);
		eventButton = (Button) findViewById(R.id.eventButton);
		noticeButton.setSelected(true);
	}

}