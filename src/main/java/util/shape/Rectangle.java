package util.shape;

import util.math.Vector;

public class Rectangle implements Polygon {

	/**
	 * diagonal vector from top-left corner to opposite corner
	 */
	public final Vector diag;

	public final Vector pos;

	public Rectangle(Rectangle rectangle) {
		this.pos = rectangle.pos;
		this.diag = rectangle.diag;
	}

	public Rectangle(Vector pos, Vector diag) {
//		assert(diag.sizeEqual() == 2);
//		assert(pos.sizeEqual() == 2);
//		assert(pos.x() >= 0);
//		assert(pos.y() >= 0);
		this.pos = pos;
		this.diag = diag;
	}

	@Override
	public PolygonalChain getPolygonalChain() {
		Vector[] points = {
				pos,
				new Vector(pos.x() + diag.x(), pos.y()),
				pos.add(diag),
				new Vector(pos.x(), pos.y() + diag.y())
		};
		int[] indices = {0, 1, 2, 3};
		return new PolygonalChain(points, indices);
	}

	@Override
	public PolygonalChain getTriangleChain() {
		Vector[] points = {
				new Vector(pos.x() + diag.x(), pos.y()),
				pos,
				pos.add(diag),
				new Vector(pos.x(), pos.y() + diag.y())
		};
		int[] indices = {0, 1, 2, 3};
		return new PolygonalChain(points, indices);
	}

	@Override
	public Rectangle getBoundingBox() {
		return this;
	}

	@Override
	public int getPointNbr() {
		return 4;
	}
}
