package com.randreucetti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KiwiRail {
	private Map<String, Vertex> stations;

	public KiwiRail() {
		this.stations = new HashMap<String, Vertex>();
	}

	public void addStation(String key, Vertex station) {
		stations.put(key, station);
	}

	public void addRoute(String source, String destination, int distance) {
		if (!stations.containsKey(source)) {
			stations.put(source, new Vertex(source));
		}
		if (!stations.containsKey(destination)) {
			stations.put(destination, new Vertex(destination));
		}
		stations.get(source).addNeighbour(destination, new Edge(stations.get(destination), distance));
	}

	public int getDistanceOfRoute(String... path) {
		int totalDistance = 0;
		for (int i = 0; i < path.length - 1; i++) {
			Vertex st1 = stations.get(path[i]);
			if (st1 != null) {
				int distToNeigbhour = st1.getDistanceToNeighbour(path[i + 1]);
				if (distToNeigbhour != -1) {
					totalDistance += distToNeigbhour;
				} else {
					return -1;
				}
			} else {
				System.err.print(path[i] + " does not exist.");
				return -1;
			}
		}
		return totalDistance;
	}

	public List<List<String>> getAllPathsLessThanDistance(String source, String destination, int distance) {
		List<List<String>> paths = new ArrayList<List<String>>();
		getAllPathsLessThanDistance(source, destination, destination, paths, new LinkedList<String>(), true, distance,
				0);
		return paths;
	}

	private void getAllPathsLessThanDistance(String current, String destination, String ignore,
			List<List<String>> paths, LinkedList<String> path, boolean isFirst, int maxDistance, int curDistance) {

		if (curDistance >= maxDistance) {
			return;
		}
		path.add(current);

		if (current == destination && !isFirst) {
			paths.add(new ArrayList<String>(path));
			if (current != ignore)
				path.removeLastOccurrence(current);
		}

		final List<String> edges = new ArrayList<String>();
		edges.addAll(stations.get(current).getNeighbours().keySet());

		for (String edge : edges) {
			getAllPathsLessThanDistance(edge, destination, ignore, paths, path, false, maxDistance, curDistance
					+ stations.get(current).getDistanceToNeighbour(edge));
		}

		path.removeLastOccurrence(current);
	}

	public List<List<String>> getAllPathsWithMaxStops(String source, String destination, int maxStops) {
		List<List<String>> paths = new ArrayList<List<String>>();
		getAllPathsLessThanStops(source, destination, destination, paths, new LinkedList<String>(), true, maxStops, 0);
		return paths;
	}

	private void getAllPathsLessThanStops(String current, String destination, String ignore, List<List<String>> paths,
			LinkedList<String> path, boolean isFirst, int maxStops, int curStops) {

		if (curStops > maxStops) {
			return;
		}
		path.add(current);

		if (current == destination && !isFirst) {
			paths.add(new ArrayList<String>(path));
			if (current != ignore)
				path.removeLastOccurrence(current);
		}

		final List<String> edges = new ArrayList<String>();
		edges.addAll(stations.get(current).getNeighbours().keySet());

		for (String edge : edges) {
			getAllPathsLessThanStops(edge, destination, ignore, paths, path, false, maxStops, curStops + 1);
		}

		path.removeLastOccurrence(current);
	}

	public List<List<String>> getAllPathsWithNumStops(String source, String destination, int numStops) {
		List<List<String>> paths = new ArrayList<List<String>>();
		getAllPathsWithNumStops(source, destination, destination, paths, new LinkedList<String>(), true, numStops, 0);
		return paths;
	}

	private void getAllPathsWithNumStops(String current, String destination, String ignore, List<List<String>> paths,
			LinkedList<String> path, boolean isFirst, int numStops, int curStops) {

		if (curStops > numStops) {
			return;
		}
		path.add(current);

		if (current == destination && curStops == numStops && !isFirst) {
			paths.add(new ArrayList<String>(path));
			if (current != ignore)
				path.removeLastOccurrence(current);
		}

		final List<String> edges = new ArrayList<String>();
		edges.addAll(stations.get(current).getNeighbours().keySet());

		for (String edge : edges) {
			getAllPathsWithNumStops(edge, destination, ignore, paths, path, false, numStops, curStops + 1);
		}

		path.removeLastOccurrence(current);
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

		// System.out.println(railway.getDistanceOfRoute(new String[] { "A",
		// "B", "C" }));
		// System.out.println(railway.getDistanceOfRoute(new String[] { "A", "D"
		// }));
		// System.out.println(railway.getDistanceOfRoute(new String[] { "A",
		// "D", "C" }));
		// System.out.println(railway.getDistanceOfRoute(new String[] { "A",
		// "E", "B", "C", "D" }));
		// System.out.println(railway.getDistanceOfRoute(new String[] { "A",
		// "E", "D" }));

		System.out.println(railway.getAllPathsLessThanDistance("C", "C", 30));
		System.out.println(railway.getAllPathsWithMaxStops("C", "C", 3));
		System.out.println(railway.getAllPathsWithNumStops("A", "C", 4));
	}
}
