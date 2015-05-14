package com.randreucetti;

/**
 * Edge class used to storing neighbour {@link Vertex} and the distance required
 * to travel to it.
 * 
 * @author ross
 *
 */
public class Edge {
	private final Vertex target;
	private final int weight;

	/**
	 * 
	 * @param {@link Vertex} target
	 * @param {@link int} weight
	 */
	public Edge(Vertex target, int weight) {
		this.target = target;
		this.weight = weight;
	}

	public Vertex getTarget() {
		return target;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return "Edge [target=" + target + ", weight=" + weight + "]";
	}
}
