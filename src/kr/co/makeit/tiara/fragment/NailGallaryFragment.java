package kr.co.makeit.tiara.fragment;

import java.io.InputStream;
import java.util.ArrayList;

import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.adapter.NailTipGridAdapter;
import kr.co.makeit.tiara.app.App;
import kr.co.makeit.tiara.model.NailGallLists;
import kr.co.makeit.tiara.model.NailTip;
import kr.co.makeit.util.ConnectHttp;
import kr.co.makeit.util.CustomProgress;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * 네일 갤러리 프래그먼트
 * @author leekangsan
 *
 */
public class NailGallaryFragment extends SherlockFragment {
	private CustomProgress customProgress;
	private String nailGallaryUrl = "galleryTotalList.tiara?";
	private ArrayList<NailTip> Array_Data;
	private GridView grid;
	private OnClickListener starListener;
	private NailGallLists[] nailGallLists;
	private NailTipGridAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_nail_gall, container, false);
		
		starListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), Array_Data.get(Integer.parseInt(v.getTag().toString())).getTitle()+"을(를) 추천하였습니다.", Toast.LENGTH_LONG).show();
				//v.setSelected(true);
				
			}
		};

		getFragmentManager().popBackStack();
		
		Array_Data = new ArrayList<NailTip>();
		adapter = new NailTipGridAdapter(getActivity(), R.layout.layout_grid_gallary, Array_Data, starListener);
		
		customProgress = new CustomProgress(getActivity(), R.layout.layout_progress);
		NailGallaryTask nailGallaryTask = new NailGallaryTask();
		nailGallaryTask.execute(nailGallaryUrl);
		
		grid = (GridView) v.findViewById(R.id.grid);
		
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				getFragmentManager().beginTransaction()
						.add(R.id.nailGallContainer, NailGallaryDetailFragment.newInstance(Array_Data.get(position).getFileUrl(), Array_Data.get(position).getTitle(), Array_Data.get(position).getgBody())).addToBackStack(null)
					.commit();
			}
		});
		return v;
	}
	
	public class NailGallaryTask extends AsyncTask<String, String, String>{
		private ConnectHttp connectHttp = new ConnectHttp();
		private Bitmap bitmap;
		
		@Override
		protected String doInBackground(String... params) {
			App.setNailGallList(connectHttp.getHttpGetContents(nailGallaryUrl, ""));
			nailGallLists = App.getNailGallLists();

			for(int i=0; i<nailGallLists.length;i++){
				try{
				 InputStream in = new java.net.URL(App.getHost()+nailGallLists[i].getgThumbNail()).openStream();
			     bitmap = BitmapFactory.decodeStream(in);
				}catch(Exception e){
					e.printStackTrace();
				}
				NailTip data = new NailTip("GALL", nailGallLists[i].getgTitle(), bitmap, nailGallLists[i].getgFileUrl(), nailGallLists[i].getgBody(), false);
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
	}
	
}
