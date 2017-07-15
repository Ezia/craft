package util.math;

import static util.Test.*;
import util.shape.Rectangle;

import java.util.LinkedList;

public class Transform {

	///// FIELDS /////

	/**
	 * Left multiplication : V * M
	 */
	private Matrix globalMatrix;
	private Matrix localMatrix;
	private Transform parent = null;
	private LinkedList<Transform> children = new LinkedList<>();
	private boolean upToDate;

	///// CONSTRUCTORS /////

	public Transform(int inputDim, int outputDim, Transform parent) {
		this.localMatrix = Matrix.identity(greater(inputDim, 0)+1, greater(outputDim, 0)+1);
		this.globalMatrix = null;
		this.upToDate = false;
		this.setParent(parent);
	}

	/**
	 *
	 * @param dim1 Dimension of the input objects
	 * @param dim2 Dimension of the transformed objects
	 */
	public Transform(int inputDim, int outputDim) {
		this(inputDim, outputDim, null);
	}

	///// ACCESSORS /////

	public int inputDimension() {
		return globalMatrix().lineNbr() - 1;
	}

	public int outputDimension() {
		return localMatrix.columnNbr() - 1;
	}

	public int localInputDimension() {
		return localMatrix.lineNbr() - 1;
	}

	public void setParent(Transform parent) {
		if (this.parent != null) {
			this.parent.removeChild(this);
		}
		if (parent != null) {
			parent.addChild(this);
		} else {
			this.parent = null;
		}
	}

	public void addChild(Transform transform) {
		notContained(notNull(transform), this.children);
		equal(this.outputDimension(), transform.localInputDimension());
		if (transform.parent != null) {
			transform.parent.removeChild(transform);
		}
		this.children.add(transform);
		transform.parent = this;
		transform.setUpToDate(false);
	}

	public void removeChild(Transform transform) {
		contained(notNull(transform), this.children);
		this.children.remove(transform);
		transform.parent = null;
		transform.globalMatrix = transform.localMatrix;
		transform.setUpToDate(true);
	}

	private void setUpToDate(boolean upToDate) {
		if (this.upToDate && !upToDate) {
			this.upToDate = false;
			for (Transform transform : this.children) {
				transform.setUpToDate(false);
			}
		} else {
			this.upToDate = upToDate;
		}
	}

	public boolean isUpToDate() {
		return this.upToDate;
	}

	public Matrix globalMatrix() {
		this.update();
		return this.globalMatrix;
	}

	public Matrix localMatrix() {
		return this.localMatrix;
	}

	public Transform parent() { return this.parent; }

	///// OPERATIONS /////

	public void update() {
		if (!this.upToDate) {
			if (this.parent == null) {
				this.globalMatrix = this.localMatrix;
			} else {
				this.globalMatrix = this.parent.globalMatrix().times(this.localMatrix);
			}
			this.upToDate = true;
		}
	}

	public void setMatrix(Matrix matrix) {
		if (parent != null) {
			equal(notNull(matrix).lineNbr(), this.localInputDimension()+1);
		}
		if (!children.isEmpty()) {
			equal(matrix.columnNbr(), this.outputDimension()+1);
		}
		this.localMatrix = matrix;
		this.setUpToDate(false);
	}

	public void setIdentity() {
		this.localMatrix = Matrix.identity(this.localInputDimension()+1, this.outputDimension()+1);
		this.setUpToDate(false);
	}

	public void preMultiply(Matrix matrix) {
		equal(matrix.columnNbr(), this.outputDimension()+1);
		this.setMatrix(matrix.times(this.localMatrix));
	}

	public void postMultiply(Matrix matrix) {
		equal(matrix.lineNbr(), this.outputDimension()+1);
		this.setMatrix(this.localMatrix.times(matrix));
	}

	public Vector apply(Vector vector) {
		equal(notNull(vector).size(), inputDimension()+1);
		return vector.times(this.globalMatrix());
	}

	public Vector applyToPoint(Vector vector) {
		equal(notNull(vector).size(), inputDimension());
		return new Vector(outputDimension(), new Vector(vector, 1.).times(this.globalMatrix()).homogenize());
	}

	public Vector applyToVector(Vector vector) {
		equal(notNull(vector).size(), inputDimension());
		return new Vector(outputDimension(), new Vector(vector, 0.).times(this.globalMatrix()));
	}

	public Vector applyLocal(Vector vector) {
		equal(notNull(vector).size(), inputDimension()+1);
		return vector.times(this.localMatrix);
	}

	public Vector applyLocalToPoint(Vector vector) {
		equal(notNull(vector).size(), inputDimension());
		return new Vector(outputDimension(), new Vector(vector, 1).times(this.localMatrix).homogenize());
	}

	public Vector applyLocalToVector(Vector vector) {
		equal(notNull(vector).size(), inputDimension());
		return new Vector(outputDimension(), new Vector(vector, 0).times(this.localMatrix));
	}

	public Rectangle applyToRectangle(Rectangle rectangle) {
		return new Rectangle(this.applyToPoint(rectangle.pos),
				this.applyToVector(rectangle.diag));
	}

	public Rectangle applyLocalToRectangle(Rectangle rectangle) {
		return new Rectangle(this.applyLocalToPoint(rectangle.pos),
				this.applyLocalToVector(rectangle.diag));
	}
}
