package com.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.lokasi.object.Lokasi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pencarian.R;

public class ListViewAdapter extends BaseAdapter {

	/*
	 * Untuk membantu menampilkan berbagai data (text dan gambar) pada listview
	 */
	
	Context mContext;
	LayoutInflater inflater;
	private List<Lokasi> lokasiList = null;
	private ArrayList<Lokasi> arraylist;

	public ListViewAdapter(Context context, List<Lokasi> lokasiList) {
		mContext = context;
		this.lokasiList = lokasiList;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<Lokasi>();
		this.arraylist.addAll(lokasiList);
	}

	public class ViewHolder {
		TextView nama;
		TextView id;
		TextView alamat;
		TextView latitude;
		TextView longitude;
		
	}

	@Override
	public int getCount() {
		return lokasiList.size();
	}

	@Override
	public Lokasi getItem(int position) {
		return lokasiList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.list_item, null);
			// Locate the TextViews in listview_item.xml
			holder.nama = (TextView) view.findViewById(R.id.nama);
			holder.id = (TextView) view.findViewById(R.id.id);
			holder.alamat = (TextView) view.findViewById(R.id.alamat);
			holder.latitude = (TextView) view.findViewById(R.id.latitude);
			holder.longitude = (TextView) view.findViewById(R.id.longitude);
			

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		
		holder.nama.setText(lokasiList.get(position).getNama());
		holder.id.setText(lokasiList.get(position).getId());
		holder.alamat.setText(lokasiList.get(position).getAlamat());
		holder.latitude.setText(lokasiList.get(position).getLatitude());
		holder.longitude.setText(lokasiList.get(position).getLongitude());
		
		
		
		return view;
	}

	
	
	

	// Filter by Name
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		lokasiList.clear();
		if (charText.length() == 0) {
			lokasiList.addAll(arraylist);
		} else {
			for (Lokasi wp : arraylist) {
				if (wp.getNama().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					lokasiList.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

	

}
