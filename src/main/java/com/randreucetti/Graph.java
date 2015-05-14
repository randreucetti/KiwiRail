package com.randreucetti;

import java.util.List;

public interface Graph {
	/**
	 * Adds a route from source to destination of a specified distance, If
	 * source or destination vertex does not already exist, it will be created
	 * 
	 * @param source
	 *            The routes starting point
	 * @param destination
	 *            The routes ending point
	 * @param distance
	 *            The distance between points
	 */
	public void addRoute(String source, String destination, int distance);

	/**
	 * 
	 * @param path
	 *            The vertexes in the order they should be traversed
	 * @return The total distance of the route, or -1 if no route exists
	 */

	public int getDistanceOfRoute(String... path);

	/**
	 * 
	 * @param source
	 *            The routes starting point
	 * @param destination
	 *            The routes ending point
	 * @param distance
	 *            All paths must be LESS than this distance
	 * @return The List of paths which meet the criteria, or an empty list if
	 *         none exists
	 */

	public List<List<String>> getAllPathsLessThanDistance(String source, String destination, int distance);

	/**
	 * 
	 * @param source
	 *            The routes starting point
	 * @param destination
	 *            The routes ending point
	 * @param maxStops
	 *            The maximum number of stop allowed for any paths between the
	 *            vertexes
	 * @return The List of paths which meet the criteria, or an empty list if
	 *         none exists
	 */

	public List<List<String>> getAllPathsWithMaxStops(String source, String destination, int maxStops);

	/**
	 * 
	 * @param source
	 *            The routes starting point
	 * @param destination
	 * 
	 * @param numStops
	 *            The exact amount of stops paths must be
	 * @return The List of paths which meet the criteria, or an empty list if
	 *         none exists
	 */

	public List<List<String>> getAllPathsWithNumStops(String source, String destination, int numStops);

	/**
	 * 
	 * @param source
	 *            The routes starting point
	 * @param destination
	 *            The routes ending point
	 * @return The shortest path between points, or an empty list if none exists
	 */

	public List<String> getShortestPath(String source, String destination);

}
