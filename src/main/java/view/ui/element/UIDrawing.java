package view.ui.element;

import util.math.Matrix;
import util.math.Transform;
import util.math.Vector;
import util.shape.Rectangle;
import view.ui.UIElement;
import view.ui.drawable.UIDrawable;

/**
 * Created by esia on 16/07/17.
 */
public class UIDrawing<T extends UIDrawable> extends UIElement {

	protected final T drawable;

	public UIDrawing(boolean proportionnal, T drawable) {
		super(proportionnal);
		this.drawable = drawable;
		this.transform.setMatrix(Matrix.translation(drawable.getBoundingBox().pos.times(-1.)));
	}

	public T getDrawable() {
		return drawable;
	}

//	@Override
//	public Rectangle getBoundingBox() {
//		return this.transform.applyLocalToRectangle(drawable.getBoundingBox());
//	}


	@Override
	public void setDimension(Vector dimension) {
		Vector newDim = dimension.clamp(getMinDimension(), getMaxDimension());
		transform.postMultiply(Matrix.homothety2D(newDim.wiseTimes(getDimension().inv())));
		if (this.getParent() != null) {
			this.getParent().wasDimensionned(this, newDim);
		}
	}

	@Override
	public Vector getDimension() {
		return this.transform.applyLocalToVector(drawable.getBoundingBox().diag);
	}
}
