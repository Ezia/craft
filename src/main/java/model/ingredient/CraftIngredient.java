package model.ingredient;

import model.CraftObject;

/**
 * Created by esia on 05/07/17.
 */
public class CraftIngredient extends CraftObject<CraftIngredientType> {
	private int stack;

	public CraftIngredient(CraftIngredientType type, int stack) {
		super(type);
		this.stack = stack;
	}

	public CraftIngredient(CraftIngredient instance) {
		this(instance.type, instance.stack);
	}

	public int getStack() {
		return stack;
	}

	public void setStack(int stack) {
		assert(stack > 0);
		this.stack = stack;
	}
}
