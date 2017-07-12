package util.lwjglUI.ui;

import util.lwjglUI.shaderProgram.LwjglProgram;
import util.lwjglUI.shaderProgram.LwjglProgramException;
import util.lwjglUI.ui.topObjects.LwjglWindow;
import util.ui.UIObject;

/**
 * Created by esia on 19/06/17.
 */
public interface LwjglObject extends UIObject {
	public abstract void draw(LwjglWindow window);
}
