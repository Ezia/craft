package util.ui;


import util.math.Transform;
import util.math.shape.shape2d.Rectangle;

/**
 *
 * @param <T> The "real" type of UIObject.
 */
public interface UIObject {

	public UIParent getParent();

	public void setParent(UIParent parent);

	public Transform getTransform();

	public double width();

	public double height();

	public Rectangle getLocalBoundingBox();

	public Rectangle getGlobalBoundingBox();

}
