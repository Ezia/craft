package model.object.ingredient;

import model.object.CraftObjectInstance;

/**
 * Created by esia on 05/07/17.
 */
public class CraftIngredientInstance extends CraftObjectInstance<CraftIngredientType> {
	private int stack;

	public CraftIngredientInstance(CraftIngredientType type, int stack) {
		super(type);
		this.stack = stack;
	}

	public CraftIngredientInstance(CraftIngredientInstance instance) {
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
