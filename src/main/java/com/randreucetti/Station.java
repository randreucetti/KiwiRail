package com.randreucetti;

/**
 * This class currently doesn't serve any real purpose but may be useful when
 * adding other train station related functionality.
 * 
 * @author ross
 *
 */
public class Station extends Vertex {

	public Station(String name) {
		super(name);
	}

	@Override
	public int compareTo(Vertex o) {
		return Integer.compare(minDistance, o.getMinDistance());
	}
}
