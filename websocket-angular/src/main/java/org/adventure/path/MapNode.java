package org.adventure.path;

public class MapNode implements IMapNode {
	private int minArrivalCost = 0;
	private int travelCost;
	private int distanceToTarget;;
	private int x;
	private int y;
	private IMapNode shortestPathParent;
	public MapNode(int travelCost, int x, int y) {
		super();
		this.travelCost = travelCost;
		this.x = x;
		this.y = y;
	}

	protected int getX() {
		return x;
	}

	protected int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "[" + x + "," + y + "," + getEstimatedTravelCost() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapNode other = (MapNode) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}



	@Override
	public int compareTo(IMapNode mapNode) {
		return this.getEstimatedTravelCost() - mapNode.getEstimatedTravelCost();
	}

	@Override
	public int getCostToArriveFrom(IMapNode mapNode) {
		return this.travelCost;
	}

	@Override
	public int getMinimumArrivalCost() {
		return minArrivalCost;
	}

	@Override
	public void setArrivalCost(IMapNode arriveFromMap) {
		int arrivalCost = this.getCostToArriveFrom(arriveFromMap) + arriveFromMap.getMinimumArrivalCost();
		if (arrivalCost < minArrivalCost || minArrivalCost == 0) {
			minArrivalCost = arrivalCost;
			shortestPathParent = arriveFromMap;
		}
	}

	@Override
	public IMapNode getShortestPathParent() {
		return shortestPathParent;
	}

	@Override
	public int getEstimatedTravelCost() {
		return getMinimumArrivalCost() + this.distanceToTarget;
	}

	@Override
	public void setDistanceToTarget(int distance) {
		distanceToTarget = distance;
	}

}
