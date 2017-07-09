package util.ui.element.shape2D;

import util.math.shape.shape2d.Rectangle;
import util.ui.UIElement;
import util.math.shape.Shape2D;
import util.math.Vector;

public abstract class UIShape2D<T extends Shape2D> extends UIElement {

	public final Vector color;
	public final T shape;

	protected UIShape2D(T shape, Vector color) {
		assert(color.size() == 4);
		this.shape = shape;
		this.color = color;
	}

	@Override
	public Rectangle getLocalBoundingBox() {
		return transform.applyLocalToRectangle(shape.getBoundingBox());
	}
}
