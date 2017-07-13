package util.shape;

import util.math.Vector;

import java.util.Arrays;

/**
 * Indices are defined counter clockwise
 */
public class PolygonalChain {
	private final Vector[] points;
	private final int[] indices;

	public PolygonalChain(Vector[] points, int[] indices) {
		// TODO check input
		this.points = points;
		this.indices = indices;
	}

	public Vector getPoint(int i) {
		return points[i];
	}

	public int getIndex(int i) {
		return indices[i];
	}

	public int getPointNbr() {
		return points.length;
	}

	public int getIndexNbr() {
		return indices.length;
	}

	public int[] getIndexArray() {
		return Arrays.copyOf(indices, indices.length);
	}

	public double[] getPointArray() {
		double[] array = new double[2*points.length];
		for (int i = 0; i < points.length; ++i) {
			array[2*i] = points[i].x();
			array[2*i+1] = points[i].y();
		}
		return array;
	}

	public float[] getFloatPointArray() {
		float[] array = new float[2*points.length];
		for (int i = 0; i < points.length; ++i) {
			array[2*i] = (float)points[i].x();
			array[2*i+1] = (float)points[i].y();
		}
		return array;
	}
}
