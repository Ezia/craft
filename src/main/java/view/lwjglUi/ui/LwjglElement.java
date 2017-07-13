package view.lwjglUi.ui;

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
	public void setParent(UIContainer parent) {
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

	public UIElement getUIObject() {
		return ui;
	}

	public abstract void draw(LwjglWindow window);
}
