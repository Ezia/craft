package view.lwjglUi.ui;

import util.math.Matrix;
import util.math.Vector;
import view.lwjglUi.ui.topObjects.LwjglWindow;
import util.math.Transform;
import util.shape.Rectangle;
import view.ui.UIContainer;
import view.ui.UIElement;
import view.ui.UIObject;

/**
 * Created by esia on 19/06/17.
 */
public abstract class LwjglElement implements UIObject {
	protected final UIElement ui;

	public LwjglElement(UIElement ui) {
		this.ui = ui;
	}

	@Override
	public UIContainer getParent() {
		return ui.getParent();
	}

	@Override
	public void setParent(UIContainer parent, Transform transform) {
		ui.setParent(parent, transform);
	}

//	@Override
//	public Rectangle getBoundingBox() {
//		return ui.getBoundingBox();
//	}

	public UIElement getUIObject() {
		return ui;
	}

	public abstract void draw(LwjglWindow window);

	@Override
	public boolean isProportionnal() {
		return false;
	}

	@Override
	public double getProportion() {
		return 0;
	}

	@Override
	public Matrix getTransformGlobalMatrix() {
		return ui.getTransformGlobalMatrix();
	}

	@Override
	public Vector getDimension() {
		return ui.getDimension();
	}

	@Override
	public void setDimension(Vector dimension) {
		ui.setDimension(dimension);
	}
}
