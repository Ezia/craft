package util.ui;


import util.math.Transform;
import util.math.shape.shape2d.Rectangle;

/**
 *
 * @param <T> The "real" type of UIElement.
 */
public abstract class UIElement implements UIObject {

	private UIContainer parent = null;
	public final Transform transform = new Transform(2, 2);

	public abstract double width();

	public abstract double height();

	public abstract Rectangle getLocalBoundingBox();

	public UIContainer getParent() {
		return this.parent;
	}

	public void setParent(UIContainer parent) {
		if (this.parent != null) {
			this.parent.remove(this);
		}
		setTransformParent(null);
		this.parent = parent;
	}

	public void setTransformParent(Transform parent) {
		transform.setParent(parent);
	}

	public Rectangle getGlobalBoundingBox() {
		return transform.applyToRectangle(getLocalBoundingBox());
	}

}
