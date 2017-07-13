package util.shape;

import util.math.Vector;

/**
 * Origine is top left
 * horizontal axis directed down is x axis
 * vertical axis directed right is y axis
 */
public interface Shape {
	public abstract Rectangle getBoundingBox();
}
