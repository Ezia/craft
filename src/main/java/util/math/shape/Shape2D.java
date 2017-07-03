package util.math.shape;

import util.math.Vector;
import util.math.shape.shape2d.Rectangle;

public abstract class Shape2D implements Shape {
	public final Vector pos;

	public Shape2D(Vector pos) {
		assert(pos.size() == 2);
		this.pos = pos;
	}

	public abstract Rectangle getBoundingBox();
}
