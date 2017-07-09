package util.math;

import java.util.LinkedList;
import java.util.TreeSet;

public class Transform {

	///// FIELDS /////

	/**
	 * Left multiplication : V * M
	 */
	private Matrix globalMatrix;
	private Matrix localMatrix;
	private Transform parent = null;
	private LinkedList<Transform> children = new LinkedList<>();
	private boolean upToDate = true;

	///// CONSTRUCTORS /////

	/**
	 *
	 * @param dim1 Dimension of the input objects
	 * @param dim2 Dimension of the transformed objects
	 */
	public Transform(int inputDim, int outputDim) {
		localMatrix = Matrix.identity(inputDim+1, outputDim+1);
		globalMatrix = localMatrix;
	}

	///// ACCESSORS /////

	public int inputDimension() {
		return localMatrix.lineNbr() - 1;
	}

	public int outputDimension() {
		return localMatrix.columnNbr() - 1;
	}

	public void setParent(Transform parent) {
		this.parent.removeChild(this);
		parent.addChild(this);
	}

	public void addChild(Transform transform) {
		assert(!children.contains(transform) && transform != null && outputDimension() == transform.inputDimension());
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

	/**
	 *
	 * @return true if globalMatrix has been updated;
	 */
	public void update() {
		if (!isUpToDate()) {
			globalMatrix = localMatrix.leftMult(parent.globalMatrix());
		}
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
}
