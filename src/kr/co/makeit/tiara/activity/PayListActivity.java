package kr.co.makeit.tiara.activity;

import java.util.ArrayList;

import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.adapter.PayListAdapter;
import kr.co.makeit.tiara.app.App;
import kr.co.makeit.tiara.model.PayList;
import kr.co.makeit.tiara.model.PayLists;
import kr.co.makeit.util.ConnectHttp;
import kr.co.makeit.util.CustomProgress;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * 적립금 리스트 액티비티
 * @author leekangsan
 *
 */
public class PayListActivity extends SherlockActivity {
	private String payListUrl = "appPayList.tiara?";
	private ArrayList<PayList> Array_Data;
	private PayList data;
	private PayListAdapter adapter;
	private ListView listView;
	private PayLists[] payList;
	private CustomProgress customProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_list);
		
		customProgress = new CustomProgress(this, R.layout.layout_progress);

		Intent intent = getIntent();
		
		((TextView)findViewById(R.id.paylist_balance)).setText(intent.getExtras().getString("firstMoney"));
		((TextView)findViewById(R.id.paylist_point)).setText(intent.getExtras().getString("savedMoney"));
		
		Array_Data = new ArrayList<PayList>();
		listView = (ListView) findViewById(R.id.paylist_listView);

		PayListThread payListThread = new PayListThread();
		payListThread.execute();

	}

	public void setPayList(){
		for(int i=0; i<payList.length;i++){
			data = new PayList(getString(R.string.service_list)+" : "+payList[i].getService(), 
					getString(R.string.pay)+" : "+payList[i].getPay_money()+"원",
					getString(R.string.discount)+" : "+Math.round(payList[i].getRate()*100)+"%",
					getString(R.string.balance)+" : "+payList[i].getLeft_money(), payList[i].getPay_date());
			Array_Data.add(data);			
		}
		
		adapter = new PayListAdapter(getApplicationContext(),
				android.R.layout.simple_list_item_1, Array_Data);
		listView.setAdapter(adapter);
		
		customProgress.dismiss();
	}

	public class PayListThread extends AsyncTask<String, String, String> {
		private String result;
		private ConnectHttp connectHttp = new ConnectHttp();

		@Override
		protected String doInBackground(String... params) {
			result = connectHttp.getHttpPostContents(payListUrl, "id="
					+ App.getUser().getId());
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			App.setPayList(result);
			payList = App.getPayLists();
			setPayList();
			super.onPostExecute(result);
		}
	}
}
