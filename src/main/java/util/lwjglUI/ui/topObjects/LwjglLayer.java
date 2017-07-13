package util.lwjglUI.ui.topObjects;


import util.lwjglUI.shaderProgram.LwjglProgramException;
import util.lwjglUI.ui.LwjglElement;
import util.ui.topContainers.UILayer;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;

public class LwjglLayer extends UILayer<LwjglElement> {

	public void draw(LwjglWindow window) throws LwjglProgramException {
		for (int i = 0; i < this.objectNbr(); i++) {
			get(i).draw(window);
		}
	}
}
