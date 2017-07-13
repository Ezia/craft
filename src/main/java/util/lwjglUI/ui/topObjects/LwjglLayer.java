package util.lwjglUI.ui.topObjects;


import util.lwjglUI.shaderProgram.LwjglProgramException;
import util.lwjglUI.ui.LwjglElement;
import util.ui.topContainers.UILayer;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;

public class LwjglLayer extends UILayer<LwjglElement> {

	public void draw(LwjglWindow window) throws LwjglProgramException {
//		switch (program.preset) {
//			case CRAFT_COLORED_VERTEX2D:
//				for (int i = 0; i < this.objectNbr(); i++) {
//					if (get(i).supports(program)) {
//						get(i).draw(program);
//					}
//				}
//				break;
//			default:
//				throw new LwjglProgramException("Unknown program.");
//		}
		for (int i = 0; i < this.objectNbr(); i++) {
			get(i).draw(window);
		}
	}
}
