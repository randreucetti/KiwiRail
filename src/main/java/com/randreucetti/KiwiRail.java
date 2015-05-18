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
	 * Based upon a recursive Breadth-first search. See See <a
	 * href="http://en.wikipedia.org/wiki/Breadth-first_search"
	 * >http://en.wikipedia.org/wiki/Breadth-first_search</a>
	 */
	@Override
	public List<List<String>> getAllPathsLessThanDistance(String source, String destination, int distance) {
		List<List<String>> paths = new ArrayList<List<String>>();
		getAllPathsLessThanDistance(source, destination, destination, paths, new LinkedList<String>(), true, distance,
				0);
		logger.info("getAllPathsLessThanDistance({}, {}, {}) returning paths {}", source, destination, distance, paths);
		return paths;
	}

	private void getAllPathsLessThanDistance(String current, String destination, String ignore,
			List<List<String>> paths, LinkedList<String> path, boolean isFirst, int maxDistance, int curDistance) {

		if (curDistance >= maxDistance) {
			return;
		}
		path.add(current);

		if (current.equals(destination) && !isFirst) {
			logger.debug("Adding path {}", path);
			paths.add(new ArrayList<String>(path));
			if (!current.equals(ignore)) {
				path.removeLastOccurrence(current);
			}
		}

		final List<String> edges = new ArrayList<String>();
		edges.addAll(stations.get(current).getNeighbours().keySet());

		for (String edge : edges) {
			getAllPathsLessThanDistance(edge, destination, ignore, paths, path, false, maxDistance, curDistance
					+ stations.get(current).getDistanceToNeighbour(edge));
		}

		path.removeLastOccurrence(current);
	}

	/**
	 * Based upon a recursive Breadth-first search. See See <a
	 * href="http://en.wikipedia.org/wiki/Breadth-first_search"
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
		getAllPathsLessThanStops(source, destination, destination, paths, new LinkedList<String>(), true, maxStops, 0);
		logger.info("getAllPathsWithMaxStops({}, {}, {}) returning paths {}", source, destination, maxStops, paths);
		return paths;
	}

	private void getAllPathsLessThanStops(String current, String destination, String ignore, List<List<String>> paths,
			LinkedList<String> path, boolean isFirst, int maxStops, int curStops) {

		if (curStops > maxStops) {
			return;
		}
		path.add(current);

		if (current.equals(destination) && !isFirst) {
			logger.debug("Adding path {}", path);
			paths.add(new ArrayList<String>(path));
			if (!current.equals(ignore)) {
				path.removeLastOccurrence(current);
			}
		}

		final List<String> edges = new ArrayList<String>();
		edges.addAll(stations.get(current).getNeighbours().keySet());

		for (String edge : edges) {
			getAllPathsLessThanStops(edge, destination, ignore, paths, path, false, maxStops, curStops + 1);
		}

		path.removeLastOccurrence(current);
	}

	/**
	 * Based upon a recursive Breadth-first search. See See <a
	 * href="http://en.wikipedia.org/wiki/Breadth-first_search"
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
		getAllPathsWithNumStops(source, destination, destination, paths, new LinkedList<String>(), true, numStops, 0);
		logger.info("getAllPathsWithNumStops({}, {}, {}) returning paths {}", source, destination, numStops, paths);
		return paths;
	}

	private void getAllPathsWithNumStops(String current, String destination, String ignore, List<List<String>> paths,
			LinkedList<String> path, boolean isFirst, int numStops, int curStops) {

		if (curStops > numStops) {
			return;
		}
		path.add(current);

		if (current.equals(destination) && curStops == numStops && !isFirst) {
			logger.debug("Adding path {}", path);
			paths.add(new ArrayList<String>(path));
			if (!current.equals(ignore)) {
				path.removeLastOccurrence(current);
			}
		}

		final List<String> edges = new ArrayList<String>();
		edges.addAll(stations.get(current).getNeighbours().keySet());

		for (String edge : edges) {
			getAllPathsWithNumStops(edge, destination, ignore, paths, path, false, numStops, curStops + 1);
		}

		path.removeLastOccurrence(current);
	}

	/**
	 * Uses a modified version of Dijkstra's algorithm
	 * 
	 * See <a
	 * href="http://en.wikipedia.org/wiki/Dijkstra%27s_algorithm">http://en
	 * .wikipedia.org/wiki/Dijkstra%27s_algorithm</a>
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
		srcVertex.setMinDistance(Integer.MAX_VALUE);
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(srcVertex);

		while (!vertexQueue.isEmpty()) {
			Vertex u = vertexQueue.poll();
			if (u.equals(destination)) {
				continue;
			}
			// Visit each edge exiting u
			for (Edge e : u.getNeighbours().values()) {
				Vertex v = e.getTarget();
				int weight = e.getWeight();
				int distanceThroughU = u.getMinDistance() + weight;
				if (distanceThroughU < v.getMinDistance()) {
					vertexQueue.remove(v);
					v.setMinDistance(distanceThroughU);
					v.setPrevious(u);
					vertexQueue.add(v);
				}
			}
		}

		int counter = 0;
		for (Vertex vertex = dstVertex; vertex != null; vertex = vertex.getPrevious()) {
			if (counter == 0 && vertex.getPrevious() == null) {
				break;
			}
			returnPath.add(vertex.getName());
			if (vertex.equals(dstVertex) && counter != 0) {
				break;
			}
			counter++;
		}
		Collections.reverse(returnPath);
		clearPaths();
		logger.info("Returning path {}", returnPath);
		return returnPath;
	}

	private void clearPaths() {
		for (Vertex v : stations.values()) {
			v.setMinDistance(Integer.MAX_VALUE);
			v.setPrevious(null);
		}
	}
}
