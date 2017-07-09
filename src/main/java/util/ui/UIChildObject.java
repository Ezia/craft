package util.ui;

import util.math.Transform;

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
		this.parent.removeChild(this);
		this.transform.setParent(null);
		this.parent = parent;
	}

	@Override
	public Transform getTransform() {
		return transform;
	}
}
