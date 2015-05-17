package com.randreucetti;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests developed according to the problem description.
 * 
 * @author ross
 *
 */
public class KiwiRailTest {
	private KiwiRail railway;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Load in our input file and adds the routes
	 */
	@Before
	public void initialize() {
		logger.info("Initializing KiwiRailTest");
		railway = new KiwiRail();

		File file = new File(this.getClass().getClassLoader().getResource("input.txt").getFile());
		List<String> routes = new ArrayList<String>();
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				routes.addAll(Arrays.asList(line.split(",")));
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String route : routes) {
			railway.addRoute(route.substring(0, 1), route.substring(1, 2), Integer.parseInt(route.substring(2, 3)));
		}
	}

	/**
	 * Tests #1 - 5
	 */
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

	/**
	 * Test #6
	 */
	@Test
	public void testGetAllPathsWithMaxStops() {
		logger.info("----------- Starting testGetAllPathsWithMaxStops() ---------");
		List<List<String>> routes = railway.getAllPathsWithMaxStops("C", "C", 3);
		assert (routes.size() == 2);
	}

	/**
	 * Test #7
	 */
	@Test
	public void testGetAllPathsWithNumStops() {
		logger.info("----------- Starting testGetAllPathsWithNumStops() ---------");
		List<List<String>> routes = railway.getAllPathsWithNumStops("A", "C", 4);
		assert (routes.size() == 3);
	}

	/**
	 * Test #8
	 */
	@Test
	public void testGetShortestPath() {
		logger.info("----------- Starting testGetShortestPath() ---------");
		List<String> route = railway.getShortestPath("A", "C");
		int distance = railway.getDistanceOfRoute(route.toArray(new String[route.size()]));
		logger.info("Route {} is of distance {}", route, distance);
		assert (distance == 9);
	}

	/**
	 * Test #9
	 */
	@Test
	public void testGetShortestPathSameNodes() {
		logger.info("----------- Starting testGetShortestPathSameNodes() ---------");
		List<String> route = railway.getShortestPath("B", "B");
		int distance = railway.getDistanceOfRoute(route.toArray(new String[route.size()]));
		logger.info("Route {} is of distance {}", route, distance);
		assert (distance == 9);
	}

	/**
	 * Test #10
	 */
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

}
