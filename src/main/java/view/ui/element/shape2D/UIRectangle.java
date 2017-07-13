package view.ui.element.shape2D;

import util.math.Vector;
import util.shape.Rectangle;

/**
 * Created by esia on 02/07/17.
 */
public class UIRectangle extends UIShape2D<Rectangle> {

	public UIRectangle(Vector diag, Vector color) {
		super(new Rectangle(new Vector(0., 0.), diag), color);
	}

	@Override
	public double width() {
		return shape.diag.x();
	}

	@Override
	public double height() {
		return shape.diag.y();
	}
}
