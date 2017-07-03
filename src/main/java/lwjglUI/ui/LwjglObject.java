package lwjglUI.ui;

import lwjglUI.shaderProgram.LwjglProgram;
import ui.UIObject;
import util.math.shape.shape2d.Rectangle;

/**
 * Created by esia on 19/06/17.
 */
public interface LwjglObject {
	public abstract void draw(LwjglProgram program);

	public abstract boolean supportsProgram(LwjglProgram program);
}
