package application;

public interface IFactory {
	OdemeYontemi createOdemeYontemi(String type);
	Yolcu createYolcu(String type, double lat, double lon, OdemeYontemi odemeYontemi);	
}
