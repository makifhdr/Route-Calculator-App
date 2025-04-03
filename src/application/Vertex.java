package application;

import java.util.ArrayList;
import java.util.List;

public class Vertex{
	private Durak durak;
	private List<Edge> edges;
	
	public Vertex() {
		this.edges = null;
		this.durak = null;
	}
	
	public Vertex(Durak durak){
		this.edges = new ArrayList<>();
		this.durak = durak;
	}
	
	public void addEdge(Vertex endV, Integer weight) {
        this.edges.add(new Edge(this, endV, weight));
    }
	
	public List<Edge> getEdges(){
		return this.edges;
	}
	
	public Durak getDurak() {
		return this.durak;
	}
}
