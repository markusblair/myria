package org.adventure.path;

public class TestFrontier {

	public static void main(String[] args) {
		TestMap testMap = new TestMap();
		IMapNode start = testMap.getMapNode(7, 3);
		IMapNode end = testMap.getMapNode(3, 3);
		
		PathFinder pathFinder = new PathFinder();
		pathFinder.setDistanceToTargetFinder(testMap);
		pathFinder.setNeighboorFinder(testMap);
		pathFinder.setStartAndEnd(start, end);
		
		System.out.println(pathFinder);
		while (pathFinder.isFrontierDone() == false) {
			pathFinder.expandFrontierToNextContourLine();
			System.out.println(pathFinder);			
		}
		pathFinder.printPath();
	}

}
