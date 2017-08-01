package util.math;

import util.math.Vector;

/**
 * Created by esia on 20/06/17.
 */
public class Quaternion {

	///// FIELDS /////

	private Vector vector;

	///// CONSTRUCTORS /////

	public Quaternion(Vector axis, float angle) {
		assert(axis.size() == 3);
		// TODO
	}

	///// ACCESSORS /////

	public Vector getAxis() {
		// TODO
		return null;
	}

	public float getAngle() {
		// TODO
		return 0;
	}

	///// OPERATIONS /////

	public Vector applyToPoint(Vector point) {
		assert(point.size() == 3);
		// TODO
		return null;
	}

	public Vector applyToVector(Vector vector) {
		assert(vector.size() == 3);
		// TODO
		return null;
	}
}
