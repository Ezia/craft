package util.math;

import util.math.shape.shape2d.Rectangle;

import java.util.Arrays;

public class Matrix {

	///// FIELDS /////

	private double[][] values;

	///// CONSTRUCTORS /////

	public Matrix(Matrix matrix) {
		this.values = Arrays.copyOf(matrix.values, matrix.size());
	}

	public Matrix(int lineNbr, int columnNbr, Matrix matrix) {
		assert(columnNbr > 0 && lineNbr > 0);
		this.values = new double[lineNbr][columnNbr];
		for (int l = 0; l < lineNbr; ++l) {
			for (int c = 0; c < columnNbr; ++c) {
				this.values[l][c] = matrix.values[l][c];
			}
		}
	}

	public Matrix(int lineNbr, int columnNbr) {
		assert(columnNbr > 0 && lineNbr > 0);
		this.values = new double[lineNbr][columnNbr];
	}

	public Matrix(int lineNbr, int columnNbr, double initValue) {
		assert(columnNbr > 0 && lineNbr > 0);
		this.values = new double[lineNbr][columnNbr];
		for (int l = 0; l < lineNbr; ++l) {
			for (int c = 0; c < columnNbr; ++c) {
				this.values[l][c] = initValue;
			}
		}
	}

	/**
	 *
	 * @param lineNbr
	 * @param columnNbr
	 * @param values Concatenation of matrix lines
	 */
	public Matrix(int lineNbr, int columnNbr, double[] values) {
		assert(columnNbr > 0 && lineNbr > 0 && values.length == columnNbr*lineNbr);
		this.values = new double[lineNbr][columnNbr];
		for (int l = 0; l < lineNbr; ++l) {
			for (int c = 0; c < columnNbr; ++c) {
				this.values[l][c] = values[l*columnNbr + c];
			}
		}
	}

	public Matrix(double[][] values) {
		assert(values.length > 0 && values[0].length > 0);
		this.values = new double[values.length][values[0].length];
		for (int l = 0; l < values.length; ++l) {
			for (int c = 0; c < values[0].length; ++c) {
				this.values[l][c] = values[l][c];
			}
		}
	}

	public Matrix(int lineNbr, int columnNbr, double[][] values) {
		assert(lineNbr > 0 && columnNbr > 0
				&& values.length <= lineNbr && values[0].length <= columnNbr
		);
		this.values = new double[lineNbr][columnNbr];
		for (int l = 0; l < lineNbr; ++l) {
			for (int c = 0; c < columnNbr; ++c) {
				this.values[l][c] = values[l][c];
			}
		}
	}

	public Matrix(int size) {
		assert(size > 0);
		this.values = new double[size][size];
	}

	///// ACCESSORS /////

	public int lineNbr() {
		return values.length;
	}

	public int columnNbr() {
		return values[0].length;
	}

	public int size() {
		return lineNbr()*columnNbr();
	}

	public double get(int l, int c) {
		assert(l >= 0 && l < lineNbr() && c >= 0 && c < columnNbr());
		return values[l][c];
	}

	public boolean isSquare() {
		return lineNbr() == columnNbr();
	}

	public boolean isInvertible() {
		// TODO
		return false;
	}

	public float[] getFloatLineArray() {
		float[] array = new float[lineNbr()*columnNbr()];
		for (int l = 0; l < lineNbr(); ++l) {
			for (int c = 0; c < columnNbr(); ++c) {
				array[l*columnNbr() + c] = (float)get(l, c);
			}
		}
		return array;
	}

	public float[] getFloatColumnArray() {
		float[] array = new float[lineNbr()*columnNbr()];
		for (int l = 0; l < lineNbr(); ++l) {
			for (int c = 0; c < columnNbr(); ++c) {
				array[c*lineNbr() + l] = (float)get(l, c);
			}
		}
		return array;
	}

	///// MODIFIERS /////

	private void set(int l, int c, double value) {
		assert(l >= 0 && l < lineNbr() && c >= 0 && c < columnNbr());
		values[l][c] = value;
	}

	///// OPERATIONS /////

	public Matrix transpose() {
		Matrix transpose = new Matrix(columnNbr(), lineNbr());
		for (int l = 0; l < lineNbr(); ++l) {
			for (int c = 0; c < columnNbr(); ++c) {
				transpose.set(c, l, get(l, c));
			}
		}
		return transpose;
	}

	public Matrix inverse() {
		// TODO
		return null;
	}

	public Matrix rightMult(Matrix mat) {
		assert(columnNbr() == mat.lineNbr());
		Matrix result = new Matrix(lineNbr(), mat.columnNbr());
		for (int l1 = 0; l1 < lineNbr(); ++l1) {
			for (int c2 = 0; c2 < mat.columnNbr(); ++c2) {
				double value = 0;
				for (int x = 0; x < mat.lineNbr(); ++x) {
					value += get(l1, x) * mat.get(x, c2);
				}
				result.set(l1, c2, value);
			}
		}
		return result;
	}

	public Matrix leftMult(Matrix mat) {
		assert(mat.columnNbr() == lineNbr());
		Matrix result = new Matrix(mat.lineNbr(), columnNbr());
		for (int l1 = 0; l1 < mat.lineNbr(); ++l1) {
			for (int c2 = 0; c2 < columnNbr(); ++c2) {
				double value = 0;
				for (int x = 0; x < lineNbr(); ++x) {
					value += mat.get(l1, x) * get(x, c2);
				}
				result.set(l1, c2, value);
			}
		}
		return result;
	}

	public Matrix times(double value) {
		Matrix result = new Matrix(lineNbr(), columnNbr());
		for (int l = 0; l < lineNbr(); ++l) {
			for (int c = 0; c < columnNbr(); ++c) {
				result.set(l, c, get(l, c) * value);
			}
		}
		return result;
	}

	public Matrix wiseMult(Matrix matrix) {
		assert(matrix.lineNbr() == lineNbr()
				&& matrix.columnNbr() == columnNbr()
		);
		Matrix result = new Matrix(lineNbr(), columnNbr());
		for (int l = 0; l < lineNbr(); ++l) {
			for (int c = 0; c < columnNbr(); ++c) {
				result.set(l, c, get(l, c) * matrix.get(l, c));
			}
		}
		return result;
	}

	///// STANDARD MATRICES /////

	// all left multiply matrices : V * M

	public static Matrix identity(int lineNbr, int columnNbr) {
		Matrix mat = new Matrix(lineNbr, columnNbr, 0);
		int minDim = Math.min(lineNbr, columnNbr);
		for (int x = 0; x < minDim; ++x) {
			mat.values[x][x] = 1;
		}
		return mat;
	}

	/**
	 *
	 * @param center
	 * @param angle in rad
	 * @return
	 */
	public static Matrix rotation2D(Vector center, double angle) {
		assert(center.size() == 2);
		Matrix mat = new Matrix(3, 3);
		double cos = (double)Math.cos(angle);
		double sin = (double)Math.sin(angle);
		double a = center.x();
		double b = center.y();
		mat.set(0,0,cos); 				mat.set(0,1,sin); 				mat.set(0,2,0);
		mat.set(1,0,-sin); 				mat.set(1,1,cos);  				mat.set(1,2,0);
		mat.set(2,0,-a*cos + b*sin + a); mat.set(2,1,-a*cos - b*sin + b); mat.set(2,2,1);
		return mat;
	}

	public static Matrix translation2D(Vector translation) {
		assert(translation.size() == 2);
		double a = translation.x();
		double b = translation.y();
		Matrix mat = new Matrix(3, 3);
		mat.set(0,0,1); mat.set(0,1,0); mat.set(0,2,0);
		mat.set(1,0,0); mat.set(1,1,1); mat.set(1,2,0);
		mat.set(2,0,a); mat.set(2,1,b); mat.set(2,2,1);
		return mat;
	}

	public static Matrix homothety2D(Vector center, Vector scale) {
		assert(scale.size() == 2 && center.size() == 2);
		double a = center.x();
		double b = center.y();
		Matrix mat = new Matrix(3, 3);
		mat.set(0,0,scale.x()); 	   mat.set(0,1,0); 				  mat.set(0,2,0);
		mat.set(1,0,0); 			   mat.set(1,1,scale.y()); 		  mat.set(1,2,0);
		mat.set(2,0,-a*scale.x() + a); mat.set(2,1,-b*scale.y() + b); mat.set(2,2,1);
		return mat;
	}

	/**
	 * Move and scale the rectangle to be centered around (0,0) and range between -1 and 1 ox x and y
	 * @param rect
	 * @return
	 */
	public static Matrix projection2D(Rectangle rect) {
		Vector center = rect.pos.add(rect.diag.mult(1./2.));
		double xScale = 1./(rect.diag.x()/2.);
		double yScale = 1./(rect.diag.y()/2.);
		Matrix mat = new Matrix(3, 3);
		mat.set(0,0,xScale); 			 mat.set(0,1,0); 				  mat.set(0,2,0);
		mat.set(1,0,0); 			  	 mat.set(1,1,yScale); 			  mat.set(1,2,0);
		mat.set(2,0,-center.x()*xScale); mat.set(2,1,-center.y()*yScale); mat.set(2,2,1);
		return mat;
	}
}
