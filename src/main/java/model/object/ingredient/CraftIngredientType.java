package model.object.ingredient;

import model.object.CraftObjectType;
import util.datastructure.Bitmap;
import util.datastructure.Image;

/**
 * Created by esia on 05/07/17.
 */
public class CraftIngredientType extends CraftObjectType {
	public final int maxStack;
	public final Bitmap occupation;
	public final Image image;

	public CraftIngredientType(String name, String description, Image image, int maxStack, Bitmap occupation) {
		super(name, description);
		this.image = image;
		this.maxStack = maxStack;
		this.occupation = occupation;
	}
}
