package application;

import java.util.List;
import java.util.Map;

public interface IHesaplayici {        
    int guzergahSureHesapla(List<String> path, Map<String, Vertex> vertices);  
    double guzergahUcretHesapla(List<String> path, Yolcu yolcu, Map<String, Vertex> vertices);  
    double calculateDistance(double lat1, double lon1, double lat2, double lon2);
}