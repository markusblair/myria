package org.adventure;

import org.adventure.character.ICharacter;
import org.adventure.items.search.ItemSearchResult;

public interface ISalePrice {

	public abstract void buyItem(ICharacter character, ItemSearchResult itemSearchResult);

}