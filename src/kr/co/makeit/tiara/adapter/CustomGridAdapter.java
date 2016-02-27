package kr.co.makeit.tiara.adapter;

import java.util.ArrayList;
import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.model.NailGallary;
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
 * 네일갤러리 어댑터
 * @author leekangsan
 *
 */
public class CustomGridAdapter extends ArrayAdapter<NailGallary> {
	private TextView gridText;
	private ImageView gridImage;
	private Button gridButton;

	private Context context;
	private OnClickListener onClickListener;
	private ArrayList<NailGallary> items;

	public CustomGridAdapter(Context context, int textViewResourceId,
			ArrayList<NailGallary> items, OnClickListener onClickListener) {
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
			NailGallary data = items.get(position);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			grid = inflater.inflate(R.layout.layout_grid_gallary, null);
			gridText = (TextView) grid.findViewById(R.id.grid_text);
			gridImage = (ImageView) grid.findViewById(R.id.grid_image);
			gridButton = (Button) grid.findViewById(R.id.grid_button);
			gridText.setText(data.getTitle());
			gridImage.setImageResource(data.getImgResouce());
			if(data.isFavorite()){
				// gridButton.setBackgroundResource(R.drawable.ic_star_on);
				// 별을 누르면 색이 들어오는 코드이나, 별을 누르면 좋아요 카운트로 사장님만 알 수 있는 코드로 변경
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
