package application;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City{
	
	private String city;
	private Taxi taxi;
	private List<Durak> duraklar;
	
	City(){}
	
	public String getCity() { return city; }
    public Taxi getTaxi() { return taxi; }
    public List<Durak> getDuraklar() { return duraklar; }
	
}
