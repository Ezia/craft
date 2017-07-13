package util.math;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Vector {

	///// FIELDS /////

	private final double[] values;

	///// OBJECT FUNCTIONS /////

	@Override
	public boolean equals(Object vector) {
		assert(Vector.class.isInstance(vector));
		return Arrays.equals(this.values, ((Vector)vector).values);
	}

	@Override
	public String toString() {
		return Arrays.toString(this.values);
	}

	///// CONSTRUCTORS /////

	public Vector(int size) {
		assert(size > 0);
		this.values = new double[size];
	}

	public Vector(int size, double initValue) {
		assert(size > 0);
		this.values = new double[size];
		for (int i = 0; i < size; i++) {
			this.values[i] = initValue;
		}
	}

	public Vector(int size, Vector vector) {
		assert(size > 0 && vector.values.length >= size);
		this.values = Arrays.copyOf(vector.values, size);
	}

	public Vector(int size, double[] values) {
		assert(size > 0 && values.length >= size);
		this.values = Arrays.copyOf(values, size);
	}

	public Vector(double ... values) {
		assert(values.length > 0);
		this.values = Arrays.copyOf(values, values.length);
	}

	public Vector(Vector vector) {
		this.values = Arrays.copyOf(vector.values, vector.values.length);
	}

	public Vector(Vector vector, double ... endValues) {
		this.values = DoubleStream.concat(Arrays.stream(vector.values), Arrays.stream(endValues))
				.toArray();
	}

	///// ACCESSORS /////

	public int size() {
		return this.values.length;
	}

	public double get(int i) {
		assert(i >= 0 && i < size());
		return this.values[i];
	}

	public double x() {
		assert(size() >= 2 && size() <= 4);
		return get(0);
	}

	public double y() {
		assert(size() >= 2 && size() <= 4);
		return get(1);
	}

	public double z() {
		assert(size() >= 3 && size() <= 4);
		return get(2);
	}

	public double w() {
		assert(size() == 4);
		return get(3);
	}

	public double norm2() {
		double norm2 = 0;
		for (int i = 0; i < this.size(); ++i) {
			norm2 += this.get(i);
		}
		return norm2;
	}

	public double norm() {
		return (double)Math.sqrt(norm2());
	}

	public double[] getArray() {
		return Arrays.copyOf(values, values.length);
	}

	public float[] getFloatArray() {
		float[] array = new float[size()];
		for (int i = 0; i < size(); ++i) {
			array[i] = (float)get(i);
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

	public Vector cross(Vector vect) {
		assert(size() == 3 && vect.size() == 3);
		return new Vector(
				y()*vect.z() - z()*vect.y(),
				z()*vect.x() - x()*vect.z(),
				x()*vect.y() - y()*vect.x()
		);
	}

	public double dot(Vector vect) {
		assert (size() == vect.size());
		double result = 0;
		for (int i = 0; i < size(); i++) {
			result += get(i) * vect.get(i);
		}
		return result;
	}

	public Vector rightMult(Matrix mat) {
		assert(mat.columnNbr() == size());
		Vector result = new Vector(mat.lineNbr());
		for (int l = 0; l < mat.lineNbr(); ++l) {
			double value = 0;
			for (int c = 0; c < mat.columnNbr(); ++c) {
				value += mat.get(l, c) * get(c);
			}
			result.set(l, value);
		}
		return result;
	}

	public Vector leftMult(Matrix mat) {
		assert(mat.lineNbr() == size());
		Vector result = new Vector(mat.columnNbr());
		for (int c = 0; c < mat.columnNbr(); ++c) {
			double value = 0;
			for (int l = 0; l < mat.lineNbr(); ++l) {
				value += mat.get(l, c) * get(l);
			}
			result.set(c, value);
		}
		return result;
	}


	public Vector add(Vector vector) {
		assert(vector.size() == size());
		Vector result = new Vector(size());
		for (int i = 0; i < size(); i++) {
			result.set(i, get(i) + vector.get(i));
		}
		return result;
	}

	public Vector add(double factor) {
		Vector result = new Vector(size());
		for (int i = 0; i < size(); i++) {
			result.set(i, get(i) + factor);
		}
		return result;
	}

	public Vector sub(Vector vector) {
		assert(vector.size() == size());
		Vector result = new Vector(size());
		for (int i = 0; i < size(); i++) {
			result.set(i, get(i) - vector.get(i));
		}
		return result;
	}

	public Vector wiseMult(Vector vector) {
		assert(vector.size() == size());
		Vector result = new Vector(size());
		for (int i = 0; i < size(); i++) {
			result.set(i, get(i) * vector.get(i));
		}
		return result;
	}

	public Vector mult(double factor) {
		Vector result = new Vector(size());
		for (int i = 0; i < size(); i++) {
			result.set(i, get(i) * factor);
		}
		return result;
	}

	public Vector capMax(Vector cap) {
		assert(cap.size() == size());
		Vector result = new Vector(size());
		for (int i = 0; i < size(); i++) {
			result.set(i, Math.min(get(i), cap.get(i)));
		}
		return result;
	}

	public Vector capMin(Vector cap) {
		assert(cap.size() == size());
		Vector result = new Vector(size());
		for (int i = 0; i < size(); i++) {
			result.set(i, Math.max(get(i), cap.get(i)));
		}
		return result;
	}

	public Vector capMax(double cap) {
		Vector result = new Vector(size());
		for (int i = 0; i < size(); i++) {
			result.set(i, Math.min(get(i), cap));
		}
		return result;
	}

	public Vector capMin(double cap) {
		Vector result = new Vector(size());
		for (int i = 0; i < size(); i++) {
			result.set(i, Math.max(get(i), cap));
		}
		return result;
	}

	public Vector clamp(double min, double max) {
		assert(min <= max);
		Vector result = new Vector(size());
		for (int i = 0; i < size(); i++) {
			result.set(i, Math.min(Math.max(get(i), min), max));
		}
		return result;
	}

	public Vector clamp(Vector min, Vector max) {
		assert(size() == max.size() && size() == min.size());
		Vector result = new Vector(size());
		for (int i = 0; i < size(); i++) {
			assert(min.get(i) <= max.get(i));
			result.set(i, Math.min(Math.max(get(i), min.get(i)), max.get(i)));
		}
		return result;
	}

	public Vector setNorm(double norm) {
		assert(norm > 0);
		return mult(norm/norm());
	}

	public Vector normalize() {
		return setNorm(1);
	}

	public Vector homogenize() {
		assert(size() >= 2);
		return mult(1/get(size()-2));
	}
}

