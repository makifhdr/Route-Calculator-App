package application;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

class Durak {
    private String id;
    private String name;
    private String type;
    private double lat;
    private double lon;
    private Boolean sonDurak;
    private List<NextStop> nextStops;
    private Transfer transfer;
    
    public Durak() {
    	this.id = "";
        this.name = "";
        this.type = "";
        this.lat = 0.0;
        this.lon = 0.0;
        this.sonDurak = null;
        this.nextStops = null;
        this.transfer = null;
    }
    
    public Durak(
    		@JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("type") String type,
            @JsonProperty("lat") double lat,
            @JsonProperty("lon") double lon,
            @JsonProperty("sonDurak") Boolean sonDurak,
            @JsonProperty("nextStops") List<NextStop> nextStops,
            @JsonProperty("transfer") Transfer transfer) {
    	
    	this.id = id;
        this.name = name;
        this.type = type;
        this.lat = lat;
        this.lon = lon;
        this.sonDurak = sonDurak;
        this.nextStops = nextStops;
        this.transfer = transfer;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public Boolean getSonDurak() { return sonDurak; }
    public List<NextStop> getNextStops() { return nextStops; }
    public Transfer getTransfer() { return transfer; }

    public void setNextStops(List<NextStop> nextStops) { this.nextStops = nextStops; }
    public void setTransfer(Transfer transfer) { this.transfer = transfer; }
    
}
