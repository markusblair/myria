package org.adventure.items;

import org.adventure.random.RandomCollection;

public class WearableFactory implements IItemFactory {
	private static RandomCollection<String> colors = new RandomCollection<String>().add(20, null).add(1,"blue").add(1, "black").add(1, "white").add(1, "green").add(1, "brown")
			.add(1, "yellow").add(1, "orange").add(5, null);
	private static RandomCollection<String> materials = new RandomCollection<String>().add(1,"cotton").add(1, "leather").add(1, "wool").add(1, "satin")
			.add(1, "silk").add(1, "burlap");
	private static RandomCollection<String> adjectives1 =  new RandomCollection<String>().add(50,null).add(10, "worn").add(5, "torn").add(5, "ragged").add(2,"fine").add(1,"exquisite");
	
	public Wearable getBoots() {
		Wearable boots = new Wearable(WearableType.SHOES);
		
		boots.setName("boots");
		boots.setVolume(2);
		boots.setWeight(2);
		boots.setColor(colors.next());
		boots.setMaterial(materials.next());
		boots.setAdjective1(adjectives1.next());
		
		RandomCollection<String> adjectives2 =  new RandomCollection<String>().add(20, null).add(1, "low").add(1, "knee high").add(1, "tall")
				.add(1, "thick souled").add(1, "thick");
		boots.setAdjective2(adjectives2.next());
		
		return boots;
	}

	@Override
	public IItem createItem() {
		return getBoots();
	}
//	
//	WearableContainer jacket = new WearableContainer(WearableType.JACKET, 4);
//	jacket.setName("jacket");
//	jacket.setDescription("A dark blue wool jacket.");
//	jacket.setLongDescription("The jacket is made of dark blue wool.  It is lined with black satin and has a small inner pocket.  There are four buttons down the front.");
//	
//	WearableContainer backpack = new WearableContainer(WearableType.BACK_PACK, 10);
//	backpack.setName("backpack");
//	backpack.setDescription("An orange cotton backpack");
//	backpack.setLongDescription("The backpack is made of orange cotton");
//	
//	WearableContainer satchel = new WearableContainer(WearableType.SHOULDER, 5);
//	satchel.setName("satchel");
//	satchel.setDescription("A brown leather satchel");
//	satchel.setLongDescription("the satchel is made of brown leather satchel");
//	
//	WearableContainer belt = new WearableContainer(WearableType.BELT, 2);
//	belt.setName("belt");
//	belt.setDescription("a gray belt");
//	belt.setLongDescription("a thin gray belt made out of braided hourse hair with a buckle with a shap of a bird");
//
//	WearableContainer pants = new WearableContainer(WearableType.PANTS, 3);
//	pants.setName("pants");
//	pants.setDescription("white wool pants");
//	pants.setLongDescription("long white wool pants with");
//	
//	WearableContainer gloves = new WearableContainer(WearableType.GLOVES, 0);
//	gloves.setName("gloves");
//	gloves.setDescription("thin leather gloves");
//	gloves.setDescription("The gloves are made of thin soft leather.");
//	
//	WearableContainer hat = new WearableContainer(WearableType.HAT, 0);
//	hat.setName("hat");
//	hat.setDescription("pink wool hat");
//	hat.setLongDescription("a little top hat made of fine wool");
//
//	WearableContainer shirt = new WearableContainer(WearableType.SHIRT, 4);
//	shirt.setName("shirt");
//	shirt.setDescription("a white and black leather shirt");
//	shirt.setLongDescription("a leather shirt with white on the front and black on the back");
//	
//	Wearable shoes = new Wearable(WearableType.SHOES);
//	shoes.setName("shoes");
//	shoes.setDescription("fine brown fur shoes");
//	shoes.setLongDescription("a pair of fine fur shoes that are brown");
//	
//	Wearable boots = new Wearable(WearableType.SHOES);
//	boots.setName("boots");
//	boots.setDescription("black cotton boots");
//	boots.setLongDescription("a pair of knee high black boots made of cotton");
//	
}
