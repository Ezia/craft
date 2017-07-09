package util.lwjglUI.ui.element;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;


import util.lwjglUI.buffer.LwjglIndexBuffer;
import util.lwjglUI.buffer.LwjglVertexBuffer;
import util.lwjglUI.shaderProgram.LwjglProgram;
import util.lwjglUI.shaderProgram.LwjglProgramException;
import util.lwjglUI.shaderProgram.LwjglProgramPreset;
import util.lwjglUI.ui.LwjglObject;
import util.lwjglUI.vertexArray.LwjglVertexArray;
import util.lwjglUI.vertexArray.LwjglVertexArrayException;
import util.ui.element.shape2D.UIRectangle;
import util.math.Vector;

import java.util.TreeMap;

public class LwjglRectangle extends UIRectangle implements LwjglObject {
	protected LwjglVertexBuffer vertexPositionBuffer = null;
	protected LwjglVertexBuffer vertexColorBuffer = null;
	protected LwjglIndexBuffer indexBuffer = null;

	protected TreeMap<LwjglProgramPreset, LwjglVertexArray> vertexArrays = new TreeMap<>();

	private boolean init = false;

	public LwjglRectangle(Vector dimension, Vector color) {
		super(dimension, color);
	}

	private void updateBuffers(LwjglProgram program) {
		if (!init) {
			try {
				this.vertexColorBuffer = new LwjglVertexBuffer(GL_STATIC_DRAW);
				this.vertexPositionBuffer = new LwjglVertexBuffer(GL_STATIC_DRAW);
				this.indexBuffer = new LwjglIndexBuffer(GL_STATIC_DRAW);

				float[] positions = new float[4*2];
				float[] colors = new float[4*4];
				short[] indices = {0, 2, 1, 3};

				Vector a = shape.pos;
				Vector b = shape.pos.add(shape.diag);

				for (int i = 0; i < 4; i++) {
					colors[4*i + 0] = (float)color.x();
					colors[4*i + 1] = (float)color.y();
					colors[4*i + 2] = (float)color.z();
					colors[4*i + 3] = (float)color.w();
				}

				positions[2*0+0] = (float)a.x();
				positions[2*0+1] = (float)a.y();

				positions[2*1+0] = (float)b.x();
				positions[2*1+1] = (float)a.y();

				positions[2*2+0] = (float)a.x();
				positions[2*2+1] = (float)b.y();

				positions[2*3+0] = (float)b.x();
				positions[2*3+1] = (float)b.y();

				this.vertexPositionBuffer.set(positions);
				this.vertexColorBuffer.set(colors);
				this.indexBuffer.set(indices);

				init = true;

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public boolean supports(LwjglProgram program) {
		switch (program.preset) {
			case CRAFT_COLORED_VERTEX2D:
				return true;
			default:
				return false;
		}
	}


	private void updateVertexArray(LwjglProgram program) throws LwjglProgramException {
		if (!vertexArrays.containsKey(program.preset)) {
			switch (program.preset) {
				case CRAFT_COLORED_VERTEX2D:
					try {
						LwjglVertexArray vertexArray = new LwjglVertexArray();

						vertexArray.bind();

						int positionAttrib = glGetAttribLocation(program.get(), "position");
						int colorAttrib = glGetAttribLocation(program.get(), "color");

						glEnableVertexAttribArray(positionAttrib);
						glEnableVertexAttribArray(colorAttrib);

						this.vertexPositionBuffer.bind();
						glVertexAttribPointer(positionAttrib, 2, GL_FLOAT, false, 0, 0);

						this.vertexColorBuffer.bind();
						glVertexAttribPointer(colorAttrib, 4, GL_FLOAT, false, 0, 0);

						this.vertexPositionBuffer.unbind();
						this.vertexColorBuffer.unbind();

						vertexArray.unbind();

						vertexArrays.put(program.preset, vertexArray);
					} catch (LwjglVertexArrayException e) {
						e.printStackTrace();
						System.exit(1);
					}
					break;
				default:
					throw new LwjglProgramException("Program not supported.");
			}
		}
	}

	@Override
	public void draw(LwjglProgram program) throws LwjglProgramException {
		updateBuffers(program);
		updateVertexArray(program);

		LwjglVertexArray vertexArray = vertexArrays.get(program.preset);
		vertexArray.bind();
		this.indexBuffer.bind();

		int model = glGetUniformLocation(program.get(), "model");
		glUniformMatrix3fv(model, false, transform.globalMatrix().getFloatColumnArray());

		glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, 0);

		this.indexBuffer.unbind();
		vertexArray.unbind();
	}
}
