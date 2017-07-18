package view.ui;


import util.math.Matrix;
import util.math.Transform;
import util.math.Vector;
import util.shape.Rectangle;

/**
 *
 * @param <T> The "real" type of UIElement.
 */
public abstract class UIElement  implements UIObject {

	private UIContainer parent = null;

	protected final Transform transform = new Transform(2, 2);

	protected boolean proportionnal;

	public UIElement(boolean proportionnal) {
		this.proportionnal = proportionnal;
	}

	public UIContainer getParent() {
		return this.parent;
	}

	public void setParent(UIContainer parent, Transform transform) {
		if (this.parent != null) {
			this.parent.remove(this);
		}
		this.transform.setParent(transform);
		this.parent = parent;
	}

	public boolean isProportionnal() {
		return proportionnal;
	}
//
//	public double getProportion() {
//		Rectangle box = getBoundingBox();
//		return box.diag.x()/box.diag.y();
//	}

	@Override
	public Matrix getTransformGlobalMatrix() {
		return transform.globalMatrix();
	}
//
//	@Override
//	public Transform getTransform() {
//		return transform;
//	}
}
