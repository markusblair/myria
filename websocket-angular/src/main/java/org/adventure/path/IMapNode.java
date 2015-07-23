package org.adventure.path;


public interface IMapNode extends Comparable<IMapNode>{
	public int getCostToArriveFrom(IMapNode mapNode);
	public int getMinimumArrivalCost();
	public void setArrivalCost(IMapNode arriveFromMap);
	public int getEstimatedTravelCost();
	public void setDistanceToTarget(int distance);
	public IMapNode getShortestPathParent();
}
