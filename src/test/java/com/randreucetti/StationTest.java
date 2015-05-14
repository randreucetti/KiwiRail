package com.randreucetti;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class StationTest {
	private Vertex station;

	@Before
	public void initialize() {
		station = new Station("testStation");
	}

	@Test
	public void testAddNeighbour() {
		Edge edge = EasyMock.createMock(Edge.class);
		station.addNeighbour("n1", edge);
		assert (station.getNeighbours().size() == 1);
	}

	@Test
	public void testGetDistanceToNeighbour() {
		Edge edge = EasyMock.createMock(Edge.class);
		EasyMock.expect(edge.getWeight()).andReturn(5);
		EasyMock.replay(edge);
		station.addNeighbour("n1", edge);
		assert (station.getDistanceToNeighbour("n1") == 5);
	}
}
