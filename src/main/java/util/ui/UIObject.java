package util.ui;

import util.math.Transform;
import util.math.shape.shape2d.Rectangle;

/**
 * Created by esia on 13/07/17.
 */
public interface UIObject {

	public abstract double width();

	public abstract double height();

	public abstract Rectangle getLocalBoundingBox();

	public UIContainer getParent();

	public void setParent(UIContainer parent);

	public void setTransformParent(Transform parent);

	public Rectangle getGlobalBoundingBox();
}
