# KiwiRail
------------------------------
The purpose of this application is to provide information about differient routes on a railroad network.

-- Design --

I've tried to keep the design of the application simple and the code is written in a way that is easy to understand (I hope).

Essentially KiwiRail holds a Map which maps a stations name to a vertex or node. Each one of these vertices stores a reference to all it's neighbouring vertices as well as the distance required to travel to each neighbour. Again each vertex stores it's neighbours in a map using the name as a key. The link between two vertices is known as an edge. 

More information on the design of the algorithms used to retrieve information may be found in the next section.

-- Implementation & Functionality --
The application is written entirely in Java using Maven for its building and dependencies. Folder structure is as you would expect with any maven project. 

KiwiRail currently provides the following functionality

- getDistanceOfRoute(String... path) :
  Returns the distance along a specified path, or -1 if none exists

- getAllPathsLessThanDistance(String source, String destination, int distance) :
  Gets all the paths between source and destination under distance or an empty list if none exists
  A recursive breadth-first search is used here

- getAllPathsWithMaxStops(String source, String destination, int maxStops) :
  Gets all the paths between source and destination with less or equal maxStops or an empty list if none exists
  A recursive breadth-first search is used here

- getAllPathsWithNumStops(String source, String destination, int numStops) : 
  Gets all the paths between source and destination with exactly numStops or an empty list if none exists
  A recursive breadth-first search is used here

- getShortestPath(String source, String destination) : 
  Gets the shortest path between source and destination, returns this as a list of stops or an empty list if none    exists
  Uses a modified version of Dijkstra's algorithm as we are only concerned with one destination

-- Running --
The application can be run by simply typing 'mvn test' at the base directory. The KiwiRailTest is specific to the problems in the description. log4j is set to info currently, you can reduce this to debug for some more detailed information if you require.
