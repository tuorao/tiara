package kr.co.makeit.tiara.fragment;

import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.app.App;
import kr.co.makeit.tiara.model.User;
import kr.co.makeit.util.SharedPreferencesControler;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * 계정 설정 프래그먼트
 * @author leekangsan
 *
 */
public class SettingAccountFragment extends SherlockFragment {
	private TextView setId, setId_e,  setName, setPhone, setBirth;
	private EditText setName_e, setPhone_e, setBirth_e;
	
	private User user;
	private SharedPreferencesControler pref;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_setting_account, container, false);
		
		pref = App.getSharedPreferences();
		setId = (TextView) v.findViewById(R.id.setId);
		setName = (TextView) v.findViewById(R.id.setName);
		setPhone = (TextView) v.findViewById(R.id.setPhone);
		setBirth = (TextView) v.findViewById(R.id.setBirth);
		
		setId_e = (TextView) v.findViewById(R.id.setId_e);
		setName_e = (EditText) v.findViewById(R.id.setName_e);
		setPhone_e = (EditText) v.findViewById(R.id.setPhone_e);
		setBirth_e = (EditText) v.findViewById(R.id.setBirth_e);
		
		user = App.getUser();
		setId.setText(user.getId());
		setName.setText(user.getName());
		setPhone.setText(user.getPhone());
		setBirth.setText(user.getBirth());
		
		setId_e.setText(user.getId());
		setName_e.setText(user.getName());
		setPhone_e.setText(user.getPhone());
		setBirth_e.setText(user.getBirth());
		
		return v;
	}
	 @Override
	public void onResume() {
		 Log.i("바꿀언어",  pref.get("lang", "ko"));
		super.onResume();
	}

}
