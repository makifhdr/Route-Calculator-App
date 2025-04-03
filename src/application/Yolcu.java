package application;

abstract class Yolcu {
	private double lat;
	private double lon;
	protected double indirimKatsayi;
	private OdemeYontemi odemeYontemi;
	
	Yolcu(){
		this.lat = 0.0;
		this.lon = 0.0;
		this.indirimKatsayi = 0.0;
		this.odemeYontemi = null;
	}
	
	public Yolcu(double lat, double lon, OdemeYontemi odemeYontemi, double indirimKatsayi){
		this.lat = lat;
		this.lon = lon;
		this.odemeYontemi = odemeYontemi;
		this.indirimKatsayi = indirimKatsayi;
	}
	
	public double getLat() {
		return this.lat;
	}
	
	public double getLon() {
		return this.lon;
	}
	
	public double ucretHesapla(double baseUcret) {
		if(odemeYontemi instanceof KentKartOdeme)
			return this.indirimKatsayi*baseUcret;
		else
			return baseUcret;
	}
}

class Genel extends Yolcu {
	Genel(double lat, double lon, OdemeYontemi odemeYontemi){
		super(lat, lon, odemeYontemi, 1.0);
	}
}

class Ogrenci extends Yolcu{
	Ogrenci(double lat, double lon, OdemeYontemi odemeYontemi){
		super(lat, lon, odemeYontemi, 0.75);
	}
}

class Yasli extends Yolcu {
	Yasli(double lat, double lon, OdemeYontemi odemeYontemi){
		super(lat, lon, odemeYontemi, 0.5);
	}
}