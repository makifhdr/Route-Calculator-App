package application;

public interface OdemeYontemi {
	
	public String getYontemIsmi();
	
}

class KrediKartiOdeme implements OdemeYontemi{
	
	@Override
	public String getYontemIsmi() {
		return "Kredi Kartı";
	}
	
}

class NakitOdeme implements OdemeYontemi{
	
	@Override
	public String getYontemIsmi() {
		return "Nakit";
	}
}

class KentKartOdeme implements OdemeYontemi{
	
	@Override
	public String getYontemIsmi() {
		return "Kentkart";
	}
}