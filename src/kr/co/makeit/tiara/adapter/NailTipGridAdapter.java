package kr.co.makeit.tiara.adapter;

import java.util.ArrayList;
import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.model.NailTip;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 네일 팁 어댑터 
 * @author leekangsan
 *
 */
public class NailTipGridAdapter extends ArrayAdapter<NailTip> {
	private TextView gridText;
	private ImageView gridImage;
	private Button gridButton;

	private Context context;
	private OnClickListener onClickListener;
	private ArrayList<NailTip> items;

	public NailTipGridAdapter(Context context, int textViewResourceId,
			ArrayList<NailTip> items, OnClickListener onClickListener) {
		super(context, textViewResourceId, items);
		this.context = context;
		this.items = items;
		this.onClickListener = onClickListener;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View grid = convertView;
		if (grid == null) {
			NailTip data = items.get(position);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			grid = inflater.inflate(R.layout.layout_grid_gallary, null);
			gridText = (TextView) grid.findViewById(R.id.grid_text);
			gridImage = (ImageView) grid.findViewById(R.id.grid_image);
			gridButton = (Button) grid.findViewById(R.id.grid_button);
			gridText.setText(data.getTitle());
			//gridImage.setImageResource(data.getImgResouce());
			
			if(data.isFavorite()){
				gridButton.setBackgroundResource(R.drawable.ic_star_on);
			}
			
			if(data.getGubun().equals("TIP")){
				gridImage.setImageBitmap(data.getYoutubeThumb());
			}else{
				gridImage.setImageBitmap(data.getThumbNail());
			}

		} else {
			grid = (View) convertView;
		}

		if (onClickListener != null) {
			gridButton.setTag(position);  
			gridButton.setOnClickListener(onClickListener);  
		}

		return grid;
	}
}
