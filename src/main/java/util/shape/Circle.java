package util.shape;

import util.math.Vector;

public class Circle implements Shape {

	public final Vector pos;
	public final float radius;

	Circle(Circle circle) {
		this.pos = circle.pos;
		this.radius = circle.radius;
	}

	public Circle(Vector pos, float radius) {
		assert(radius > 0);
		this.pos = pos;
		this.radius = radius;
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(
				pos.sub(new Vector(-radius, -radius)),
				new Vector(2*radius, 2*radius)
		);
	}
}
