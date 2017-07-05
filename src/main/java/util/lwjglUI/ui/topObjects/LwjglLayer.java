package util.lwjglUI.ui.topObjects;


import util.lwjglUI.shaderProgram.LwjglProgram;
import util.lwjglUI.shaderProgram.LwjglProgramException;
import util.lwjglUI.ui.LwjglObject;
import util.lwjglUI.vertexArray.LwjglVertexArrayException;
import util.math.Matrix;
import util.math.Vector;
import util.math.shape.shape2d.Rectangle;
import util.ui.topContainers.UILayer;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;

public class LwjglLayer extends UILayer<LwjglObject> {

	public void draw(LwjglProgram program) throws LwjglProgramException {

		switch (program.preset) {
			case CRAFT_COLORED_VERTEX2D:
				for (int i = 0; i < this.objectNbr(); i++) {
					LwjglObject obj = get(i);
					float depth = (this.objectNbr() - i)/(this.objectNbr()) * 2 - 1;
					int depthLoc = glGetUniformLocation(program.get(), "depth");
					glUniform1f(depthLoc, depth);
					obj.draw(program);
				}

				break;
			default:
				throw new LwjglProgramException("Unknown program.");
		}


	}
}
