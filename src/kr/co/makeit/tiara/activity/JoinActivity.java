package kr.co.makeit.tiara.activity;

import java.util.ArrayList;
import java.util.List;

import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.SoftKeyboardHandledLinearLayout;
import kr.co.makeit.util.ConnectHttp;
import kr.co.makeit.util.CustomProgress;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * 회원가입 액티비티
 * @author leekangsan
 *
 */
public class JoinActivity extends SherlockActivity {
	private final String signupUrl = "signUp.tiara?";
	private final String idCheckUrl = "nickNameCheckAnd.tiara?id=";
	private CustomProgress customProgress;
	
	private SoftKeyboardHandledLinearLayout mainView;
	private Handler mHandler = new Handler();
	private View titleView;
	private View bottomView;
	
	private EditText inputId;
	private EditText inputPw;
	private EditText inputPwCheck;
	private EditText inputName;
	private EditText inputPhone;
	private EditText inputBirthMon;
	private EditText inputBirthDay;
	
	private String id;
	private String pw;
	private String passCheck;
	private String name;
	private String birth;
	private String phone;
	private int birthMon;
	private int birthDay;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);
		getSupportActionBar().hide();
		setupViews();

		titleView = findViewById(R.id.titleFrameLayout);
		bottomView = findViewById(R.id.bottom);
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
	}

	public void onJoinClick(View v) {
		id = inputId.getText().toString();
		pw = inputPw.getText().toString();
		passCheck = inputPwCheck.getText().toString();
		name = inputName.getText().toString();
		try{
			birthMon = Integer.parseInt(inputBirthMon.getText().toString());
			birthDay = Integer.parseInt(inputBirthDay.getText().toString());
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		try{
			phone = inputPhone.getText().toString();
			phone = phone.substring(0, 3)+"-"+phone.substring(3, 7)+"-"+phone.substring(7,11);	
		}catch(StringIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		
		 
		if(checkValue()){
			new JoinProcess().execute();
			finish();
		}
	}
	
	public boolean checkValue(){
		if(id.length()<1){
			inputId.setError("아이디를 입력해주세요.");
		}else if(pw.length()<1){
			inputPw.setError("비밀번호를 입력해주세요.");
		}else if(passCheck.length()<1){
			inputPwCheck.setError("비밀번호를 입력해주세요.");
		}else if(name.length()<1){
			inputName.setError("이름을 입력해주세요.");
		}else if(birthMon>12 && birthMon<1){
			Toast.makeText(getApplicationContext(), "생일을 확인해주세요", Toast.LENGTH_SHORT).show();
		}else if(birthDay>31 && birthDay<1){
			Toast.makeText(getApplicationContext(), "생일을 확인해주세요", Toast.LENGTH_SHORT).show();
		}else if(!(pw.equals(passCheck))){
			Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
		}else{
			return true;
		}
		return false;
	}
	
	public void onIdClick(View v){
		inputId.setError(null);
	}
	public void onPwClick(View v){
		inputPw.setError(null);
	}
	public void onPw2Click(View v){
		inputPwCheck.setError(null);
	}
	public void onNameClick(View v){
		inputName.setError(null);
	}
	public void onCheckClick(View v) {
		customProgress = new CustomProgress(this, R.layout.layout_progress);
		id = inputId.getText().toString();
		if(id.length()<1){
			inputId.setError("아이디를 입력해주세요");
			customProgress.dismiss();
		}else{
			new IdCheckProcess().execute();
		}
	}

	public void onCancelClick(View v) {
		finish();
	}
	
	private class JoinProcess extends AsyncTask<Void, Void, String> {
		private ConnectHttp connectHttp = new ConnectHttp();
		private List<NameValuePair> paramList = new ArrayList<NameValuePair>();

		@Override
		protected String doInBackground(Void... params) {
			paramList.add(new BasicNameValuePair("id", id));
			paramList.add(new BasicNameValuePair("pw", pw));
			paramList.add(new BasicNameValuePair("passCheck", passCheck));
			paramList.add(new BasicNameValuePair("name", name));
			paramList.add(new BasicNameValuePair("birth", birth));
			paramList.add(new BasicNameValuePair("phone", phone));
			
			connectHttp.getHttpPostContents(signupUrl, paramList);

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}

		protected void onPreExecute() {
			// 로딩.. 필요하면 구현
			super.onPreExecute();
		}
	}
	
	private class IdCheckProcess extends AsyncTask<Void, Void, String> {
		private ConnectHttp connectHttp = new ConnectHttp();

		@Override
		protected String doInBackground(Void... params) {
			return connectHttp.getHttpGetContents(idCheckUrl, id);
		}

		protected void onPostExecute(String result) {
			result = result.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
			if(result.equals("0")){
				Toast.makeText(getApplicationContext(), "\" "+id+" \"" +"은(는) 사용 가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), "\" "+id+" \"" +"은(는) 이미 있는 아이디 입니다. \n 다른아이디를 사용해주세요.", Toast.LENGTH_SHORT).show();
			}
			customProgress.dismiss();
			super.onPostExecute(result);
		}
	}
	
	private void setupViews(){
		inputId = (EditText) findViewById(R.id.joinIdText);
		inputPw = (EditText) findViewById(R.id.joinPwText);
		inputPwCheck = (EditText) findViewById(R.id.joinPw2Text);
		inputName = (EditText) findViewById(R.id.joinNameText);
		inputPhone = (EditText) findViewById(R.id.joinPhoneText);
		inputBirthMon = (EditText) findViewById(R.id.joinBirthMonText);
		inputBirthDay = (EditText) findViewById(R.id.joinBirthDayText);
		
	}
}
