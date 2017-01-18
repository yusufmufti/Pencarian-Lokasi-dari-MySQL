package com.utilities;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lokasi.object.LokasiImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoadingListener;
import com.pencarian.R;

public class ListViewAdapterImg extends BaseAdapter {

	/*
	 * Untuk membantu menampilkan berbagai data (text dan gambar) pada listview
	 */
	
	Context mContext;
	LayoutInflater inflater;
	private List<LokasiImage> lokasiList = null;
	private ArrayList<LokasiImage> arraylist;
	
	private String path_image = "http://sanggar.hol.es/layanankesehatan/image/";
	private ImageLoader imageLoader;
	private DisplayImageOptions options;


	public ListViewAdapterImg(Context context, List<LokasiImage> lokasiList) {
		mContext = context;
		this.lokasiList = lokasiList;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<LokasiImage>();
		this.arraylist.addAll(lokasiList);
	}

	public class ViewHolder {
		TextView nama;
		TextView id;
		TextView alamat;
		TextView latitude;
		TextView longitude;
		ImageView image;
		
	}

	@Override
	public int getCount() {
		return lokasiList.size();
	}

	@Override
	public LokasiImage getItem(int position) {
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
			view = inflater.inflate(R.layout.list_item_img, null);
			// Locate the TextViews in listview_item.xml
			holder.nama = (TextView) view.findViewById(R.id.nama);
			holder.id = (TextView) view.findViewById(R.id.id);
			holder.alamat = (TextView) view.findViewById(R.id.alamat);
			holder.latitude = (TextView) view.findViewById(R.id.latitude);
			holder.longitude = (TextView) view.findViewById(R.id.longitude);
			holder.image = (ImageView) view.findViewById(R.id.image);
			

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		
		holder.nama.setText(lokasiList.get(position).getNama());
		holder.id.setText(lokasiList.get(position).getId());
		holder.alamat.setText(lokasiList.get(position).getAlamat());
		holder.latitude.setText(lokasiList.get(position).getLatitude());
		holder.longitude.setText(lokasiList.get(position).getLongitude());
		
		loadImageFromURL(path_image + lokasiList.get(position).getImage(), holder);
		
		return view;
	}

	
	private void loadImageFromURL(String url, ViewHolder v) {

		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUrl(R.drawable.ic_launcher).cacheInMemory()
				.cacheOnDisc().build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		imageLoader.displayImage(url, v.image, options,
				new ImageLoadingListener() {
					@Override
					public void onLoadingComplete() {

					}

					@Override
					public void onLoadingFailed() {

					}

					@Override
					public void onLoadingStarted() {

					}
				});

	}
	
	
	

	// Filter by Name
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		lokasiList.clear();
		if (charText.length() == 0) {
			lokasiList.addAll(arraylist);
		} else {
			for (LokasiImage wp : arraylist) {
				if (wp.getNama().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					lokasiList.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

	

}
