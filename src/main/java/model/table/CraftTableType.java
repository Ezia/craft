package model.table;

import model.CraftObjectType;
import util.datastructure.Bitmap;

/**
 * Created by esia on 05/07/17.
 */
public class CraftTableType extends CraftObjectType {

	public final Bitmap bitmap;

	public CraftTableType(String name, String description, Bitmap bitmap) {
		super(name, description);
		this.bitmap = bitmap;
	}
}
