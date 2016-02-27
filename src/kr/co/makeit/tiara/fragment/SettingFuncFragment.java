package kr.co.makeit.tiara.fragment;

import kr.co.makeit.tiara.R;
import kr.co.makeit.util.ChangeLocale;
import kr.co.makeit.util.SharedPreferencesControler;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * 기능 설정 프래그먼트
 * @author leekangsan
 *
 */
public class SettingFuncFragment extends SherlockFragment implements OnClickListener{
	private LinearLayout languageView; 
	private TextView languageText;
	private Button pushBtn;
	private SharedPreferencesControler pref;
	private View parent;
	private Intent intent;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parent = inflater
				.inflate(R.layout.fragment_setting_func, container, false);
		languageView = (LinearLayout) parent.findViewById(R.id.languageView);
		languageText = (TextView) parent.findViewById(R.id.languageText);
		pushBtn = (Button) parent.findViewById(R.id.pushButton);
		
		pref = new SharedPreferencesControler(getSherlockActivity(), "myPref");
		
		if(pref.get("push", "on").equals("off")){
			pushBtn.setSelected(false);
		}else{
			pushBtn.setSelected(true);
		}
		
		((TextView)parent.findViewById(R.id.languageText)).setOnClickListener(this);
		((TextView)parent.findViewById(R.id.languageKorea)).setOnClickListener(this);
		((TextView)parent.findViewById(R.id.languageChina)).setOnClickListener(this);
		((TextView)parent.findViewById(R.id.languageVietnam)).setOnClickListener(this);
		((TextView)parent.findViewById(R.id.languageUS)).setOnClickListener(this);
		
		return parent;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.languageKorea:
			languageText.setText("한국어");
			languageText.setVisibility(View.VISIBLE);
			ChangeLocale.setLocale(getActivity(), "ko");
			pref.put("lang", "ko");
			((TextView)(getActivity().getActionBar().getCustomView().findViewById(R.id.actionBarLeftText))).setText("설정");
			languageView.setVisibility(View.INVISIBLE);
			intent = getActivity().getIntent();
			getActivity().finish();
			intent.putExtra("refresh", true);
			startActivity(intent);
			
			break;
		case R.id.languageChina:
			languageText.setText("中國語");
			languageText.setVisibility(View.VISIBLE);
			ChangeLocale.setLocale(getActivity(), "zh");
			pref.put("lang", "zh");
			((TextView)(getActivity().getActionBar().getCustomView().findViewById(R.id.actionBarLeftText))).setText("設定");
			languageView.setVisibility(View.INVISIBLE);
			intent = getActivity().getIntent();
			getActivity().finish();
			intent.putExtra("refresh", true);
			startActivity(intent);
			break;
		case R.id.languageVietnam:
			languageText.setText("tiếng Việt");
			languageText.setVisibility(View.VISIBLE);
			ChangeLocale.setLocale(getActivity(), "ru");
			pref.put("lang", "ru");
			((TextView)(getActivity().getActionBar().getCustomView().findViewById(R.id.actionBarLeftText))).setText("Thiết lập");
			languageView.setVisibility(View.INVISIBLE);
			intent = getActivity().getIntent();
			getActivity().finish();
			intent.putExtra("refresh", true);
			startActivity(intent);
			break;
		case R.id.languageUS:
			languageText.setText("English");
			languageText.setVisibility(View.VISIBLE);
			ChangeLocale.setLocale(getActivity(), "en");
			pref.put("lang", "en");
			((TextView)(getActivity().getActionBar().getCustomView().findViewById(R.id.actionBarLeftText))).setText("Setting");
			languageView.setVisibility(View.INVISIBLE);
			intent = getActivity().getIntent();
			getActivity().finish();
			intent.putExtra("refresh", true);
			startActivity(intent);
			break;
		case R.id.languageText:
			languageText.setVisibility(View.INVISIBLE);		
			languageView.setVisibility(View.VISIBLE);
			break;
		}
		
	}
}
