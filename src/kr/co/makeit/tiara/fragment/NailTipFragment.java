package kr.co.makeit.tiara.fragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.adapter.NailTipGridAdapter;
import kr.co.makeit.tiara.app.App;
import kr.co.makeit.tiara.model.NailTip;
import kr.co.makeit.tiara.model.NailTipLists;
import kr.co.makeit.util.ConnectHttp;
import kr.co.makeit.util.CustomProgress;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * 네일 팁 프래그먼트
 * @author leekangsan
 *
 */
public class NailTipFragment extends SherlockFragment {
	private String nailTipUrl = "getVideoList.tiara?";
	private ArrayList<NailTip> Array_Data;
	private GridView grid;
	private View parentView;
	private View.OnClickListener starListener;
	private NailTipLists[] nailTipLists;
	private NailTipGridAdapter adapter;
	private CustomProgress customProgress;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.fragment_nail_gall, container,
				false);
		starListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), Array_Data.get(Integer.parseInt(v.getTag().toString())).getTitle()+"을(를) 추천하였습니다.", Toast.LENGTH_LONG).show();
			}
		};

		getFragmentManager().popBackStack();
		
		customProgress = new CustomProgress(getActivity(), R.layout.layout_progress);
		
		Array_Data = new ArrayList<NailTip>();
		adapter = new NailTipGridAdapter(getActivity(), R.layout.layout_grid_gallary, Array_Data, starListener);
		grid = (GridView) parentView.findViewById(R.id.grid);
		
		new NailTipTask().execute();
		
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				getFragmentManager().beginTransaction()
						.add(R.id.nailGallContainer, NailTipDetailFragment.newInstance(Array_Data.get(position).getVideoUrl())).addToBackStack(null).commit();
			}
		});

		return parentView;
	}
	
	public class NailTipTask extends AsyncTask<String, String, String>{
		private ConnectHttp connectHttp = new ConnectHttp(); 
		private Bitmap bitmap;
		
		@Override
		protected String doInBackground(String... params) {
			App.setNailTipList(connectHttp.getHttpGetContents(nailTipUrl, ""));
			nailTipLists = App.getNailTipLists();
			
			for(int i=0; i<nailTipLists.length;i++){
				try{
					 InputStream in = new java.net.URL(getYoutubeThumb(nailTipLists[i].getVideo_url())).openStream();
				     bitmap = BitmapFactory.decodeStream(in);
					}catch(FileNotFoundException f){
						try{
							InputStream in = new java.net.URL(getYoutubeThumb2(nailTipLists[i].getVideo_url())).openStream();
							bitmap = BitmapFactory.decodeStream(in);
						}catch(Exception e){
							e.printStackTrace();
						}
					 
				}catch(Exception e){
					e.printStackTrace();
				}
				NailTip data = new NailTip("TIP", nailTipLists[i].getVideo_title() ,R.drawable.nail_tip1, true, nailTipLists[i].getVideo_url(), bitmap);
				adapter.add(data);
			}
			
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			
			grid.setAdapter(adapter);
			customProgress.dismiss();
			
			super.onPostExecute(result);
		}
		
		protected String getYoutubeThumb(String youtubeUrl){
			return "http://i.ytimg.com/vi/"+youtubeUrl.substring(31, 42)+"/maxresdefault.jpg";
		}
		protected String getYoutubeThumb2(String youtubeUrl){
			return "http://i.ytimg.com/vi/"+youtubeUrl.substring(31, 42)+"/hqdefault.jpg";
		}
		
	}
}
