package view.ui;

import util.math.Matrix;
import util.math.Transform;
import util.math.Vector;
import util.shape.Rectangle;

import java.util.stream.DoubleStream;

/**
 * Created by esia on 13/07/17.
 */
public interface UIObject {

	void setParent(UIContainer parent, Transform transform);

	UIContainer getParent();

	Matrix getTransformGlobalMatrix();

	// Dimension

	Vector getDimension();

	boolean isProportionnal();

	default double getProportion() {
		Vector dim = getDimension();
		return dim.x()/dim.y();
	}

	void setDimension(Vector dimension);

	default Vector getMinDimension() {
		return new Vector(0., 0.);
	}

	default Vector getMaxDimension() {
		return new Vector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	}
}
