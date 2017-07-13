package view.lwjglUi.ui.element;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;


import util.shape.Polygon;
import util.shape.PolygonalChain;
import view.lwjglUi.buffer.LwjglIndexBuffer;
import view.lwjglUi.buffer.LwjglVertexBuffer;
import view.lwjglUi.shaderProgram.LwjglProgram;
import view.lwjglUi.shaderProgram.LwjglProgramPreset;
import view.lwjglUi.ui.LwjglElement;
import view.lwjglUi.ui.topObjects.LwjglWindow;
import view.lwjglUi.vertexArray.LwjglVertexArray;
import view.lwjglUi.vertexArray.LwjglVertexArrayException;
import view.ui.element.shape2D.UIColoredPolygon;
import util.math.Vector;

public class LwjglColoredPolygon extends LwjglElement {
	protected LwjglVertexBuffer vertexPositionBuffer = null;
	protected LwjglIndexBuffer indexBuffer = null;
	protected LwjglVertexArray vertexArray = null;

	private boolean init = false;

	public LwjglColoredPolygon(Polygon poly, Vector color) {
		super(new UIColoredPolygon(poly, color));
	}

	private void updateBuffers(LwjglProgram program) {
		if (!init) {
			try {
				PolygonalChain chain = getUIColoredPolygon().getPolygon().getTriangleChain();

				this.vertexPositionBuffer = new LwjglVertexBuffer(GL_STATIC_DRAW);
				float[] positions = chain.getFloatPointArray();
				this.vertexPositionBuffer.set(positions);

				this.indexBuffer = new LwjglIndexBuffer(GL_STATIC_DRAW);
				int[] indices = chain.getIndexArray();
				this.indexBuffer.set(indices);

				init = true;
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	private void updateVertexArray(LwjglProgram program) {
		try {
			vertexArray = new LwjglVertexArray();

			vertexArray.bind();

			int positionAttrib = glGetAttribLocation(program.get(), "position");
			glEnableVertexAttribArray(positionAttrib);
			this.vertexPositionBuffer.bind();
			glVertexAttribPointer(positionAttrib, 2, GL_FLOAT, false, 0, 0);
			this.vertexPositionBuffer.unbind();

			vertexArray.unbind();
		} catch (LwjglVertexArrayException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void draw(LwjglWindow window) {
		LwjglProgram program = window.getProgram(LwjglProgramPreset.CRAFT_COLORED_VERTEX2D);
		program.use();

		updateBuffers(program);
		updateVertexArray(program);

		vertexArray.bind();
		indexBuffer.bind();

		int colorPos = glGetUniformLocation(program.get(), "color");
		glUniform4fv(colorPos, getUIColoredPolygon().color.getFloatArray());

		int model = glGetUniformLocation(program.get(), "model");
		glUniformMatrix3fv(model, false, getUIColoredPolygon().transform.globalMatrix().getFloatColumnArray());

		glDrawElements(GL_TRIANGLE_STRIP,
				getUIColoredPolygon().getPolygon().getPolygonalChain().getIndexNbr(),
				GL_UNSIGNED_INT, 0);

		this.indexBuffer.unbind();
		vertexArray.unbind();

		program.unuse();
	}

	public UIColoredPolygon getUIColoredPolygon() {
		return (UIColoredPolygon)ui;
	}

}
