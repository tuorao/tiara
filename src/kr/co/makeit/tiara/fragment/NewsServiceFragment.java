package kr.co.makeit.tiara.fragment;

import kr.co.makeit.tiara.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * 티아라 매장 정보 프래그먼트 (서비스 -> 매장정보로 변경)
 * @author leekangsan
 *
 */
public class NewsServiceFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_news_service, container, false);
		return v;
	}
	
	
}
