package application;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transfer {
    private String transferStopId;
    private int transferSure;
    private double transferUcret;
    
    public Transfer(){
    	this.transferStopId = "";
    	this.transferSure = 0;
    	this.transferUcret = 0.0;
    }
    
    public Transfer(
    		@JsonProperty("transferStopId") String transferStopId,
            @JsonProperty("transferSure") int transferSure,
            @JsonProperty("transferUcret") double transferUcret){
    	
    	this.transferStopId = transferStopId;
    	this.transferSure = transferSure;
    	this.transferUcret = transferUcret;
    }
    
    public String getTransferStopId() { return transferStopId; }
    public int getTransferSure() { return transferSure; }
    public double getTransferUcret() { return transferUcret; }
}
