package org.adventure.path;

import java.util.ArrayList;
import java.util.Collection;

public class TestMap implements IDistanceToTargetFinder, INeighboorFinder{
	private int[][] mapCost = {
			{1,1,1,1,1,1,2,1,1,1},
			{1,1,1,1,29,1,2,1,1,1},
			{1,1,1,1,29,4,4,4,4,1},
			{1,1,1,1,29,1,2,1,1,1},
			{1,29,29,29,29,2,2,2,1,1},
			{2,2,2,2,4,5,4,1,4,4},
			{3,3,3,4,4,5,5,3,4,1},
			{3,3,2,3,4,4,5,3,4,1},
			{1,2,2,2,1,4,4,1,1,1},
			{1,1,1,1,1,1,1,1,1,1}
		};
	private MapNode[][] mapNodes = new MapNode[10][10];
	
	public MapNode getMapNode(int x, int y) {
		if (mapNodes[x][y] == null) {
			mapNodes[x][y] = new MapNode(mapCost[x][y], x, y);
		}
		return mapNodes[x][y];
	}
	
	@Override
	public Collection<IMapNode> getNeighbors(IMapNode mapNode) {
		int x = ((MapNode)mapNode).getX();
		int y = ((MapNode)mapNode).getY();
		Collection<IMapNode> neighbors = new ArrayList<IMapNode>();
		if (x > 0 && y>0) {
			neighbors.add(getMapNode(x-1, y-1));			
		}
		if (x > 0) {
			neighbors.add(getMapNode(x-1, y));			
		}
		if (x > 0 && y <9) {
			neighbors.add(getMapNode(x-1, y+1));			
		}
		if (y> 0) {
			neighbors.add(getMapNode(x, y-1));			
		}
		if (y < 9) {
			neighbors.add(getMapNode(x, y+1));			
		}
		if (x < 9 && y > 0) {
			neighbors.add(getMapNode(x+1, y-1));			
		}
		if (x < 9) {
			neighbors.add(getMapNode(x+1, y));			
		}
		if (x < 9 && y < 9) {
			neighbors.add(getMapNode(x+1, y+1));			
		}
		return neighbors;
	}

	@Override
	public int calculateDistanceBetween(IMapNode mapNode, IMapNode target) {
		int x = ((MapNode)mapNode).getX();
		int y = ((MapNode)mapNode).getY();
		int targetX = ((MapNode)target).getX();
		int targetY = ((MapNode)target).getY();
		int xDiff= Math.abs(x-targetX);
		int yDiff= Math.abs(y-targetY);
		
		return Math.min(xDiff, yDiff);
	} 
	
	
}
