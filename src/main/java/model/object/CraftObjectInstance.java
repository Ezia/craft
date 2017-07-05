package model.object;

/**
 * Created by esia on 05/07/17.
 */
public class CraftObjectInstance<T extends CraftObjectType> {
	public final T type;

	public CraftObjectInstance(T type) {
		this.type = type;
	}
}

