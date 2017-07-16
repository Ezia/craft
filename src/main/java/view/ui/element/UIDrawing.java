package view.ui.element;

import util.shape.Rectangle;
import view.ui.UIElement;
import view.ui.drawable.Drawable;

/**
 * Created by esia on 16/07/17.
 */
public class UIDrawing<T extends Drawable> extends UIElement {
	protected final T drawable;

	public UIDrawing(T drawable) {
		this.drawable = drawable;
	}

	public T getDrawable() {
		return drawable;
	}

	@Override
	public double width() {
		return drawable.width();
	}

	@Override
	public double height() {
		return drawable.height();
	}

	@Override
	public Rectangle getLocalBoundingBox() {
		return this.transform.applyLocalToRectangle(drawable.getBoundingBox());
	}
}
