package kr.co.makeit.tiara.fragment;

import java.util.ArrayList;

import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.adapter.BaseExpandableAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * 티아라 소식 프래그먼트 
 * @author leekangsan
 *
 */
public class NewsNoticeFragment extends SherlockFragment {

	private ArrayList<String> mGroupList = null;
	private ArrayList<ArrayList<String>> mChildList = null;
	private ExpandableListView elv_news;
	private String result;

	public NewsNoticeFragment(){
	}
	
	public NewsNoticeFragment(String result) {
		this.result = result;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_news_notice, container,
				false);

		elv_news = (ExpandableListView) v.findViewById(R.id.noticeExpList);
		mGroupList = new ArrayList<String>();
		mChildList = new ArrayList<ArrayList<String>>();

		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				String title = obj.getString("title");
				String content = obj.getString("content");

				mGroupList.add(title);
				mChildList.add(new ArrayList<String>());
				mChildList.get(i).add(content);
			}
			elv_news.setAdapter(new BaseExpandableAdapter(
					getSherlockActivity(), mGroupList, mChildList));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return v;
	}

}
