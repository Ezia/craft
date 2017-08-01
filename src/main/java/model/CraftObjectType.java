package model;

import view.ui.Image;

/**
 * Created by esia on 05/07/17.
 */
public class CraftObjectType {
	public final String name;
	public final String description;
	public final Image image;

	public CraftObjectType(String name, String description, Image image) {
		this.name = name;
		this.description = description;
		this.image = image;
	}
}
