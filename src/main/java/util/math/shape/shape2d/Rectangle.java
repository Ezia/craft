package util.math.shape.shape2d;

import util.math.shape.Shape2D;
import util.math.Vector;


public class Rectangle extends Shape2D {

	/**
	 * diagonal vector from top-left corner to opposite corner
	 */
	public final Vector diag;

	public Rectangle(Rectangle rectangle) {
		super(rectangle.pos);
		this.diag = rectangle.diag;
	}

	public Rectangle(Vector pos, Vector diag) {
		super(pos);
		assert(diag.size() == 2);
		this.diag = diag;
	}

	@Override
	public Rectangle getBoundingBox() {
		return this;
	}
}
