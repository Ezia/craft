package view.ui.drawable;

import util.shape.Rectangle;

/**
 * Created by esia on 16/07/17.
 */
public interface Drawable {

	double width();

	double height();

	Rectangle getBoundingBox();
}
