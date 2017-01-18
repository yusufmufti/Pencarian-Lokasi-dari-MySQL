package com.lokasi.object;

/*
 * Object utama TempatIbadah
 */
public class Lokasi {

	private String id;
	private String nama;
	private String alamat;
	private String latitude;
	private String longitude;

	public Lokasi(String id, String nama, String alamat, String latitude, String longitude) {

		this.id = id;
		this.nama = nama;
		this.setAlamat(alamat);
		this.setLatitude(latitude);
		this.setLongitude(longitude);

	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
