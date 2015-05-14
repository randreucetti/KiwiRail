package com.randreucetti;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KiwiRailTest {
	private KiwiRail railway;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void initialize() {
		logger.info("Initializing KiwiRailTest");
		railway = new KiwiRail();

		railway.addRoute("A", "B", 5);
		railway.addRoute("B", "C", 4);
		railway.addRoute("C", "D", 8);
		railway.addRoute("D", "C", 8);
		railway.addRoute("D", "E", 6);
		railway.addRoute("A", "D", 5);
		railway.addRoute("C", "E", 2);
		railway.addRoute("E", "B", 3);
		railway.addRoute("A", "E", 7);
	}

	@Test
	public void testGetDistanceOfRoute() {
		logger.info("----------- Starting testGetDistanceOfRoute() ---------");
		int distance1 = railway.getDistanceOfRoute(new String[] { "A", "B", "C" });
		assert (distance1 == 9);

		int distance2 = railway.getDistanceOfRoute(new String[] { "A", "D" });
		assert (distance2 == 5);

		int distance3 = railway.getDistanceOfRoute(new String[] { "A", "D", "C" });
		assert (distance3 == 13);

		int distance4 = railway.getDistanceOfRoute(new String[] { "A", "E", "B", "C", "D" });
		assert (distance4 == 22);

		int distance5 = railway.getDistanceOfRoute(new String[] { "A", "E", "D" });
		assert (distance5 == -1);
	}

	@Test
	public void testGetAllPathsWithMaxStops() {
		logger.info("----------- Starting testGetAllPathsWithMaxStops() ---------");
		List<List<String>> routes = railway.getAllPathsWithMaxStops("C", "C", 3);
		assert (routes.size() == 2);
	}

	@Test
	public void testGetAllPathsWithNumStops() {
		logger.info("----------- Starting testGetAllPathsWithNumStops() ---------");
		List<List<String>> routes = railway.getAllPathsWithNumStops("A", "C", 4);
		assert (routes.size() == 3);
	}

	@Test
	public void testGetAllPathsLessThanDistance() {
		logger.info("----------- Starting testGetAllPathsLessThanDistance() ---------");
		List<List<String>> routes = railway.getAllPathsLessThanDistance("C", "C", 30);
		for (List<String> route : routes) {
			int distance = railway.getDistanceOfRoute(route.toArray(new String[route.size()]));
			logger.info("Route {} is of distance {}", route, distance);
			assert (distance < 30);
		}
		assert (routes.size() == 7);
	}

	@Test
	public void testGetShortestPath() {
		logger.info("----------- Starting testGetShortestPath() ---------");
		List<String> route = railway.getShortestPath("A", "C");
		int distance = railway.getDistanceOfRoute(route.toArray(new String[route.size()]));
		logger.info("Route {} is of distance {}", route, distance);
		assert (distance == 9);
	}

	@Test
	public void testGetShortestPathSameNodes() {
		logger.info("----------- Starting testGetShortestPathSameNodes() ---------");
		List<String> route = railway.getShortestPath("B", "B");
		int distance = railway.getDistanceOfRoute(route.toArray(new String[route.size()]));
		logger.info("Route {} is of distance {}", route, distance);
		assert (distance == 9);
	}
}
