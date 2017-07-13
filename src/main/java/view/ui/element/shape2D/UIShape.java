package view.ui.element.shape2D;

import util.shape.Rectangle;
import util.shape.Shape;
import util.math.Vector;
import view.ui.UIElement;

public abstract class UIShape extends UIElement {

	protected final Shape shape;

	protected UIShape(Shape shape) {
		this.shape = shape;
	}

	public Shape getShape() {
		return shape;
	}

	@Override
	public Rectangle getLocalBoundingBox() {
		return transform.applyLocalToRectangle(shape.getBoundingBox());
	}

	@Override
	public double width() {
		return getLocalBoundingBox().diag.x();
	}

	@Override
	public double height() {
		return getLocalBoundingBox().diag.y();
	}
}
