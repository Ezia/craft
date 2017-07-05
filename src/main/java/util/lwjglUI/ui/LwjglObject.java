package util.lwjglUI.ui;

import util.lwjglUI.shaderProgram.LwjglProgram;

/**
 * Created by esia on 19/06/17.
 */
public interface LwjglObject {
	public abstract void draw(LwjglProgram program);

	public abstract boolean supportsProgram(LwjglProgram program);
}
