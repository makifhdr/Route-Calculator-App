package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


public class PathFinder {
	public static List<String> findShortestPath(String start, String end, Map<String, Vertex> vertices) {
        if (!vertices.containsKey(start) || !vertices.containsKey(end)) {
            System.out.println("Error: Invalid vertices");
            return Collections.emptyList();
        }

        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<QueueObject> pq = new PriorityQueue<>(Comparator.comparingDouble(q -> q.distance));

        // Initialize distances
        for (String key : vertices.keySet()) {
            distances.put(key, Double.MAX_VALUE);
            previous.put(key, null);
        }

        // Start at the source
        distances.put(start, 0.0);
        pq.add(new QueueObject(start, 0));

        while (!pq.isEmpty()) {
            QueueObject current = pq.poll();
            Vertex currentVertex = vertices.get(current.vertex);

            if (current.vertex.equals(end)) break; // Early exit

            for (Edge edge : currentVertex.getEdges()) {
                String neighbor = edge.getEndV().getDurak().getId();
                
                double newDist = distances.get(current.vertex) + edge.getWeight();

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, current.vertex);
                    pq.add(new QueueObject(neighbor, newDist));
                }
            }
        }

        // Reconstruct path
        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = previous.get(at)) {
            path.add(0, at);
        }

        // If the path only contains one element, no path exists
        if (path.size() == 1 && !path.get(0).equals(start)) {
            System.out.println("No path found.");
            return Collections.emptyList();
        }

        return path;
    }
	
	public static List<String> findShortestPathFiltered(String start, String end, String filterType, Map<String, Vertex> vertices) {
    	if (!vertices.containsKey(start) || !vertices.containsKey(end)) {
            System.out.println("Error: Invalid vertices");
            return Collections.emptyList();
        }

        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<QueueObject> pq = new PriorityQueue<>(Comparator.comparingDouble(q -> q.distance));

        // Initialize distances
        for (String key : vertices.keySet()) {
            distances.put(key, Double.MAX_VALUE);
            previous.put(key, null);
        }

        // Start at the source
        distances.put(start, 0.0);
        pq.add(new QueueObject(start, 0));

        while (!pq.isEmpty()) {
            QueueObject current = pq.poll();
            Vertex currentVertex = vertices.get(current.vertex);

            if (current.vertex.equals(end)) break;

            for (Edge edge : currentVertex.getEdges()) {
                String neighbor = edge.getEndV().getDurak().getId();
                String neighborType = edge.getEndV().getDurak().getType();
                
                if(!neighborType.equals(filterType)) continue;
                
                double newDist = distances.get(current.vertex) + edge.getWeight();

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, current.vertex);
                    pq.add(new QueueObject(neighbor, newDist));
                }
            }
        }

        // Reconstruct path
        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = previous.get(at)) {
            path.add(0, at);
        }

        // If the path only contains one element, no path exists
        if (path.size() == 1) return Collections.emptyList();
                
        return path;
    }
}
