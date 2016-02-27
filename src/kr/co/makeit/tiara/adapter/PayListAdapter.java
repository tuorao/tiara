package kr.co.makeit.tiara.adapter;

import java.util.ArrayList;

import kr.co.makeit.tiara.R;
import kr.co.makeit.tiara.model.PayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 적립금 리스트 어댑터
 * @author leekangsan
 *
 */
public class PayListAdapter extends ArrayAdapter<PayList> {
	private TextView service;
	private TextView charge;
	private TextView discount;
	private TextView balance;
	private TextView date;
	
	private Context context;

	private ArrayList<PayList> items;

	public PayListAdapter(Context context, int textViewResourceId,
			ArrayList<PayList> items) {
		super(context, textViewResourceId, items);
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.layout_list_paylist, null);
		}
		PayList paylist = items.get(position);

		if (paylist != null) {
			service = (TextView) v.findViewById(R.id.paylist_service);
			charge = (TextView) v.findViewById(R.id.paylist_charge);
			discount = (TextView) v.findViewById(R.id.paylist_discount);
			balance = (TextView) v.findViewById(R.id.paylist_balance);
			date =  (TextView) v.findViewById(R.id.paylist_date);
			
			service.setText(paylist.getService());
			charge.setText(paylist.getCharge());
			discount.setText(paylist.getDiscount());
			balance.setText(paylist.getBalance());
			date.setText(paylist.getDate());
			
		}
		return v;
	}
}
