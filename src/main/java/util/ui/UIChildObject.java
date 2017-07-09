package util.ui;

import util.math.Transform;
import util.math.shape.shape2d.Rectangle;

/**
 * Created by esia on 08/07/17.
 */
public abstract class UIChildObject implements UIObject {

	private UIParent parent = null;
	private final Transform transform = new Transform(2, 2);

	@Override
	public UIParent getParent() {
		return this.parent;
	}

	@Override
	public void setParent(UIParent parent) {
		if (this.parent != null) {
			this.parent.removeChild(this);
		}
		getTransform().setParent(null);
		this.parent = parent;
	}

	@Override
	public Transform getTransform() {
		return transform;
	}

	@Override
	public Rectangle getGlobalBoundingBox() {
		return transform.applyToRectangle(getLocalBoundingBox());
	}
}
