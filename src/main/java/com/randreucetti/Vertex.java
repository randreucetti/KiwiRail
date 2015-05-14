package com.randreucetti;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
	private final String name;
	private Map<String, Edge> neighbours;
	private Vertex previous;

	public Vertex(String name) {
		this.name = name;
		this.neighbours = new HashMap<String, Edge>();
	}

	public void addNeighbour(String name, Edge e) {
		neighbours.put(name, e);
	}

	public Map<String, Edge> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(Map<String, Edge> neighbours) {
		this.neighbours = neighbours;
	}

	public int getDistanceToNeighbour(String neighbour) {
		Edge neighbourStn = neighbours.get(neighbour);
		if (neighbourStn != null) {
			return neighbourStn.getWeight();
		} else {
			System.err.print(name + " does not have a neighbour " + neighbour);
			return -1;
		}
	}

	public Vertex getPrevious() {
		return previous;
	}

	public void setPrevious(Vertex previous) {
		this.previous = previous;
	}

	public String getName() {
		return name;
	}
}