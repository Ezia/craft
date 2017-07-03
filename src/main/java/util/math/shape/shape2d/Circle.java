package util.math.shape.shape2d;

import util.math.shape.Shape2D;
import util.math.Vector;

public class Circle extends Shape2D {

	public final float radius;

	Circle(Circle circle) {
		super(circle.pos);
		radius = circle.radius;
	}

	public Circle(Vector pos, float radius) {
		super(pos);
		this.radius = radius;
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(
				pos,
				new Vector(2*radius, 2*radius)
		);
	}
}
