package util.lwjglUI.ui;

import util.lwjglUI.shaderProgram.LwjglProgram;
import util.lwjglUI.shaderProgram.LwjglProgramException;
import util.lwjglUI.vertexArray.LwjglVertexArrayException;
import util.ui.UIObject;

/**
 * Created by esia on 19/06/17.
 */
public interface LwjglObject extends UIObject {
	public abstract void draw(LwjglProgram program) throws LwjglProgramException;
	public abstract boolean supports(LwjglProgram program);
}
