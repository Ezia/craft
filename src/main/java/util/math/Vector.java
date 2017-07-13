package util.math;

import static util.Test.*;

import java.util.Arrays;

public class Vector {

	///// FIELDS /////

	private final double[] values;

	///// CONSTRUCTORS /////

	public Vector(int size, double initValue) {
		this.values = new double[greater(size, 0)];
		for (int i = 0; i < size; i++) {
			this.values[i] = initValue;
		}
	}

	public Vector(int size, double[] values) {
		this.values = new double[range(size, 1, notNull(values).length+1)];
		for (int i = 0; i < size; i++) {
			this.values[i] = values[i];
		}
	}

	public Vector(Vector vector, double ... endValues) {
		this.values = new double[notNull(vector).size() + notNull(endValues).length];
		for (int i = 0; i < vector.size(); i++) {
			this.values[i] = vector.values[i];
		}
		for (int i = 0; i < endValues.length; i++) {
			this.values[vector.size() + i] = endValues[i];
		}
	}

	public Vector(int size) {
		this(size, 0.);
	}

	public Vector(int size, Vector vector) {
		this(size, notNull(vector).values);
	}

	public Vector(double ... values) {
		this(notNull(values).length, values);
	}

	public Vector(Vector vector) {
		this(notNull(vector).size(), vector.values);
	}

	///// ACCESSORS /////

	public int size() {
		return this.values.length;
	}

	public double get(int i) {
		return this.values[range(i, 0, size())];
	}

	public double x() {
		return this.values[0];
	}

	public double y() {
		sizeGreaterOrEqual(this, 2);
		return this.values[1];
	}

	public double z() {
		sizeGreaterOrEqual(this, 3);
		return this.values[2];
	}

	public double w() {
		sizeGreaterOrEqual(this, 2);
		return this.values[size()-1];
	}

	public double norm2() {
		double norm2 = 0;
		for (int i = 0; i < this.size(); ++i) {
			norm2 += this.values[i];
		}
		return norm2;
	}

	public double norm() {
		return Math.sqrt(this.norm2());
	}

	public double[] getDoubleArray() {
		return Arrays.copyOf(this.values, this.values.length);
	}

	public float[] getFloatArray() {
		float[] array = new float[this.size()];
		for (int i = 0; i < this.size(); ++i) {
			array[i] = (float)this.values[i];
		}
		return array;
	}

	///// MODIFIERS /////

	private void set(int i, double value) {
		assert(i >= 0 && i < size());
		values[i] =  value;
	}

	private void setX(double value) {
		assert(size() >= 2 && size() <= 4);
		set(0, value);
	}

	private void setY(double value) {
		assert(size() >= 2 && size() <= 4);
		set(1, value);
	}

	private void setZ(double value) {
		assert(size() >= 3 && size() <= 4);
		set(2, value);
	}

	private void setW(double value) {
		assert(size() == 4);
		set(3, value);
	}

	///// OPERATIONS /////

	public Vector cross(Vector vector) {
		sizeEqual(this, notNull(vector));
		return new Vector(
				this.y()*vector.z() - this.z()*vector.y(),
				this.z()*vector.x() - this.x()*vector.z(),
				this.x()*vector.y() - this.y()*vector.x()
		);
	}

	public double dot(Vector vector) {
		sizeEqual(this, notNull(vector));
		double result = 0;
		for (int i = 0; i < this.size(); i++) {
			result += this.values[i] * vector.values[i];
		}
		return result;
	}

	public Vector times(Matrix matrix) {
		equal(notNull(matrix.lineNbr()), this.size());
		Vector result = new Vector(matrix.columnNbr());
		for (int c = 0; c < matrix.columnNbr(); ++c) {
			double value = 0;
			for (int l = 0; l < matrix.lineNbr(); ++l) {
				value += matrix.get(l, c) * this.values[l];
			}
			result.values[c] = value;
		}
		return result;
	}

	public Vector add(Vector vector) {
		Vector result = new Vector(equal(notNull(vector).size(), this.size()));
		for (int i = 0; i < this.size(); i++) {
			result.values[i] = this.values[i] + vector.values[i];
		}
		return result;
	}

	public Vector add(double factor) {
		Vector result = new Vector(this.size());
		for (int i = 0; i < this.size(); i++) {
			result.values[i] = this.values[i] + factor;
		}
		return result;
	}

	public Vector sub(Vector vector) {
		Vector result = new Vector(equal(notNull(vector).size(), this.size()));
		for (int i = 0; i < this.size(); i++) {
			result.values[i] = this.values[i] - vector.values[i];
		}
		return result;
	}

	public Vector wiseTimes(Vector vector) {
		Vector result = new Vector(equal(notNull(vector).size(), this.size()));
		for (int i = 0; i < this.size(); i++) {
			result.values[i] = this.values[i] * vector.values[i];
		}
		return result;
	}

	public Vector times(double factor) {
		Vector result = new Vector(this.size());
		for (int i = 0; i < this.size(); i++) {
			result.values[i] = this.values[i] * factor;
		}
		return result;
	}

	public Vector capMax(Vector cap) {
		Vector result = new Vector(equal(notNull(cap).size(), this.size()));
		for (int i = 0; i < this.size(); i++) {
			result.values[i] = Math.min(this.values[i], cap.values[i]);
		}
		return result;
	}

	public Vector capMin(Vector cap) {
		Vector result = new Vector(equal(notNull(cap).size(), this.size()));
		for (int i = 0; i < this.size(); i++) {
			result.values[i] = Math.max(this.values[i], cap.values[i]);
		}
		return result;
	}

	public Vector capMax(double cap) {
		Vector result = new Vector(this.size());
		for (int i = 0; i < this.size(); i++) {
			result.values[i] = Math.min(this.values[i], cap);
		}
		return result;
	}

	public Vector capMin(double cap) {
		Vector result = new Vector(this.size());
		for (int i = 0; i < this.size(); i++) {
			result.values[i] = Math.max(this.values[i], cap);
		}
		return result;
	}

	public Vector clamp(double min, double max) {
		lessOrEqual(min, max);
		Vector result = new Vector(this.size());
		for (int i = 0; i < this.size(); i++) {
			result.values[i] = Math.min(Math.max(this.values[i], min), max);
		}
		return result;
	}

	public Vector clamp(Vector min, Vector max) {
		Vector result = new Vector(equal(equal(notNull(min).size(), notNull(max).size()), this.size()));
		for (int i = 0; i < this.size(); i++) {
			lessOrEqual(min.values[i], max.values[i]);
			result.values[i] = Math.min(Math.max(this.values[i], min.values[i]), max.values[i]);
		}
		return result;
	}

	public Vector normalize(double norm) {
		return this.times(greaterOrEqual(norm, 0.)/this.norm());
	}

	public Vector normalize() {
		return this.normalize(1.);
	}

	public Vector homogenize() {
		sizeGreaterOrEqual(this, 2);
		return this.times(1/this.values[this.size()-1]);
	}
}

