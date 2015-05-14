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

public class KiwiRail implements Graph {
	private Map<String, Vertex> stations;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public KiwiRail() {
		this.stations = new HashMap<String, Vertex>();
	}

	@Override
	public void addNode(String key, Vertex station) {
		logger.debug("Adding station: {}", station);
		stations.put(key, station);
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

	@Override
	public int getDistanceOfRoute(String... path) {
		int totalDistance = 0;
		for (int i = 0; i < path.length - 1; i++) {
			Vertex st1 = stations.get(path[i]);
			if (st1 != null) {
				int distToNeigbhour = st1.getDistanceToNeighbour(path[i + 1]);
				if (distToNeigbhour != -1) {
					totalDistance += distToNeigbhour;
				} else {
					logger.warn("No path {} could be found, returning {}", path, -1);
					return -1;
				}
			} else {
				logger.warn("Station \"{}\" does not exist.", path[i]);
				return -1;
			}
		}
		logger.info("Distance for path {} is {}", path, totalDistance);
		return totalDistance;
	}

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

	@Override
	public List<List<String>> getAllPathsWithMaxStops(String source, String destination, int maxStops) {
		List<List<String>> paths = new ArrayList<List<String>>();
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

	@Override
	public List<List<String>> getAllPathsWithNumStops(String source, String destination, int numStops) {
		List<List<String>> paths = new ArrayList<List<String>>();
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

	@Override
	public List<String> getShortestPath(String source, String destination) {
		clearPaths();
		Vertex srcVertex = stations.get(source);
		srcVertex.setMinDistance(0);
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(srcVertex);

		while (!vertexQueue.isEmpty()) {
			Vertex u = vertexQueue.poll();

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
		List<Vertex> path = getShortestPathTo(stations.get(destination));
		List<String> returnPath = new ArrayList<String>();
		for (Vertex v : path) {
			returnPath.add(v.getName());
		}
		return returnPath;
	}

	private static List<Vertex> getShortestPathTo(Vertex target) {
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = target; vertex != null; vertex = vertex.getPrevious())
			path.add(vertex);
		Collections.reverse(path);
		return path;
	}

	private void clearPaths() {
		for (Vertex v : stations.values()) {
			v.setMinDistance(Integer.MAX_VALUE);
			v.setPrevious(null);
		}
	}

	public static void main(String[] args) {
		KiwiRail railway = new KiwiRail();
		railway.addRoute("A", "B", 5);
		railway.addRoute("B", "C", 4);
		railway.addRoute("C", "D", 8);
		railway.addRoute("D", "C", 8);
		railway.addRoute("D", "E", 6);
		railway.addRoute("A", "D", 5);
		railway.addRoute("C", "E", 2);
		railway.addRoute("E", "B", 3);
		railway.addRoute("A", "E", 7);

		System.out.println(railway.getShortestPath("A", "C"));
		System.out.println(railway.getShortestPath("B", "B"));

	}
}
