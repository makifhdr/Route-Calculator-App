package application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

abstract class Arac {
	
}

class Tramvay extends Arac{
	
}

class Otobus extends Arac{
	
}

class Taxi extends Arac{
	private double openingFee;
    private double costPerKm;
    
    public Taxi() {
    	this.openingFee = 0.0;
    	this.costPerKm = 0.0;
    }
    
    @JsonCreator
    public Taxi(
    		@JsonProperty("openingFee")double openingFee, 
    		@JsonProperty("costPerKm")double costPerKm) {
    	this.openingFee = openingFee;
    	this.costPerKm = costPerKm;
    }
    
    public double getOpeningFee() {
    	return this.openingFee;
    }
    
    public double getCostPerKm() {
    	return this.costPerKm;
    }
}