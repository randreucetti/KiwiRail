package com.randreucetti;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Vertex implements Comparable<Vertex> {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected String name;
	protected Map<String, Edge> neighbours;
	protected Vertex previous;
	protected int minDistance;

	public Vertex(String name) {
		this.name = name;
		this.neighbours = new HashMap<String, Edge>();
		previous = null;
		minDistance = Integer.MAX_VALUE;
	}

	public void addNeighbour(String name, Edge e) {
		logger.debug("Adding neigbour: {}", e);
		neighbours.put(name, e);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
			logger.debug("Getting distance to neighbour {} ", neighbourStn);
			return neighbourStn.getWeight();
		} else {
			logger.warn("{} does not have a neighbour {}", this, neighbour);
			return -1;
		}
	}

	public Vertex getPrevious() {
		return previous;
	}

	public void setPrevious(Vertex previous) {
		this.previous = previous;
	}

	public int getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(int minDistance) {
		this.minDistance = minDistance;
	}
}