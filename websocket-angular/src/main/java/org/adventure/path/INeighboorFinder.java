package org.adventure.path;

import java.util.Collection;

public interface INeighboorFinder {
	public Collection<IMapNode> getNeighbors(IMapNode mapNode);
}
