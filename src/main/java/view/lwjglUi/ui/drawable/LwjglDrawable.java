package view.lwjglUi.ui.drawable;

import util.math.Matrix;
import util.math.Vector;
import util.shape.Rectangle;
import view.lwjglUi.ui.topObjects.LwjglWindow;
import view.ui.drawable.UIDrawable;

/**
 * Created by esia on 16/07/17.
 */
public abstract class LwjglDrawable implements UIDrawable {

	protected final UIDrawable drawable;

	public LwjglDrawable(UIDrawable drawable) {
		this.drawable = drawable;
	}

	public UIDrawable getDrawable() {
		return drawable;
	}

	@Override
	public Rectangle getBoundingBox() {
		return drawable.getBoundingBox();
	}

	public abstract void draw(LwjglWindow window, Matrix transform);
}
