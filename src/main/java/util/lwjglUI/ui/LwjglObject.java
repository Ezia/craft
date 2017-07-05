package util.lwjglUI.ui;

import util.lwjglUI.shaderProgram.LwjglProgram;
import util.lwjglUI.shaderProgram.LwjglProgramException;
import util.lwjglUI.vertexArray.LwjglVertexArrayException;

/**
 * Created by esia on 19/06/17.
 */
public interface LwjglObject {
	public abstract void draw(LwjglProgram program);

}
