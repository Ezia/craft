package view.ui.drawable;

import util.shape.Rectangle;
import util.shape.Shape;

/**
 * Created by esia on 16/07/17.
 */
public abstract class UIShape<T extends Shape> implements UIDrawable {
	protected final T shape;

	public UIShape(T shape) {
		this.shape = shape;
	}

	public T getShape() {
		return shape;
	}

	@Override
	public Rectangle getBoundingBox() {
		return shape.getBoundingBox();
	}
}
