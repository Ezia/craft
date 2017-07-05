package model.object.recipe;

import model.object.CraftObjectType;
import model.object.ingredient.CraftIngredient;
import util.datastructure.ComponentTable;

/**
 * Created by esia on 05/07/17.
 */
public class CraftRecipeType extends CraftObjectType {
	private ComponentTable<CraftIngredient> ingredients;
	private CraftIngredient result;

	public CraftRecipeType(String name, String description,
						   ComponentTable<CraftIngredient> ingredients,
						   CraftIngredient result) {
		super(name, description);
		this.ingredients = ingredients;
		this.result = result;
	}
}
