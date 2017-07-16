package view.lwjglUi.ui.drawable;

import util.math.Matrix;
import util.math.Vector;
import util.shape.Polygon;
import util.shape.PolygonalChain;
import util.shape.Rectangle;
import view.lwjglUi.buffer.LwjglIndexBuffer;
import view.lwjglUi.buffer.LwjglVertexBuffer;
import view.lwjglUi.shaderProgram.LwjglProgram;
import view.lwjglUi.shaderProgram.LwjglProgramPreset;
import view.lwjglUi.ui.topObjects.LwjglWindow;
import view.lwjglUi.vertexArray.LwjglVertexArray;
import view.lwjglUi.vertexArray.LwjglVertexArrayException;
import view.ui.element.UIColoredPolygon;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by esia on 16/07/17.
 */
public class LwjglUniformColorPolygon implements LwjglDrawable {

	protected LwjglVertexBuffer vertexPositionBuffer = null;
	protected LwjglIndexBuffer indexBuffer = null;
	protected int indexNbr = 0;
	protected LwjglVertexArray vertexArray = null;

	private boolean init = false;

	private Polygon polygon;
	private Vector color;

	public LwjglUniformColorPolygon(Polygon poly, Vector color) {
		this.polygon = poly;
		this.color = color;
	}

	private void updateBuffers(LwjglProgram program) {
		if (!init) {
			try {
				PolygonalChain chain = polygon.getTriangleChain();

				this.vertexPositionBuffer = new LwjglVertexBuffer(GL_STATIC_DRAW);
				float[] positions = chain.getFloatPointArray();
				this.vertexPositionBuffer.set(positions);

				this.indexBuffer = new LwjglIndexBuffer(GL_STATIC_DRAW);
				int[] indices = chain.getIndexArray();
				this.indexBuffer.set(indices);
				indexNbr = indices.length;

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
	public void draw(LwjglWindow window, Matrix transform) {
		LwjglProgram program = window.getProgram(LwjglProgramPreset.CRAFT_COLORED_VERTEX2D);
		program.use();

		updateBuffers(program);
		updateVertexArray(program);

		vertexArray.bind();
		indexBuffer.bind();

		int colorPos = glGetUniformLocation(program.get(), "color");
		glUniform4fv(colorPos, color.getFloatArray());

		int model = glGetUniformLocation(program.get(), "model");
		glUniformMatrix3fv(model, false, transform.getFloatColumnArray());

		glDrawElements(GL_TRIANGLE_STRIP,
				indexNbr,
				GL_UNSIGNED_INT, 0);

		this.indexBuffer.unbind();
		vertexArray.unbind();

		program.unuse();
	}

	@Override
	public double width() {
		return polygon.getBoundingBox().diag.x();
	}

	@Override
	public double height() {
		return polygon.getBoundingBox().diag.y();
	}

	@Override
	public Rectangle getBoundingBox() {
		return polygon.getBoundingBox();
	}
}
