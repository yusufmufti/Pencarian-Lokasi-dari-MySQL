package com.lokasi.object;

/*
 * Object utama TempatIbadah
 */
public class LokasiImage {

	private String id;
	private String nama;
	private String alamat;
	private String latitude;
	private String longitude;
	private String image;

	public LokasiImage(String id, String nama, String alamat, String latitude, String longitude, String image) {

		this.id = id;
		this.nama = nama;
		this.setAlamat(alamat);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setImage(image);

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
