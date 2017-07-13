package util.shape;

import util.math.Vector;

public abstract class Shape {
	public final Vector pos;

	public Shape(Vector pos) {
		assert(pos.size() == 2);
		this.pos = pos;
	}

	public abstract Rectangle getBoundingBox();
}
