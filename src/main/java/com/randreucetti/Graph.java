package com.randreucetti;

import java.util.List;

public interface Graph {
	public void addNode(String name, Vertex v);

	public void addRoute(String source, String destination, int distance);

	public int getDistanceOfRoute(String... path);

	public List<List<String>> getAllPathsLessThanDistance(String source, String destination, int distance);

	public List<List<String>> getAllPathsWithMaxStops(String source, String destination, int maxStops);

	public List<List<String>> getAllPathsWithNumStops(String source, String destination, int numStops);

	public List<String> getShortestPath(String source, String destination);

}
