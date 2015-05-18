package com.randreucetti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The KiwiRail class which implements {@link Graph} details are given in the JavaDoc of each method about the implementation
 * 
 * @author ross
 *
 */
public class KiwiRail implements Graph {
	private final Map<String, Vertex> stations;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public KiwiRail() {
		this.stations = new HashMap<String, Vertex>();
	}

	@Override
	public void addRoute(String source, String destination, int distance) {
		if (distance < 0) {
			logger.error("Distance must not be less than 0");
			return;
		}
		if (!stations.containsKey(source)) {
			this.addNode(source, new Station(source));
		}
		if (!stations.containsKey(destination)) {
			this.addNode(destination, new Station(destination));
		}
		stations.get(source).addNeighbour(destination, new Edge(stations.get(destination), distance));
	}

	private void addNode(String key, Vertex station) {
		logger.debug("Adding station: {}", station);
		stations.put(key, station);
	}

	@Override
	public int getDistanceOfRoute(String... path) {
		int totalDistance = 0;
		for (int i = 0; i < path.length - 1; i++) {
			Vertex station = stations.get(path[i]);
			if (station == null) {
				logger.warn("Station {} does not exist, returning -1", path[i]);
				return -1;
			}
			int distToNeigbhour = station.getDistanceToNeighbour(path[i + 1]);
			if (distToNeigbhour != -1) {
				totalDistance += distToNeigbhour;
			} else {
				logger.warn("No path {} could be found, returning {}", path, -1);
				return -1;
			}

		}
		logger.info("Distance for path {} is {}", path, totalDistance);
		return totalDistance;
	}

	/**
	 * Based upon a recursive Breadth-first search. See See <a href="http://en.wikipedia.org/wiki/Breadth-first_search"
	 * >http://en.wikipedia.org/wiki/Breadth-first_search</a>
	 */
	@Override
	public List<List<String>> getAllPathsWithMaxStops(String source, String destination, int maxStops) {
		List<List<String>> paths = new ArrayList<List<String>>();
		Vertex srcVertex = stations.get(source);
		if (srcVertex == null) {
			logger.error("Source node {} does not exit", source);
			return paths;
		}
		Vertex dstVertex = stations.get(destination);
		if (dstVertex == null) {
			logger.error("Destination node {} does not exit", destination);
			return paths;
		}
		getAllPathsWithMaxStops(source, destination, paths, new LinkedList<String>(), true, maxStops, 0);
		logger.info("getAllPathsWithMaxStops({}, {}, {}) returning paths {}", source, destination, maxStops, paths);
		return paths;
	}

	private void getAllPathsWithMaxStops(String current, String destination, List<List<String>> paths, LinkedList<String> path, boolean isFirst,
			int maxStops, int curStops) {

		if (curStops > maxStops) { // If we've gone over limit, stop
			return;
		}
		path.add(current);

		if (current.equals(destination) && !isFirst) { // If we've arrived at our destination
			logger.debug("Adding path {}", path);
			paths.add(new ArrayList<String>(path));
		}

		final List<String> edges = new ArrayList<String>();
		edges.addAll(stations.get(current).getNeighbours().keySet());

		for (String edge : edges) {
			getAllPathsWithMaxStops(edge, destination, paths, path, false, maxStops, curStops + 1);
		}

		path.removeLastOccurrence(current); // Maintain our path
	}

	/**
	 * Based upon a recursive Breadth-first search. See See <a href="http://en.wikipedia.org/wiki/Breadth-first_search"
	 * >http://en.wikipedia.org/wiki/Breadth-first_search</a>
	 */
	@Override
	public List<List<String>> getAllPathsWithNumStops(String source, String destination, int numStops) {
		List<List<String>> paths = new ArrayList<List<String>>();
		Vertex srcVertex = stations.get(source);
		if (srcVertex == null) {
			logger.error("Source node {} does not exit", source);
			return paths;
		}
		Vertex dstVertex = stations.get(destination);
		if (dstVertex == null) {
			logger.error("Destination node {} does not exit", destination);
			return paths;
		}
		getAllPathsWithNumStops(source, destination, paths, new LinkedList<String>(), true, numStops, 0);
		logger.info("getAllPathsWithNumStops({}, {}, {}) returning paths {}", source, destination, numStops, paths);
		return paths;
	}

	private void getAllPathsWithNumStops(String current, String destination, List<List<String>> paths, LinkedList<String> path, boolean isFirst,
			int numStops, int curStops) {

		if (curStops > numStops) { // If we've gone over limit, stop
			return;
		}
		path.add(current);

		if (current.equals(destination) && curStops == numStops && !isFirst) { // If we've arrived at our destination and correct amount of stops
			logger.debug("Adding path {}", path);
			paths.add(new ArrayList<String>(path));
		}

		final List<String> edges = new ArrayList<String>();
		edges.addAll(stations.get(current).getNeighbours().keySet());

		for (String edge : edges) {
			getAllPathsWithNumStops(edge, destination, paths, path, false, numStops, curStops + 1);
		}

		path.removeLastOccurrence(current); // Maintain our path
	}

	/**
	 * Based upon a recursive Breadth-first search. See See <a href="http://en.wikipedia.org/wiki/Breadth-first_search"
	 * >http://en.wikipedia.org/wiki/Breadth-first_search</a>
	 */
	@Override
	public List<List<String>> getAllPathsLessThanDistance(String source, String destination, int distance) {
		List<List<String>> paths = new ArrayList<List<String>>();
		getAllPathsLessThanDistance(source, destination, paths, new LinkedList<String>(), true, distance, 0);
		logger.info("getAllPathsLessThanDistance({}, {}, {}) returning paths {}", source, destination, distance, paths);
		return paths;
	}

	private void getAllPathsLessThanDistance(String current, String destination, List<List<String>> paths, LinkedList<String> path, boolean isFirst,
			int maxDistance, int curDistance) {

		if (curDistance >= maxDistance) { // If we've hit our limit, stop
			return;
		}
		path.add(current);

		if (current.equals(destination) && !isFirst) { // If we've arrived at our destination
			logger.debug("Adding path {}", path);
			paths.add(new ArrayList<String>(path));
		}

		final List<String> edges = new ArrayList<String>();
		edges.addAll(stations.get(current).getNeighbours().keySet());

		for (String edge : edges) {
			getAllPathsLessThanDistance(edge, destination, paths, path, false, maxDistance, curDistance
					+ stations.get(current).getDistanceToNeighbour(edge));
		}

		path.removeLastOccurrence(current); // Maintain our path
	}

	/**
	 * Uses a modified version of Dijkstra's algorithm
	 * 
	 * See <a href="http://en.wikipedia.org/wiki/Dijkstra%27s_algorithm">http://en .wikipedia.org/wiki/Dijkstra%27s_algorithm</a>
	 */
	@Override
	public List<String> getShortestPath(String source, String destination) {
		List<String> returnPath = new ArrayList<String>();
		Vertex srcVertex = stations.get(source);
		if (srcVertex == null) {
			logger.error("Source node {} does not exit", source);
			return returnPath;
		}
		Vertex dstVertex = stations.get(destination);
		if (dstVertex == null) {
			logger.error("Destination node {} does not exit", destination);
			return returnPath;
		}

		boolean sameSrcAndDest = false;
		if (srcVertex.equals(dstVertex)) { // incase source and destination are same
			sameSrcAndDest = true;
		}
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(srcVertex); // adds our first node

		while (!vertexQueue.isEmpty()) {
			Vertex u = vertexQueue.poll();
			if (u.equals(dstVertex) && !sameSrcAndDest) { // we have arrived and don't need to look past this node
				sameSrcAndDest = false;
				continue;
			}
			// Visit each edge exiting u
			for (Edge e : u.getNeighbours().values()) { // check all neighbours
				Vertex v = e.getTarget();
				int weight = e.getWeight();
				int distanceThroughU = u.getMinDistance() + weight; // is this a faster way to get here?
				if (distanceThroughU < v.getMinDistance()) {
					vertexQueue.remove(v); // remove the old route
					v.setMinDistance(distanceThroughU); // set new distance
					v.setPrevious(u); // set new route
					vertexQueue.add(v); // add to our queue
				}
			}
		}

		// Path reconstruction
		int counter = 0;
		for (Vertex vertex = dstVertex; vertex != null; vertex = vertex.getPrevious()) {
			if (counter == 0 && vertex.getPrevious() == null) {
				break;
			}
			returnPath.add(vertex.getName());
			if (vertex.equals(dstVertex) && counter != 0) { // we have arrived
				break;
			}
			counter++;
		}
		Collections.reverse(returnPath); // flip it around
		clearPaths(); // cleanup for next time...
		logger.info("Returning path {}", returnPath);
		return returnPath;
	}

	/**
	 * Dijkstra's algorithm is typically used to find the shortest route from a source node to all other nodes, since our use case is only concerned
	 * with 1 source and destination it is necessary to clear the states of each station after calculating.
	 */
	private void clearPaths() {
		for (Vertex v : stations.values()) {
			v.setMinDistance(Integer.MAX_VALUE);
			v.setPrevious(null);
		}
	}
}
