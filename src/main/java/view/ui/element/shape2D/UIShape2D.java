package view.ui.element.shape2D;

import util.shape.Rectangle;
import util.shape.Shape;
import util.math.Vector;
import view.ui.UIElement;

public abstract class UIShape2D<T extends Shape> extends UIElement {

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
