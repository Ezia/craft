package util.math;

import util.shape.Rectangle;

import java.util.LinkedList;

public class Transform {

	///// FIELDS /////

	/**
	 * Left multiplication : V * M
	 */
	private Matrix globalMatrix;
	private Matrix localMatrix;
	private Transform parent;
	private LinkedList<Transform> children = new LinkedList<>();
	private boolean upToDate;

	///// CONSTRUCTORS /////

	/**
	 *
	 * @param dim1 Dimension of the input objects
	 * @param dim2 Dimension of the transformed objects
	 */
	public Transform(int inputDim, int outputDim) {
		localMatrix = Matrix.identity(inputDim+1, outputDim+1);
		globalMatrix = localMatrix;
		upToDate = true;
		parent = null;
	}

	public Transform(int inputDim, int outputDim, Transform parent) {
		localMatrix = Matrix.identity(inputDim+1, outputDim+1);
		globalMatrix = null;
		upToDate = false;
		this.parent = parent;
	}

	///// ACCESSORS /////

	public int inputDimension() {
		return localMatrix.lineNbr() - 1;
	}

	public int outputDimension() {
		return localMatrix.columnNbr() - 1;
	}

	public void setParent(Transform parent) {
		if (this.parent != null) {
			this.parent.removeChild(this);
		}
		if (parent != null) {
			parent.addChild(this);
		}
	}

	public void addChild(Transform transform) {
		assert(!children.contains(transform));
		assert(transform != null);
		assert(outputDimension() == transform.inputDimension());
		if (transform.parent != null) {
			transform.parent.removeChild(transform);
		}
		children.add(transform);
		transform.parent = this;
		transform.setUpToDate(false);
	}

	public void removeChild(Transform transform) {
		assert (transform != null && children.contains(transform));
		children.remove(transform);
		transform.parent = null;
		transform.globalMatrix = transform.localMatrix;
		transform.setUpToDate(true);
	}

	private void setUpToDate(boolean utd) {
		if (isUpToDate() && !utd) {
			upToDate = false;
			for (Transform tr : children) {
				if (tr.isUpToDate()) {
					setUpToDate(false);
				}
			}
		} else {
			upToDate = utd;
		}
	}

	public boolean isUpToDate() {
		return upToDate;
	}

	public Matrix globalMatrix() {
		update();
		return globalMatrix;
	}

	public Matrix localMatrix() {
		return localMatrix;
	}

	public Transform parent() { return parent; }

	///// OPERATIONS /////

	public void setMatrix(Matrix mat) {
		assert(mat != null);
		this.localMatrix = mat;
		setUpToDate(false);
	}

	public void setIdentity() {
		this.localMatrix = Matrix.identity(inputDimension()+1, outputDimension()+1);
		setUpToDate(false);
	}

	public void preMultiply(Matrix mat) {
		this.localMatrix = localMatrix.leftMult(mat);
		setUpToDate(false);
	}

	public void postMultiply(Matrix mat) {
		this.localMatrix = localMatrix.rightMult(mat);
		setUpToDate(false);
	}

	public void update() {
		if (!isUpToDate()) {
			if (parent == null) {
				globalMatrix = localMatrix;
			} else {
				globalMatrix = localMatrix.leftMult(parent.globalMatrix());
			}
		}
		upToDate = true;
	}

	public Vector apply(Vector vect) {
		update();
		return vect.leftMult(globalMatrix);
	}

	public Vector applyToPoint(Vector vect) {
		update();
		return new Vector(vect, 1).leftMult(globalMatrix);
	}

	public Vector applyToVector(Vector vect) {
		update();
		return new Vector(vect, 0).leftMult(globalMatrix);
	}


	public Vector applyLocal(Vector vect) {
//		update();
		return vect.leftMult(localMatrix);
	}

	public Vector applyLocalToPoint(Vector vect) {
//		update();
		return new Vector(vect, 1).leftMult(localMatrix);
	}

	public Vector applyLocalToVector(Vector vect) {
//		update();
		return new Vector(vect, 0).leftMult(localMatrix);
	}

	public Rectangle applyToRectangle(Rectangle rect) {
		return new Rectangle(applyToPoint(rect.pos), applyToVector(rect.diag));
	}

	public Rectangle applyLocalToRectangle(Rectangle rect) {
		return new Rectangle(applyLocalToPoint(rect.pos), applyLocalToVector(rect.diag));
	}
}
