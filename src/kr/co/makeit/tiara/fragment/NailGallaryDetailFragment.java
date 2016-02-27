package kr.co.makeit.tiara.fragment;

import java.io.InputStream;

import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.app.App;
import kr.co.makeit.util.CustomProgress;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * 네일갤러리 상세보기 프래그먼트
 * @author leekangsan
 *
 */
public class NailGallaryDetailFragment extends SherlockFragment {
	private String imgRes;
	private String txt;
	private String title;
	private ImageView imageView;
	CustomProgress customProgress;

	public static NailGallaryDetailFragment newInstance(String imgRes, String title, String txt){
		NailGallaryDetailFragment fragment = new NailGallaryDetailFragment();
		Bundle localBundle = new Bundle();
		localBundle.putString("imgRes", imgRes);
		localBundle.putString("txt", txt);
		localBundle.putString("title", title);
		fragment.setArguments(localBundle);
		
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_nail_gall_detail,
				container, false);
		
		this.imgRes = getArguments().getString("imgRes");
		
		imageView = ((ImageView) v.findViewById(R.id.gall_detail_image));
		//((ImageView) v.findViewById(R.id.gall_detail_image))
		//.setImageResource(this.imgRes);
		
		this.title = getArguments().getString("title");
		((TextView) v.findViewById(R.id.gall_detail_title))
		.setText(this.title);
		
		this.txt = getArguments().getString("txt");
		((TextView) v.findViewById(R.id.gall_detail_text))
		.setText(this.txt);
		
		Log.i("TEST", title+"ASD");
		
		customProgress = new CustomProgress(getActivity(), R.layout.layout_progress);
		NailGallaryImageDownloadTask imageDownloadTask = new NailGallaryImageDownloadTask();
		imageDownloadTask.execute(imgRes);

		return v;
	}
	
	public class NailGallaryImageDownloadTask extends AsyncTask<String, String, String>{
		Bitmap bitmap;
		
		@Override
		protected String doInBackground(String... params) {
				try{
				 InputStream in = new java.net.URL(App.getHost()+params[0]).openStream();
			     bitmap = BitmapFactory.decodeStream(in);
				}catch(Exception e){
					e.printStackTrace();
				}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			imageView.setImageBitmap(bitmap);
			customProgress.dismiss();
//			customProgress.dismiss();
			super.onPostExecute(result);
		}
	}
	
	
	
}
