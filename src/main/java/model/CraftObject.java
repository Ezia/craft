package model;

/**
 * Created by esia on 05/07/17.
 */
public class CraftObject<T extends CraftObjectType> {
	public final T type;

	public CraftObject(T type) {
		this.type = type;
	}
}

