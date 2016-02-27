package kr.co.makeit.tiara.fragment;

import java.util.ArrayList;

import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.adapter.BaseExpandableAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * 티아라 이벤트 프래그먼
 * @author leekangsan
 *
 */
public class NewsEventFragment extends SherlockFragment {

	private ArrayList<String> mGroupList = null;
	private ArrayList<String> mChildListContent = null;
	private ArrayList<ArrayList<String>> mChildList = null;
	private ExpandableListView elv_event;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_news_event, container,
				false);

		elv_event = (ExpandableListView) v.findViewById(R.id.eventExpList);
		mGroupList = new ArrayList<String>();
		mChildList = new ArrayList<ArrayList<String>>();
		mChildListContent = new ArrayList<String>();
		
		
		mGroupList.add("티아라 애플리케이션 런칭 기념 이벤트");
		mChildListContent.add("안녕하세요 \n티아라네일 앱을 받아주셔서 감사합니다. \n감사의 의미로 앱을 다운받아주신 분들에게 적립금을 5000원씩 드립니다. \n감사합니다^^\n");
		mChildList.add(mChildListContent);
		
		elv_event.setAdapter(new BaseExpandableAdapter(getSherlockActivity(), mGroupList, mChildList));

		return v;
	}
}
