package application;

import java.util.List;
import java.util.Map;

public class Hesaplayici implements IHesaplayici {
	
	@Override
	public int guzergahSureHesapla(List<String> path, Map<String, Vertex> vertices) {
	        int totalTime = 0;

	        for (int i = 0; i < path.size() - 1; i++) {
	            String from = path.get(i);
	            String to = path.get(i + 1);
	            
	            Vertex fromVertex = vertices.get(from);

	            if (fromVertex != null) {
	                for (Edge edge : fromVertex.getEdges()) {
	                    if (edge.getEndV().getDurak().getId().equals(to)) {
	                        totalTime += edge.getWeight(); // Assuming weight represents time
	                        break;
	                    }
	                }
	            }
	        }
	        
	        return totalTime;
	    }
	    

	    @Override
	    public double guzergahUcretHesapla(List<String> path, Yolcu yolcu, Map<String, Vertex> vertices) {
			double totalFee = 0.0;
			
			for (int i = 0; i < path.size() - 1; i++) {
		        String currentId = path.get(i);
		        String nextId = path.get(i + 1);
		        
		        Vertex currentVertex = vertices.get(currentId);
		        Durak currentDurak = currentVertex.getDurak();
		        
		        // Find the fee in nextStops list
		        for (NextStop nextStop : currentDurak.getNextStops()) {
		            if (nextStop.getStopId().equals(nextId)) {
		                totalFee += yolcu.ucretHesapla(nextStop.getUcret());
		                break;
		            }
		        }
		        
		        for(NextStop nextStop : vertices.get(nextId).getDurak().getNextStops()) {
		        	if (nextStop.getStopId().equals(currentId)) {
		                totalFee += yolcu.ucretHesapla(nextStop.getUcret());
		                break;
		            }
		        }
		        
		        if (currentDurak.getTransfer() != null && currentDurak.getTransfer().getTransferStopId().equals(nextId)) {
		            totalFee += currentDurak.getTransfer().getTransferUcret();
		        }
		    }
			
			return totalFee;
	    }    
		
		public static final double EARTH_RADIUS = 6371000; 

	    public double toRadians(double degree) {
	        return degree * (Math.PI / 180);
	    }
	    
	    @Override
	    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

	        if (lat1 < -90 || lat1 > 90 || lat2 < -90 || lat2 > 90 ||
	            lon1 < -180 || lon1 > 180 || lon2 < -180 || lon2 > 180) {
	            throw new IllegalArgumentException("Coordinates must be valid: Latitude [-90,90], Longitude [-180,180]");
	        }
	        
	        double lat1Rad = toRadians(lat1);
	        double lon1Rad = toRadians(lon1);
	        double lat2Rad = toRadians(lat2);
	        double lon2Rad = toRadians(lon2);
	        
	        double deltaLat = lat2Rad - lat1Rad;
	        double deltaLon = lon2Rad - lon1Rad;
	        
	        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
	                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
	                Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
	        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	        
	        return (EARTH_RADIUS * c)/1000;
	    }
}
