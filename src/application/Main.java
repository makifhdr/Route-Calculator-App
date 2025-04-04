package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
    
public class Main{
		
	private static final double esikDeger = 3.0;
	
	public static void main(String[] args) {
		
		IParser parser = new JsonParser();
		IHesaplayici hesaplayici = new Hesaplayici();
		IFactory factory = new Factory();
		
		List<Durak> durakListesi = new ArrayList<>();
		Taxi taxi = null;
		List<String> durakIDListesi = new ArrayList<>();

		try {
		    durakListesi = parser.parseStations("files/veriseti.json");
		    taxi = parser.parseTaxi("files/veriseti.json");

		    if (durakListesi == null || durakListesi.isEmpty()) {
		        throw new Exception("Durak listesi boş veya yüklenemedi!");
		    }

		    if (taxi == null) {
		        throw new Exception("Taksi bilgisi yüklenemedi!");
		    }

		} catch (FileNotFoundException e) {
		    System.err.println("Hata: Dosya bulunamadı! -> " + e.getMessage());
		} catch (IOException e) {
		    System.err.println("Hata: Dosya okunurken bir hata oluştu! -> " + e.getMessage());
		} catch (Exception e) {
		    System.err.println("Bilinmeyen hata: " + e.getMessage());
		}
		

	    boolean taksiKullanCurrent = false;
	    boolean taksiKullanCurrentBus = false;
	    boolean taksiKullanCurrentTram = false;
	    boolean taksiKullanTarget = false;
	    boolean taksiKullanTargetBus = false;
	    boolean taksiKullanTargetTram = false;
		
		
	    Graph graph = new Graph();
	    
	    
	    for(Durak durak : durakListesi) {
			graph.addVertex(durak);
			durakIDListesi.add(durak.getId());
		}
	    
	    for(Vertex vertice : graph.getVertices().values()) {
	    	Durak currentDurak = vertice.getDurak();
	    	
	    	if(!currentDurak.getNextStops().isEmpty()) {
				for(NextStop nextStop : currentDurak.getNextStops()) {
					graph.addEdge(currentDurak.getId(), nextStop.getStopId(), nextStop.getSure());
					graph.addEdge(nextStop.getStopId(), currentDurak.getId(), nextStop.getSure());
				}
	    	}
	    	
	    	Transfer transferDurak = currentDurak.getTransfer();
	    	
	    	if(transferDurak != null) {
	    		
	    		Vertex transferVertex = graph.getVertices().get(transferDurak.getTransferStopId());
	    		
	    		if (transferVertex != null) {
		    		vertice.addEdge(transferVertex, transferDurak.getTransferSure());
	    		}
	    	}
		}
    	
    	Map<String, Vertex> graphVertices = graph.getVertices();
	    
	    if (taxi != null) {
	    	System.out.println("Taksi açılış fiyatı: " + taxi.getOpeningFee() + "\nTaksi km başına ücret: " + taxi.getCostPerKm());
	    } else {
	        System.out.println("Taxi object is null!");
	    }
	    
	    Scanner scanner = new Scanner(System.in);
	    
	    System.out.println("Bulunduğunuz enlemi giriniz: ");
	    
	    double currentLat = scanner.nextDouble();
	    
	    System.out.println("Bulunduğunuz boylamı giriniz: ");
	    
	    double currentLon = scanner.nextDouble();
	    
	    System.out.println("Gitmek istediğiniz enlemi giriniz: ");
	    
	    double targetLat = scanner.nextDouble();
	    
	    System.out.println("Gitmek istediğiniz boylamı giriniz: ");
	    
	    double targetLon = scanner.nextDouble();
	    
	    double distanceToTarget = hesaplayici.calculateDistance(currentLat, currentLon, targetLat, targetLon);
	    
	    System.out.printf("Aradaki mesafe: %,.2f km\n", distanceToTarget);
	    
	    //Buffer clear
	    scanner.nextLine();
	    
	    System.out.println("Ödeme yönteminizi yazınız:(Kredi Kartı, Kentkart, Nakit) ");
	    
	    String odemeTipi = scanner.nextLine();
	    
	    System.out.println("Yolcu tipini yazınız:(Genel, Yaşlı, Öğrenci) ");
	    
	    String yolcuTipi = scanner.nextLine();
	    
	    Yolcu yolcu = factory.createYolcu(yolcuTipi, currentLat, currentLon, factory.createOdemeYontemi(odemeTipi));
	    
	    double distanceToCurrentDurak = Double.MAX_VALUE;
	    double distanceToCurrentBusDurak = Double.MAX_VALUE;
	    double distanceToCurrentTramDurak = Double.MAX_VALUE;
	    
	    Durak currentEnYakinDurak = null;
	    Durak currentEnYakinBusDurak = null;
	    Durak currentEnYakinTramDurak = null;
	    
	    for(Durak durak : durakListesi) {
	    	
	    	double distance = hesaplayici.calculateDistance(currentLat, currentLon, durak.getLat(), durak.getLon());
	    	
	    	if(distanceToCurrentDurak > distance) {
	    		currentEnYakinDurak = durak;
	    		distanceToCurrentDurak = distance;
	    	}
	    	
	    	if(distanceToCurrentBusDurak > distance && durak.getType().equals("bus")) {
	    		currentEnYakinBusDurak = durak;
	    		distanceToCurrentBusDurak = distance;
	    	}

	    	if(distanceToCurrentTramDurak > distance && durak.getType().equals("tram")) {
	    		currentEnYakinTramDurak = durak;
	    		distanceToCurrentTramDurak = distance;
	    	}
	    }
	    
	    System.out.printf("Bulunduğunuz konuma en yakın durak: %s Mesafe: %,.2f km\n", currentEnYakinDurak.getName(), distanceToCurrentDurak);
	    
	    System.out.printf("Bulunduğunuz konuma en yakın otobüs durağı: %s Mesafe: %,.2f km\n", currentEnYakinBusDurak.getName(), distanceToCurrentBusDurak);
	    
	    System.out.printf("Bulunduğunuz konuma en yakın tramvay durağı: %s Mesafe: %,.2f km\n", currentEnYakinTramDurak.getName(), distanceToCurrentTramDurak);
	    
	    if(distanceToCurrentDurak > esikDeger) taksiKullanCurrent = true;
	    if(distanceToCurrentBusDurak > esikDeger) taksiKullanCurrentBus = true;
	    if(distanceToCurrentTramDurak > esikDeger) taksiKullanCurrentTram = true;
	    
	    double distanceToTargetDurak = Double.MAX_VALUE;
	    double distanceToTargetBusDurak = Double.MAX_VALUE;
	    double distanceToTargetTramDurak = Double.MAX_VALUE;
	    
	    Durak targetEnYakinDurak = null;
	    Durak targetEnYakinBusDurak = null;
	    Durak targetEnYakinTramDurak = null;
	    
	    for(Durak durak : durakListesi) {
	    	
	    	double distance = hesaplayici.calculateDistance(targetLat, targetLon, durak.getLat(), durak.getLon());
	    	
	    	if(distanceToTargetDurak > distance) {
	    		targetEnYakinDurak = durak;
	    		distanceToTargetDurak = distance;
	    	}
	    	
	    	if (durak.getType().equals("bus") && distance < distanceToTargetBusDurak) {
	            targetEnYakinBusDurak = durak;
	            distanceToTargetBusDurak = distance;
	        }

	        if (durak.getType().equals("tram") && distance < distanceToTargetTramDurak) {
	            targetEnYakinTramDurak = durak;
	            distanceToTargetTramDurak = distance;
	        }
	    }
	    
	    System.out.printf("Gitmek istediğiniz konuma en yakın durak: %s Mesafe: %,.2f km\n", targetEnYakinDurak.getName(), distanceToTargetDurak);
	    
	    System.out.printf("Gitmek istediğiniz konuma en yakın otobüs durağı: %s Mesafe: %,.2f km\n", targetEnYakinBusDurak.getName(), distanceToTargetBusDurak);
	    
	    System.out.printf("Gitmek istediğiniz konuma en yakın tramvay durağı: %s Mesafe: %,.2f km\n\n", targetEnYakinTramDurak.getName(), distanceToTargetTramDurak);
	    
	    if(distanceToTargetDurak > esikDeger) taksiKullanTarget = true;
	    if(distanceToTargetBusDurak > esikDeger) taksiKullanTargetBus = true;
	    if(distanceToTargetTramDurak > esikDeger) taksiKullanTargetTram = true;
	    
	    List<String> enKisaYol = PathFinder.findShortestPath(currentEnYakinDurak.getId(), targetEnYakinDurak.getId(), graphVertices);
	    List<String> enKisaYolBus = PathFinder.findShortestPathFiltered(currentEnYakinBusDurak.getId(), targetEnYakinBusDurak.getId(), "bus", graphVertices);
	    List<String> enKisaYolTram = PathFinder.findShortestPathFiltered(currentEnYakinTramDurak.getId(), targetEnYakinTramDurak.getId(), "tram", graphVertices);
	    
	    List<String> namePath = new ArrayList<>();
        for (String durakID : enKisaYol) {
            Vertex vertex = graphVertices.get(durakID);
            if (vertex != null) {
                namePath.add(vertex.getDurak().getName());
            }
        }
        
        for (int i = 0; i < enKisaYol.size() - 1; i++) {
        	String durakID = enKisaYol.get(i);
        	String nextDurakID = enKisaYol.get(i+1);
        	
            Vertex vertex = graphVertices.get(durakID);
            Vertex vertexNext = graphVertices.get(nextDurakID);
            if (vertex != null) {
            	if(!vertex.getDurak().getType().equals(vertexNext.getDurak().getType()))
            		namePath.set(i, vertex.getDurak().getName() + "(Aktarma)");
            }
        }
        
        List<String> namePathBus = new ArrayList<>();
        for (String durakID : enKisaYolBus) {
            Vertex vertex = graphVertices.get(durakID);
            if (vertex != null) {
                namePathBus.add(vertex.getDurak().getName());
            }
        }
        
        List<String> namePathTram = new ArrayList<>();
        for (String durakID : enKisaYolTram) {
            Vertex vertex = graphVertices.get(durakID);
            if (vertex != null) {
                namePathTram.add(vertex.getDurak().getName());
            }
        }
        
        double guzergahUcreti = (Math.round(hesaplayici.guzergahUcretHesapla(enKisaYol, yolcu, graphVertices) * 100.0) / 100.0);
        double guzergahUcretiBus = (Math.round(hesaplayici.guzergahUcretHesapla(enKisaYolBus, yolcu, graphVertices) * 100.0) / 100.0);
        double guzergahUcretiTram = (Math.round(hesaplayici.guzergahUcretHesapla(enKisaYolTram, yolcu, graphVertices) * 100.0) / 100.0);
        
        if(taksiKullanCurrent)
        	guzergahUcreti += distanceToCurrentDurak * taxi.getCostPerKm() + taxi.getOpeningFee();
        if(taksiKullanCurrentBus)
        	guzergahUcretiBus += distanceToCurrentBusDurak * taxi.getCostPerKm() + taxi.getOpeningFee();
        if(taksiKullanCurrentTram)
        	guzergahUcretiTram += distanceToCurrentTramDurak * taxi.getCostPerKm() + taxi.getOpeningFee();
        
        if(taksiKullanTarget)
        	guzergahUcreti += distanceToTargetDurak* taxi.getCostPerKm() + taxi.getOpeningFee();
        if(taksiKullanTargetBus)
        	guzergahUcretiBus += distanceToTargetBusDurak* taxi.getCostPerKm() + taxi.getOpeningFee();
        if(taksiKullanTargetTram)
        	guzergahUcretiTram += distanceToTargetTramDurak* taxi.getCostPerKm() + taxi.getOpeningFee();
        	  
	    System.out.printf("Sadece taksi ile giderseniz;\nÜcret: %,.2f TL\n\n", (taxi.getOpeningFee() + (taxi.getCostPerKm() * distanceToTarget)));
	    
	    yolBilgisiYazdir("Otobüs ve Tramvay", currentEnYakinDurak.getName(), targetEnYakinDurak.getName(), namePath, guzergahUcreti, hesaplayici.guzergahSureHesapla(enKisaYol, graphVertices), taksiKullanCurrent, taksiKullanTarget, graphVertices);
	    
	    yolBilgisiYazdir("Otobüs", currentEnYakinBusDurak.getName(), targetEnYakinBusDurak.getName(), namePathBus, guzergahUcretiBus, hesaplayici.guzergahSureHesapla(enKisaYolBus, graphVertices), taksiKullanCurrentBus, taksiKullanTargetBus, graphVertices);
         
	    yolBilgisiYazdir("Tramvay", currentEnYakinTramDurak.getName(), targetEnYakinTramDurak.getName(), namePathTram, guzergahUcretiTram, hesaplayici.guzergahSureHesapla(enKisaYolTram, graphVertices), taksiKullanCurrentTram, taksiKullanTargetTram, graphVertices);
	    	    
	    	    	   
    	scanner.close();

	}
	
	private static void yolBilgisiYazdir(
		    String tasimaTipi,
		    String baslangicDurak,
		    String hedefDurak,
		    List<String> yol,
		    double guzergahUcreti,
		    int guzergahSuresi,
		    boolean taksiKullanCurrent1,
		    boolean taksiKullanTarget1,
		    Map<String, Vertex> graphVertices
		) {
		
		if(yol.isEmpty())
			System.out.println(baslangicDurak + " ve " + hedefDurak + "arası yol bulunamadı");
		else {
			System.out.printf("Sadece %s kullanarak: %s ve %s arası en kısa yol: %s\n", tasimaTipi, baslangicDurak, hedefDurak, yol);
		    System.out.println("Süre: " + guzergahSuresi + " dk");
		    System.out.printf("Ücret: %,.2f TL", guzergahUcreti);
		    if(taksiKullanCurrent1) System.out.printf("\nMesafe %,.1f km den fazla olduğu için %s durağına giderken taksi kullanıldı", Main.esikDeger, tasimaTipi);
		    if(taksiKullanTarget1) System.out.printf("\nMesafe %,.1f km den fazla olduğu için %s durağından hedefe giderken taksi kullanıldı\n\n", Main.esikDeger, tasimaTipi);
		    System.out.println();
		}
			
		
	}
}
