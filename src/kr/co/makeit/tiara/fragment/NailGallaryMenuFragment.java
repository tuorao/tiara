package kr.co.makeit.tiara.fragment;

import kr.co.makeit.tiara.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * 네일 메뉴 프래그먼트 ( 현재 쓰이지 않는다. )
 * @author leekangsan
 *
 */

@Deprecated
public class NailGallaryMenuFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_nail_gall_menu, container,
				false);
		return v;
	}

}
