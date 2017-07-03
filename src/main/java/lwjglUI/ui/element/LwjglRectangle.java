package lwjglUI.ui.element;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;


import lwjglUI.buffer.LwjglBufferException;
import lwjglUI.buffer.LwjglIndexBuffer;
import lwjglUI.buffer.LwjglVertexBuffer;
import lwjglUI.shaderProgram.LwjglProgram;
import lwjglUI.shaderProgram.LwjglProgramPreset;
import lwjglUI.ui.LwjglObject;
import lwjglUI.vertexArray.LwjglVertexArray;
import ui.element.shape2D.UIRectangle;
import util.math.Vector;

public class LwjglRectangle extends UIRectangle implements LwjglObject {
	protected LwjglVertexBuffer vertexPositionBuffer = null;
	protected LwjglVertexBuffer vertexColorBuffer = null;
	protected LwjglIndexBuffer indexBuffer = null;
	protected LwjglVertexArray vertexArray = null;
	private boolean init = false;

	@Override
	public boolean supportsProgram(LwjglProgram program) {
		if (program.name.equals(LwjglProgramPreset.CRAFT_COLORED_VERTEX2D.name)) {
			return true;
		}
		return false;
	}

	public LwjglRectangle(Vector dimension, Vector color) {
		super(dimension, color);
	}

	public void initBuffers(LwjglProgram program) {
		try {
			if (init) {
				return;
			}

			this.vertexColorBuffer = new LwjglVertexBuffer(GL_STATIC_DRAW);
			this.vertexPositionBuffer = new LwjglVertexBuffer(GL_STATIC_DRAW);
			this.indexBuffer = new LwjglIndexBuffer(GL_STATIC_DRAW);
			this.vertexArray = new LwjglVertexArray();

			float[] positions = new float[4*2];
			float[] colors = new float[4*4];
			short[] indices = {0, 1, 2, 3};

			Vector a = shape.pos;
			Vector b = shape.pos.add(shape.diag);

			for (int i = 0; i < 4; i++) {
				colors[4*i + 0] = (float)color.x();
				colors[4*i + 1] = (float)color.y();
				colors[4*i + 2] = (float)color.z();
				colors[4*i + 3] = (float)1.;
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

			init = true;

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void draw(LwjglProgram program) {
		initBuffers(program);

		vertexArray.bind();
		this.indexBuffer.bind();

		glDrawElements(GL_TRIANGLE_STRIP, 4, GL_UNSIGNED_SHORT, 0);

		this.indexBuffer.unbind();
		vertexArray.unbind();
	}
}
