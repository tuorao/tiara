package kr.co.makeit.tiara.activity;

import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.app.App;
import kr.co.makeit.tiara.model.User;
import kr.co.makeit.util.ChangeLocale;
import kr.co.makeit.util.ConnectHttp;
import kr.co.makeit.util.CustomProgress;
import kr.co.makeit.util.SharedPreferencesControler;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.echo.holographlibrary.PieSlice;

/**
 * 메인 액티비티, 그래프는 아래 라이브러리를 사용.
 * https://bitbucket.org/danielnadeau/holographlibrary/wiki/Home
 * 
 * @author leekangsan
 *
 */

public class MainActivity extends SherlockActivity {
	private final String moneyUrl = "appMoneyPro.tiara?id=";
	private final String pushUrl = "appKey.tiara?";
	
	private final int RESULT_SETTING = 0;

	private PieGraph cashGraph;
	private PieGraph pointGraph;
	private SharedPreferencesControler pref;
	private User user;
	private TextView nameText;
	private TextView firstMoney;
	private TextView savedMoney;
	private boolean isBackClick = false;

	private CustomProgress customProgress;

	private Resources resources;
	private PieSlice slice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().hide();
		
		resources = getResources();
		slice = new PieSlice();

		customProgress = new CustomProgress(MainActivity.this,
				R.layout.layout_progress);

		pref = App.getSharedPreferences();
		App.setUser(pref.get("user", "noData"));
		user = App.getUser();

		new CashReceiveProcess().execute();

		firstMoney = (TextView) findViewById(R.id.firstMoney);
		savedMoney = (TextView) findViewById(R.id.savedMoney);

		nameText = (TextView) findViewById(R.id.nameText);
		nameText.setText(user.getName());

		cashGraph = (PieGraph) findViewById(R.id.cashGraph);
		pointGraph = (PieGraph) findViewById(R.id.pointGraph);

		cashGraph.setOnSliceClickedListener(new OnSliceClickedListener() {

			@Override
			public void onClick(int index) {
				if (index == -1) {
					Intent intent = new Intent(getApplicationContext(),
							PayListActivity.class);
					intent.putExtra("firstMoney", firstMoney.getText().toString());
					intent.putExtra("savedMoney", savedMoney.getText().toString());
					
					startActivity(intent);
				}
			}
		});
		pointGraph.setOnSliceClickedListener(new OnSliceClickedListener() {
			@Override
			public void onClick(int index) {
				if (index == -1) {
					Intent intent = new Intent(getApplicationContext(),
							PayListActivity.class);
					intent.putExtra("firstMoney", firstMoney.getText().toString());
					intent.putExtra("savedMoney", savedMoney.getText().toString());
					startActivity(intent);
				}
			}
		});
		slice.setColor(resources.getColor(R.color.graph_color1));
		slice.setSelectedColor(resources.getColor(R.color.transparent_orange));

		cashGraph.addSlice(slice);
		slice = new PieSlice();
		slice.setColor(resources.getColor(R.color.orange));
		slice.setValue(1);
		cashGraph.addSlice(slice);

		slice = new PieSlice();
		slice.setColor(resources.getColor(R.color.graph_color1));
		slice.setSelectedColor(resources.getColor(R.color.transparent_orange));

		pointGraph.addSlice(slice);
		slice = new PieSlice();
		slice.setColor(resources.getColor(R.color.orange));
		slice.setValue(2);
		pointGraph.addSlice(slice);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ChangeLocale.refreshThis(this, pref.get("lang", "ko"));
	}

	public void onNoticeClick(View v) {
		Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
		startActivity(intent);
	}

	public void onNailInfoClick(View v) {
		Intent intent = new Intent(getApplicationContext(),
				NailInfoActivity.class);
		startActivity(intent);
	}

	public void onPreviewClick(View v) {
		Intent intent = new Intent(getApplicationContext(),
				PreviewActivity.class);
		startActivity(intent);
	}

	public void onSettingClick(View v) {
		Intent intent = new Intent(getApplicationContext(),
				SettingActivity.class);
		intent.putExtra("refresh", false);
		startActivityForResult(intent, RESULT_SETTING);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	public Animator.AnimatorListener getAnimationListener() {
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1)
			return new Animator.AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animation) {
				}
				@Override
				public void onAnimationEnd(Animator animation) {
				}
				@Override
				public void onAnimationCancel(Animator animation) {
				}
				@Override
				public void onAnimationRepeat(Animator animation) {
				}
			};
		else
			return null;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 0) {
			if (data != null) {
				if (data.getStringExtra("result").equals("finish")) {
					finish();
				}
			}
		}
	}

	public void graphProcess(String first, String saved) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				cashGraph.getSlices().get(0).setGoalValue((float) 10);
				cashGraph.getSlices().get(1).setGoalValue((float) 0);
				cashGraph.setDuration(3000);
				cashGraph
						.setInterpolator(new AccelerateDecelerateInterpolator());
				cashGraph.setAnimationListener(getAnimationListener());
				cashGraph.animateToGoalValues();
			}
		} , 100);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				pointGraph.getSlices().get(0).setGoalValue((float) 10);
				pointGraph.getSlices().get(1).setGoalValue((float) 0);
				
				pointGraph.setDuration(3000);
				pointGraph.setInterpolator(new AccelerateDecelerateInterpolator());
				pointGraph.setAnimationListener(getAnimationListener());
				pointGraph.animateToGoalValues();
			}
		} , 100);
		
		

	}

	@Override
	public void onBackPressed() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				isBackClick = false;
			}
		}, 2000);
		if (!isBackClick) {
			Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르시면 종료됩니다.",
					Toast.LENGTH_SHORT).show();
			isBackClick = true;
		} else {
			super.onBackPressed();
		}
	}

	private class CashReceiveProcess extends AsyncTask<String, String, String> {
		private ConnectHttp connectHttp = new ConnectHttp();

		@Override
		protected String doInBackground(String... params) {
			connectHttp.getHttpGetContents(pushUrl, "id="+user.getId().trim()+"&key="+App.getPushKey());
			return connectHttp.getHttpPostContents(moneyUrl, user.getId().trim()); 
		}

		@Override
		protected void onPostExecute(String result) {
			String first_money, saved_money;
			ObjectMapper mapper = new ObjectMapper();
			try {
				JsonNode node = mapper.readTree(result);
				first_money = node.path("first_money").getTextValue();
				saved_money = node.path("saved_money").getTextValue();

				firstMoney.setText(first_money);
				savedMoney.setText(saved_money);

				graphProcess(first_money, saved_money);
			} catch (Exception e) {
				e.printStackTrace();
			}

			customProgress.dismiss();
			super.onPostExecute(result);
		}
	}
}
