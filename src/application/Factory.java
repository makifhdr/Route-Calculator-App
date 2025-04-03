package application;

public class Factory implements IFactory {
	
	@Override
	public OdemeYontemi createOdemeYontemi(String type) {
		switch (type.trim()) {
			case "Kredi Kartı":
				return new KrediKartiOdeme();
			case "Nakit":
				return new NakitOdeme();
			case "Kentkart":
				return new KentKartOdeme();
			default:
				throw new IllegalArgumentException("Geçersiz ödeme yöntemi!");
		}
	}
	
	@Override
	public Yolcu createYolcu(String type, double lat, double lon, OdemeYontemi odemeYontemi) {
        switch (type.trim()) {
            case "Genel":
                return new Genel(lat, lon, odemeYontemi);
            case "Öğrenci":
                return new Ogrenci(lat, lon, odemeYontemi);
            case "Yaşlı":
                return new Yasli(lat, lon, odemeYontemi);
            default:
                throw new IllegalArgumentException("Geçersiz yolcu tipi!");
        }
    }
}
