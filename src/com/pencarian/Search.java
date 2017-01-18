package com.pencarian;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lokasi.object.Lokasi;
import com.network.ConnectionDetector;
import com.utilities.ConnectServer;
import com.utilities.G;
import com.utilities.ListViewAdapter;
import com.utilities.Tag;

/*
 * Halaman pencarian
 * Pengguna : User
 */

public class Search extends Activity {

	/*
	 * Deklarasi variabel
	 */
	
	private ListView listview; 
	private ArrayList<Lokasi> dtList = new ArrayList<Lokasi>();
	private JSONArray arrayJson=null;
	private ListViewAdapter adapter;
	
	private ListViewAdapter adapterDefault;
	private ArrayList<Lokasi> dtListDefault = new ArrayList<Lokasi>();
	private ConnectionDetector connectionDetector;
	private boolean isConnected;
	private EditText cari;
	
	// Variabel komponen google map 
		private GoogleMap map = null;
		
		private String nama;
		private double latitude, longitude;
		
		// Variabel testing center lokasi
		private LatLng tambonji = new LatLng(-7.4385208,139.779141);
		
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2980b9")));
		
		/*
		 * Inisiasi variabel komponen
		 */
		listview = (ListView)findViewById(R.id.list);
		cari	 = (EditText)findViewById(R.id.cari);

		getMap();
		
		/*
		 * Mengecek internet
		 */
		
		connectionDetector= new ConnectionDetector(getApplicationContext());
		isConnected = connectionDetector.isConnectingToInternet();

		/*
		 * Jika list data ditekan maka membuka halama Detail,java
		 */
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent i = new Intent(getApplicationContext(), Peta.class);
					String nama = dtList.get(position).getNama();
					double latitude = Double.parseDouble(dtList.get(position).getLatitude());
					double longitude = Double.parseDouble(dtList.get(position).getLongitude());
					
					
					initCamera(new LatLng(latitude, longitude), nama);
					
					listview.setVisibility(View.GONE);
    				
					
					
					
					
					
				}
			});
			
		
			/*
			 * Fungsi untuk mengecek inputan pada kotak input
			 */
		cari.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(isConnected){
					if(s.length()>2){
						CariData(String.valueOf(s));
					}else if(s.length()<1){
						
						listview.setAdapter(null);
						listview.setVisibility(View.GONE);
						map.clear();
	    				
						
					}
				}else{
					G.n("Internet tidak tersedia", getApplicationContext());
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				  
				
			}
		});

	}

	/*
	 * Membuat menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
		}
		
		if (id == R.id.action_settings) {
			Intent i = new Intent(getApplicationContext(), SearchPuskesmas.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	
	/*
	 * Fungsi mengambil data di server berdasarkan parameter pencarian dan menampilkan hasilnya
	 */
	public void CariData(final String key){
		
		final String url ="http://sanggar.hol.es/layanankesehatan/cari.php?key="+key;
		listview.setAdapter(null);
		
		if(key.length()>0 ){
			
				
			
			 final List<NameValuePair> params = new ArrayList<NameValuePair>();
		        
		        params.add(new BasicNameValuePair(Tag.NAMA, key));
		        
		      
		        new AsyncTask<ConnectServer,Long, JSONObject >() {
		        	
		        	protected void onPreExecute() {
		        		G.n("Mencari..", getApplicationContext());
		        	};
		        	
		            protected JSONObject doInBackground(ConnectServer... apiConnectors) {
		                return apiConnectors[0].getJSONFromUrl(url);
		            }
		            
		            protected void onPostExecute(JSONObject result) {
		            	try {
		    				dtList.clear();
		    				

		    				arrayJson = result.getJSONArray("lokasi");
		    				Log.e("result", result.toString());
		    				Log.e("jumlah", String.valueOf(arrayJson.length()));

		    				for (int i = 0; i < arrayJson.length(); i++) {

		    					JSONObject c = arrayJson.getJSONObject(i);

		    					String id = c.getString("id").trim();
		    					String nama = c.getString("nama").trim();
		    					String alamat = c.getString("alamat").trim();
		    					String latitude = c.getString("latitude").trim();
		    					String longitude = c.getString("longitude").trim();
		    					
		    					
		    					
		    					
		    					Lokasi lokasi = new Lokasi(id, nama, alamat, latitude, longitude);
		    					
		    					dtList.add(lokasi);

		    				}
		    				
		    				G.l(dtList.toString());
		    				
		    				dtListDefault = dtList;
		    				
		    				adapter = new ListViewAdapter(getApplicationContext(), dtList);
		    				adapterDefault = new ListViewAdapter(getApplicationContext(), dtListDefault);
		    				
		    				
		    				listview.setAdapter(adapter);
		    				listview.setVisibility(View.VISIBLE);
		    				

		    			} catch (JSONException e) {
		    				e.printStackTrace();
		    	            
		    	        } 
		            
		            };
		            
		        }.execute(new ConnectServer());
			
			
		}else{
			
		}
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
		private void initCamera(LatLng point, String nama) {
			CameraPosition position = CameraPosition.builder().target(point)
					.zoom(14.5f).bearing(0.0f).tilt(0.0f).build();
			getMap().animateCamera(CameraUpdateFactory.newCameraPosition(position),
					null);
			getMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
			
			AddMarker(point, nama);
		}
		

		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {

			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
	

}
