package model.ingredient;

import model.CraftObjectType;
import util.datastructure.Bitmap;
import view.ui.Image;

/**
 * Created by esia on 05/07/17.
 */
public class CraftIngredientType extends CraftObjectType {
	public final int maxStack;
	public final Bitmap occupation;

	public CraftIngredientType(String name, String description, Image image,
							   int maxStack, Bitmap occupation) {
		super(name, description, image);
		this.maxStack = maxStack;
		this.occupation = occupation;
	}
}
