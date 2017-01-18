package com.pencarian;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoadingListener;
import com.utilities.Tag;


public class Peta extends Activity{
	
	// Variabel komponen google map 
	private GoogleMap map = null;
	
	private String nama, gambar;
	private double latitude, longitude;
	
	
	// Variabel testing center lokasi
	private LatLng tambonji = new LatLng(-7.4385208,139.779141);
	
	private String url = "http://sanggar.hol.es/layanankesehatan/image/";
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2980b9")));
		
		// mengambil layout /res/layout/detailsubberanda.xml
		setContentView(R.layout.peta);
		
		image = (ImageView)findViewById(R.id.image);
		
		nama = getIntent().getStringExtra(Tag.NAMA);
		gambar = getIntent().getStringExtra(Tag.IMAGE);
		latitude = getIntent().getDoubleExtra(Tag.LATITUDE, 0);
		longitude = getIntent().getDoubleExtra(Tag.LONGITUDE, 0);
		
		getActionBar().setTitle(nama);

		getMap();
		
		initCamera(new LatLng(latitude, longitude));
		
		loadImageFromURL(url + gambar);
		
	}
	
	// Membantu tombol home untuk kembali ke halaman sebelumnya
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
	    switch (menuItem.getItemId()) {
	        case android.R.id.home:
	            finish();
	            return true;
	    }
	    return (super.onOptionsItemSelected(menuItem));
	}


	
	// inisiasi google map
	public GoogleMap getMap() {

		if (map == null) {
			MapFragment fm = (MapFragment) getFragmentManager()
					.findFragmentById(R.id.map);

			map = fm.getMap();
			return map;
		} else {
			return map;
		}

	}
	
	// untuk membuat marker di peta
	private void AddMarker(LatLng coord, String address){
		 MarkerOptions options = new MarkerOptions().position( coord );
		    options.title( address);
		    getMap().addMarker( options );
	}
	
	
	// untuk mengarahkan lokasi peta
	private void initCamera(LatLng point) {
		CameraPosition position = CameraPosition.builder().target(point)
				.zoom(10.5f).bearing(0.0f).tilt(0.0f).build();
		getMap().animateCamera(CameraUpdateFactory.newCameraPosition(position),
				null);
		getMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
		AddMarker(point, nama);
	}
	
	/*
	 * FUngsi mengambil gambar dari server
	 */
	private void loadImageFromURL(String url) {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUrl(R.drawable.ic_launcher).cacheInMemory()
				.cacheOnDisc().build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(url, image, options,
				new ImageLoadingListener() {
					@Override
					public void onLoadingComplete() {
						image.setVisibility(View.VISIBLE);

					}

					@Override
					public void onLoadingFailed() {

					
					}

					@Override
					public void onLoadingStarted() {
						
					}
				});

	}
	
	
	

}
