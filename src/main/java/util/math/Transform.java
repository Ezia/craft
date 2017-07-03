package util.math;

import java.util.TreeSet;

public class Transform {

	///// FIELDS /////

	/**
	 * Left multiplication : V * M
	 */
	private Matrix globalMatrix;
	private Matrix localMatrix;
	private Transform parent = null;
	private TreeSet<Transform> children = new TreeSet<>();
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

	public void addChild(Transform transform) {
		assert(transform != null && outputDimension() == transform.inputDimension());
		if (transform.parent != null) {
			transform.parent.removeChild(transform);
		}
		children.add(transform);
		transform.parent = this;
		transform.upToDate = false;
	}

	public void removeChild(Transform transform) {
		assert (transform != null);
		children.remove(transform);
		transform.parent = null;
		transform.globalMatrix = transform.localMatrix;
		transform.upToDate = true;
	}

	public boolean isUpToDate() {
		return upToDate;
	}

	public Matrix globalMatrix() {
		return globalMatrix;
	}

	public Matrix localMatrix() {
		return localMatrix;
	}

	///// OPERATIONS /////

	public void setIdentity() {
		this.localMatrix = Matrix.identity(inputDimension()+1, outputDimension()+1);
		upToDate = false;
	}

	public void preMultiply(Matrix mat) {
		this.localMatrix = localMatrix.leftMult(mat);
		upToDate = false;
	}

	public void postMultiply(Matrix mat) {
		this.localMatrix = localMatrix.rightMult(mat);
		upToDate = false;
	}

	/**
	 *
	 * @return true if globalMatrix has been updated;
	 */
	public boolean update() {
		if (parent != null) {
			if (parent.update() || !upToDate) {
				globalMatrix = localMatrix.leftMult(parent.globalMatrix);
				return true;
			}
		}
		return false;
	}

	public boolean updateChildren() {
		// TODO
		return false;
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
}
