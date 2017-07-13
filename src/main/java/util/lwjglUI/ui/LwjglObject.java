package util.lwjglUI.ui;

import util.lwjglUI.shaderProgram.LwjglProgram;
import util.lwjglUI.shaderProgram.LwjglProgramException;
import util.lwjglUI.ui.topObjects.LwjglWindow;
import util.math.Transform;
import util.math.shape.shape2d.Rectangle;
import util.ui.UIObject;
import util.ui.UIParent;

/**
 * Created by esia on 19/06/17.
 */
public abstract class LwjglObject implements UIObject {
	protected final UIObject ui;

	public LwjglObject(UIObject ui) {
		this.ui = ui;
	}

	@Override
	public UIParent getParent() {
		return ui.getParent();
	}

	@Override
	public void setParent(UIParent parent) {
		ui.setParent(parent);
	}

	@Override
	public void setTransformParent(Transform parent) {
		ui.setTransformParent(parent);
	}

	@Override
	public double width() {
		return ui.width();
	}

	@Override
	public double height() {
		return ui.height();
	}

	@Override
	public Rectangle getLocalBoundingBox() {
		return ui.getLocalBoundingBox();
	}

	@Override
	public Rectangle getGlobalBoundingBox() {
		return ui.getGlobalBoundingBox();
	}

	public UIObject getUIObject() {
		return ui;
	}

	public abstract void draw(LwjglWindow window);
}
