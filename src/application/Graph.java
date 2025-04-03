package application;

import java.util.*;

public class Graph {
	private Map<String, Vertex> vertices = new HashMap<>();
    
    public Graph() {
    	this.vertices = new HashMap<>();
    }
    
    public Vertex addVertex(Durak durak) {
    	if (!vertices.containsKey(durak.getId())) {
            Vertex newVertex = new Vertex(durak);
            vertices.put(durak.getId(), newVertex);
        }
        return vertices.get(durak.getId()); // Return existing or new vertex
    }
    
    public void addEdge(String durakId1, String durakId2, Integer weight) {
    	Vertex vertex1 = vertices.get(durakId1);
        Vertex vertex2 = vertices.get(durakId2);

        if (vertex1 != null && vertex2 != null) {
            vertex1.addEdge(vertex2, weight);
        } else {
            System.out.println("Error: One or both Durak IDs not found.");
        }
    }
    
    public Map<String, Vertex> getVertices() {
    	return this.vertices;
    }
}