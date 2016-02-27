package kr.co.makeit.tiara.activity;

import kr.co.makeit.tiara.NailCamera;
import kr.co.makeit.tiara.NailView;
import kr.co.makeit.tiara.R;
import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.digitalaria.gama.wheel.Wheel;
import com.digitalaria.gama.wheel.WheelAdapter;
import com.digitalaria.gama.wheel.WheelAdapter.OnItemClickListener;

/**
 * 네일 미리보기 액티비티 
 * @author leekangsan
 *
 */
public class PreviewActivity extends SherlockFragmentActivity implements
		OnItemClickListener {
	private Wheel wheel;
	private Resources res;
	private NailView nailView;
	private NailCamera nailCamera;
	private boolean isCameraStop = false;
	private int[] icons = { R.drawable.nail_no1, R.drawable.nail_no2,
			R.drawable.nail_no3, R.drawable.nail_no4,
			R.drawable.nail_no5, R.drawable.nail_no6,
			R.drawable.nail_no7, R.drawable.nail_no8,
			R.drawable.nail_no9, R.drawable.nail_no10,
			};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_preview);
		setupActionBar();
		init();
		nailView = new NailView(getApplicationContext());
		nailView.setSelectedImage(R.drawable.nail_no1);
		nailCamera = new NailCamera(this);
		((RelativeLayout) findViewById(R.id.test)).addView(nailCamera);
		((RelativeLayout) findViewById(R.id.test)).addView(nailView);
		ImageView img = new ImageView(getApplicationContext());
		img.setImageResource(R.drawable.camera);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				150, 150);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		img.setLayoutParams(params);
		img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(toggleCamera()){
					nailCamera.onCameraFocus(NailCamera.CAMARA_STOP);
				}else{
					nailCamera.startCamera();
				}
				
			}
		});
		((RelativeLayout) findViewById(R.id.test)).addView(img);
		
	}
	
	private boolean toggleCamera(){
		if(isCameraStop==true){
			isCameraStop=false;
		}else{
			isCameraStop=true;
		}
		return isCameraStop;
	}

	private void init() {
		res = getApplicationContext().getResources();
		wheel = (Wheel) findViewById(R.id.wheel);
		wheel.setItems(getDrawableFromData(icons));
		wheel.setOnItemClickListener(this);
		wheel.setWheelDiameter(400);
	}

	private Drawable[] getDrawableFromData(int[] data) {
		Drawable[] ret = new Drawable[data.length];
		for (int i = 0; i < data.length; i++) {
			ret[i] = res.getDrawable(data[i]);
		}
		return ret;
	}

	public void setupActionBar() {

		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.actionbar_preview);

		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00000000")));
		getSupportActionBar().setStackedBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00000000")));

	}
	
	@Override
	public void onItemClick(WheelAdapter<?> parent, View view, int position,
			long id) {
		nailView.setSelectedImage(icons[position]);
	}

}
