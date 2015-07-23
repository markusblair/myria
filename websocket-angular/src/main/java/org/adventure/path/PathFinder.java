package org.adventure.path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class PathFinder {
	IDistanceToTargetFinder distanceToTargetFinder;
	INeighboorFinder neighboorFinder;
	
	private int minEdgeTravelCost;
	private boolean frontierDone;
	private IMapNode start;
	private IMapNode end;
	protected Collection<IMapNode> frontier = new HashSet<IMapNode>();  //node that have had all the neighbors searched and are part of the frontier
	protected Collection<IMapNode> edge = new HashSet<IMapNode>();  //node that has been visited but not all of its neighboors have been visited.
	
	public boolean isFrontierDone() {
		return frontierDone;
	}
	
	protected void setDistanceToTargetFinder(IDistanceToTargetFinder distanceToTargetFinder) {
		this.distanceToTargetFinder = distanceToTargetFinder;
	}

	protected void setNeighboorFinder(INeighboorFinder neighboorFinder) {
		this.neighboorFinder = neighboorFinder;
	}

	public void setStartAndEnd(IMapNode start, IMapNode end) {
		this.start = start;
		this.end = end;
		this.edge.add(start);
		int distanceBetweenStartAndEnd = distanceToTargetFinder.calculateDistanceBetween(start, end);
		start.setDistanceToTarget(distanceBetweenStartAndEnd);
	}
	
	public void expandFrontierToNextContourLine() {
		List<IMapNode> copyOfEdge = new ArrayList<IMapNode>(edge);
		Collections.sort(copyOfEdge);
		minEdgeTravelCost = copyOfEdge.get(0).getEstimatedTravelCost();
		System.out.println("Min Edge Travel Cost =" + minEdgeTravelCost);
		for (IMapNode edgeNode : copyOfEdge) {
			if (edgeNode.getEstimatedTravelCost() == minEdgeTravelCost) {
				// This edge is the same estimated cost as the minimum so it should be added to the frontier as well.
				addToFrontier(edgeNode);
			}
		}
	}
	
	public void addToFrontier(IMapNode edgeNode) {
		this.frontier.add(edgeNode);
		this.edge.remove(edgeNode);
		if (edgeNode.equals(this.end)) {
			this.frontierDone = true;
		}
		else {
			Collection<IMapNode> neighboors =  neighboorFinder.getNeighbors(edgeNode);
			for (IMapNode edgeNeighboor : neighboors) {
				if (frontier.contains(edgeNeighboor) == false) {
					int distanceToEnd = distanceToTargetFinder.calculateDistanceBetween(edgeNeighboor, this.end);
					edgeNeighboor.setDistanceToTarget(distanceToEnd);
					edgeNeighboor.setArrivalCost(edgeNode);
					if (edgeNeighboor.getEstimatedTravelCost() == minEdgeTravelCost) {
						// This edge is the same estimated cost as the minimum so it should be added to the frontier as well.
						addToFrontier(edgeNeighboor);
					}
					else {
						this.edge.add(edgeNeighboor);					
					}
				}
			}	
		}
	}
	
	public void printPath() {
		IMapNode currentNode = this.end;
		while (currentNode.getShortestPathParent() != null) {
			System.out.println(currentNode);
			currentNode= currentNode.getShortestPathParent();
		}
	}

	@Override
	public String toString() {
		List<IMapNode> copyFrontier = new ArrayList<IMapNode>(frontier);
		Collections.sort(copyFrontier);
		List<IMapNode> copyOfEdge = new ArrayList<IMapNode>(edge);
		Collections.sort(copyOfEdge);
		int[][] frontierResult = new int[10][10];
		for (IMapNode frontierNode : frontier) {
			int x = ((MapNode)frontierNode).getX();
			int y = ((MapNode)frontierNode).getY();
			frontierResult[x][y] = ((MapNode)frontierNode).getEstimatedTravelCost();
		}
		for (int[] is : frontierResult) {
			for (int i : is) {
				if (i > 0) System.out.print(i);
				System.out.print("\t");
			}
			System.out.println();
		}
		return "PathFinder [\nfrontier=" + copyFrontier.size()+"\t" + copyFrontier + "\n edge=" + copyOfEdge.size()+"\t\t" + copyOfEdge + "]";
	}
	
	
}
