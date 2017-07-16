package view.lwjglUi.ui.drawable;

import util.math.Matrix;
import view.lwjglUi.ui.topObjects.LwjglWindow;
import view.ui.drawable.Drawable;

/**
 * Created by esia on 16/07/17.
 */
public interface LwjglDrawable extends Drawable {

	void draw(LwjglWindow window, Matrix transform);

}
