package view.lwjglUi.ui.drawable;

import util.math.Matrix;
import util.shape.Rectangle;
import view.lwjglUi.ui.topObjects.LwjglWindow;
import view.ui.drawable.UIDrawable;

/**
 * Created by esia on 16/07/17.
 */
public abstract class LwjglDrawable<T extends UIDrawable> implements UIDrawable {

	protected final T drawable;

	public LwjglDrawable(T drawable) {
		this.drawable = drawable;
	}

	public T getDrawable() {
		return drawable;
	}

	@Override
	public Rectangle getBoundingBox() {
		return drawable.getBoundingBox();
	}

	public abstract void draw(LwjglWindow window, Matrix transform);

}
