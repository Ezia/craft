package util.math;

import static util.Test.*;
import util.shape.Rectangle;


public class Matrix {

	///// FIELDS /////

	private final double[][] values;

	///// CONSTRUCTORS /////

	public Matrix(int lineNbr, int columnNbr, double initValue) {
		this.values = new double[greater(lineNbr, 0)][greater(columnNbr, 0)];
		for (int l = 0; l < lineNbr; ++l) {
			for (int c = 0; c < columnNbr; ++c) {
				this.values[l][c] = initValue;
			}
		}
	}

	public Matrix(int lineNbr, int columnNbr, double[][] values) {
		this.values = new double[range(lineNbr, 1, notNull(values).length+1)]
				[greater(columnNbr, 0)];
		for (int l = 0; l < lineNbr; ++l) {
			lessOrEqual(columnNbr, values[l].length);
			for (int c = 0; c < columnNbr; ++c) {
				this.values[l][c] = values[l][c];
			}
		}
	}

	public Matrix(int lineNbr, int columnNbr, Matrix matrix) {
		this(lineNbr, columnNbr, notNull(matrix).values);
	}

	public Matrix(int lineNbr, int columnNbr) {
		this(lineNbr, columnNbr, 0.);
	}

	public Matrix(int size) {
		this(size, size, 0.);
	}

	public Matrix(Matrix matrix) {
		this(notNull(matrix).lineNbr(), matrix.columnNbr(), matrix);
	}

	public Matrix(double[][] values) {
		this(greater(notNull(values).length, 0), values[0].length, values);
	}

	///// ACCESSORS /////

	public int lineNbr() {
		return this.values.length;
	}

	public int columnNbr() {
		return this.values[0].length;
	}

	public double get(int l, int c) {
		return this.values[range(l, 0, this.lineNbr())]
				[range(c, 0, this.columnNbr())];
	}

	public boolean isSquare() {
		return this.lineNbr() == this.columnNbr();
	}

	public boolean isInvertible() {
		// TODO
		return false;
	}

	public float[] getFloatLineArray() {
		float[] array = new float[this.lineNbr()*this.columnNbr()];
		for (int l = 0; l < this.lineNbr(); ++l) {
			for (int c = 0; c < this.columnNbr(); ++c) {
				array[l*this.columnNbr() + c] = (float)this.values[l][c];
			}
		}
		return array;
	}

	public float[] getFloatColumnArray() {
		float[] array = new float[this.lineNbr()*this.columnNbr()];
		for (int l = 0; l < this.lineNbr(); ++l) {
			for (int c = 0; c < this.columnNbr(); ++c) {
				array[c*this.lineNbr() + l] = (float)this.values[l][c];
			}
		}
		return array;
	}

	///// MODIFIERS /////

	private void set(int l, int c, double value) {
		values[range(l, 0, this.lineNbr())]
				[range(c, 0, this.columnNbr())] = value;
	}

	///// OPERATIONS /////

	public Matrix transpose() {
		Matrix transpose = new Matrix(this.columnNbr(), this.lineNbr());
		for (int l = 0; l < this.lineNbr(); ++l) {
			for (int c = 0; c < this.columnNbr(); ++c) {
				transpose.values[c][l] = this.values[l][c];
			}
		}
		return transpose;
	}

	public Matrix inverse() {
		// TODO
		return null;
	}

	/**
	 * this * mat
	 * @param mat
	 * @return
	 */
	public Matrix times(Matrix matrix) {
		equal(this.columnNbr(), notNull(matrix).lineNbr());
		Matrix result = new Matrix(this.lineNbr(), matrix.columnNbr());
		for (int l1 = 0; l1 < this.lineNbr(); ++l1) {
			for (int c2 = 0; c2 < matrix.columnNbr(); ++c2) {
				double value = 0;
				for (int x = 0; x < matrix.lineNbr(); ++x) {
					value += this.values[l1][x] * matrix.values[x][c2];
				}
				result.values[l1][c2] = value;
			}
		}
		return result;
	}

	public Vector times(Vector vector) {
		equal(this.columnNbr(), notNull(vector).size());
		double[] result = new double[this.lineNbr()];
		for (int l = 0; l < this.lineNbr(); ++l) {
			double value = 0;
			for (int c = 0; c < this.columnNbr(); ++c) {
				value += this.values[l][c] * vector.get(c);
			}
			result[l] = value;
		}
		return new Vector(result);
	}

	public Matrix times(double value) {
		Matrix result = new Matrix(this.lineNbr(), this.columnNbr());
		for (int l = 0; l < this.lineNbr(); ++l) {
			for (int c = 0; c < this.columnNbr(); ++c) {
				result.values[l][c] = this.values[l][c] * value;
			}
		}
		return result;
	}

	public Matrix wiseTimes(Matrix matrix) {
		Matrix result = new Matrix(equal(this.lineNbr(), notNull(matrix).lineNbr()),
				equal(this.columnNbr(), matrix.columnNbr()));
		for (int l = 0; l < this.lineNbr(); ++l) {
			for (int c = 0; c < this.columnNbr(); ++c) {
				result.values[l][c] = this.values[l][c] * matrix.values[l][c];
			}
		}
		return result;
	}

	///// STANDARD MATRICES /////

	// all left multiply matrices : V * M

	public static Matrix identity(int size) {
		return identity(greater(size, 0), size);
	}

	public static Matrix identity(int lineNbr, int columnNbr) {
		Matrix identity = new Matrix(greater(lineNbr, 0), greater(columnNbr, 0), 0);
		int minDim = Math.min(lineNbr, columnNbr);
		for (int x = 0; x < minDim; ++x) {
			identity.values[x][x] = 1;
		}
		return identity;
	}

	/**
	 *
	 * @param center
	 * @param angle in rad
	 * @return
	 */
	public static Matrix rotation2D(Vector center, double angle) {
		double centerX = sizeEqual(notNull(center), 2).x();
		double centerY = center.y();

		double cos = (double)Math.cos(angle);
		double sin = (double)Math.sin(angle);

		Matrix rotation = new Matrix(3, 3);

		rotation.values[0][0] = cos;
		rotation.values[0][1] = sin;
		rotation.values[0][2] = 0;

		rotation.values[1][0] = -sin;
		rotation.values[1][1] = cos;
		rotation.values[1][2] = 0;

		rotation.values[2][0] = -centerX*cos + centerY*sin + centerX;
		rotation.values[2][1] = -centerX*cos - centerY*sin + centerY;
		rotation.values[2][2] = 1;

		return rotation;
	}

	public static Matrix translation(Vector vector) {
		Matrix translation = identity(notNull(vector).size()+1);
		for (int i = 0; i < vector.size(); i++) {
			translation.values[vector.size()][i] = vector.get(i);
		}
		return translation;
	}

	public static Matrix homothety2D(Vector center, Vector scale) {
		double centerX = sizeEqual(notNull(center), 2).x();
		double centerY = center.y();
		double scaleX = sizeEqual(notNull(scale), 2).x();
		double scaleY = scale.y();

		Matrix homothety = new Matrix(3, 3);

		homothety.values[0][0] = scaleX;
		homothety.values[0][1] = 0;
		homothety.values[0][2] = 0;

		homothety.values[1][0] = 0;
		homothety.values[1][1] = scaleY;
		homothety.values[1][2] = 0;

		homothety.values[2][0] = -centerX*scaleX + centerX;
		homothety.values[2][1] = -centerY*scaleY + centerY;
		homothety.values[2][2] = 1;

		return homothety;
	}

	public static Matrix homothety2D(Vector center, double scale) {
		return homothety2D(center, new Vector(scale, scale));
	}

	/**
	 * Move and scale the rectangle to be centered around (0,0) and range between -1 and 1 ox x and y
	 * @param rectangle
	 * @return
	 */
	public static Matrix centeredProjection2D(Rectangle rectangle) {
		double centerX = notNull(rectangle).pos.x();
		double centerY = rectangle.pos.y();

		double scaleX = 2./(rectangle.diag.x());
		double scaleY = 2./(rectangle.diag.y());

		Matrix projection = new Matrix(3, 3);

		projection.values[0][0] = scaleX;
		projection.values[0][1] = 0;
		projection.values[0][2] = 0;

		projection.values[1][0] = 0;
		projection.values[1][1] = -scaleY;
		projection.values[1][2] = 0;

		projection.values[2][0] = -1 - scaleX*centerX;
		projection.values[2][1] = 1 + scaleY*centerY;
		projection.values[2][2] = 1;
		return projection;
	}

	public static Matrix corneredProjection2D(Rectangle rectangle) {
		double centerX = notNull(rectangle).pos.x();
		double centerY = rectangle.pos.y();

		double scaleX = 1./(rectangle.diag.x());
		double scaleY = 1./(rectangle.diag.y());

		Matrix projection = new Matrix(3, 3);

		projection.values[0][0] = scaleX;
		projection.values[0][1] = 0;
		projection.values[0][2] = 0;

		projection.values[1][0] = 0;
		projection.values[1][1] = scaleY;
		projection.values[1][2] = 0;

		projection.values[2][0] = -scaleX*centerX;
		projection.values[2][1] = -scaleY*centerY;
		projection.values[2][2] = 1;
		return projection;
	}
}
