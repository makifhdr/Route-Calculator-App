package application;

import com.fasterxml.jackson.annotation.JsonProperty;

class NextStop {
	
    private String stopId;
	private double mesafe;
	private int sure;
	private double ucret;
	
	public NextStop() {
    	this.stopId = "";
    	this.mesafe = 0.0;
    	this.sure = 0;
    	this.ucret = 0.0;
    }
    
    public NextStop(
    		@JsonProperty("stopId") String stopId,
            @JsonProperty("mesafe") double mesafe,
            @JsonProperty("sure") int sure,
            @JsonProperty("ucret") double ucret) {
    	
    	this.stopId = stopId;
    	this.mesafe = mesafe;
    	this.sure = sure;
    	this.ucret = ucret;
    }
    
    public String getStopId() { return stopId; }
    public double getMesafe() { return mesafe; }
    public int getSure() { return sure; }
    public double getUcret() { return ucret; }
    
}
