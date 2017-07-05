package model.object.recipe;

import model.object.CraftObjectType;
import model.object.ingredient.CraftIngredientInstance;
import util.datastructure.ComponentTable;

/**
 * Created by esia on 05/07/17.
 */
public class CraftRecipeType extends CraftObjectType {
	private ComponentTable<CraftIngredientInstance> ingredients;
	private CraftIngredientInstance result;

	public CraftRecipeType(String name, String description,
						   ComponentTable<CraftIngredientInstance> ingredients,
						   CraftIngredientInstance result) {
		super(name, description);
		this.ingredients = ingredients;
		this.result = result;
	}
}
