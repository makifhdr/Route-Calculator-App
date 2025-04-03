package application;

public class Edge {
    private Vertex startV;
    private Vertex endV;
    private Integer weight;

    public Edge(Vertex start, Vertex end, Integer weight) {
        this.startV = start;
        this.endV = end;
        this.weight = weight;
    }
    
    public Vertex getStartV() {
    	return this.startV;
    }
    
    public Vertex getEndV() {
    	return this.endV;
    }
    
    public Integer getWeight() {
    	return this.weight;
    }
}