package com.randreucetti;

public class Edge {
	private final Vertex target;
	private final Integer weight;

	public Edge(Vertex target, Integer weight) {
		this.target = target;
		this.weight = weight;
	}

	public Vertex getTarget() {
		return target;
	}

	public Integer getWeight() {
		return weight;
	}
}
