package com.randreucetti;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for the Vertex type, a vertex represents a node on a
 * graph and holds links to neigbouring vertices and their distances in the form
 * of {@link Edge}, The datastructure used for holding the neighbours is a
 * {@link HashMap}
 * 
 * To support efficient calculation of shortest paths between vertices
 * additional fields {@link Vertex} previous and {@link int} minDistance are
 * present
 * 
 * @author ross
 *
 */
public abstract class Vertex implements Comparable<Vertex> {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected String name;
	protected Map<String, Edge> neighbours;
	protected Vertex previous;
	protected int minDistance;

	/**
	 * 
	 * @param {@link String} name The name of this vertex
	 */
	public Vertex(String name) {
		this.name = name;
		this.neighbours = new HashMap<String, Edge>();
		previous = null;
		minDistance = Integer.MAX_VALUE;
	}

	/**
	 * Adds a neighbour Vertex and the distance assocated with travelling to it
	 * 
	 * @param {@link String} name The name of the neighbouring vertex
	 * @param {@link Edge} The neighbouring vertex and distance
	 */
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

	/**
	 * Gets the distance to a neighbouring vertex
	 * 
	 * @param {@link String} neighbour
	 * @return distance to a vertex or -1 if neighbour does not exist
	 */
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

	@Override
	public String toString() {
		return "Vertex [name=" + name + "]";
	}
}