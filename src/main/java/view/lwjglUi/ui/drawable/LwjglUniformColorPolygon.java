package view.lwjglUi.ui.drawable;

import util.math.Color;
import util.math.Matrix;
import util.shape.Polygon;
import util.shape.PolygonalChain;
import view.lwjglUi.buffer.LwjglIndexBuffer;
import view.lwjglUi.buffer.LwjglVertexBuffer;
import view.lwjglUi.shaderProgram.LwjglProgram;
import view.lwjglUi.shaderProgram.LwjglProgramPreset;
import view.lwjglUi.ui.topObjects.LwjglWindow;
import view.lwjglUi.vertexArray.LwjglVertexArray;
import view.ui.drawable.UIUniformColorPolygon;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;

public class LwjglUniformColorPolygon extends LwjglDrawable {

	private LwjglVertexBuffer vertexPositionBuffer = null;
	private LwjglIndexBuffer indexBuffer = null;
	private LwjglVertexArray vertexArray = null;
	private int indexNbr = 0;

	private boolean upToDate = false;

	public LwjglUniformColorPolygon(Polygon polygon, Color color) {
		super(new UIUniformColorPolygon(polygon, color));
	}

	public UIUniformColorPolygon getUIUniformColorPolygon() {
		return (UIUniformColorPolygon)this.drawable;
	}

	private void update(LwjglProgram program) {
		if (!upToDate) {
			try {
				PolygonalChain chain = this.getUIUniformColorPolygon().getShape().getTriangleChain();

				// vertices positions
				this.vertexPositionBuffer = new LwjglVertexBuffer(GL_STATIC_DRAW);
				float[] positions = chain.getFloatPointArray();
				this.vertexPositionBuffer.set(positions);

				// indices
				this.indexBuffer = new LwjglIndexBuffer(GL_STATIC_DRAW);
				int[] indices = chain.getIndexArray();
				this.indexBuffer.set(indices);
				indexNbr = indices.length;

				// vertex array
				vertexArray = new LwjglVertexArray();
				vertexArray.bind();

				int positionAttrib = glGetAttribLocation(program.get(), "position");
				glEnableVertexAttribArray(positionAttrib);
				this.vertexPositionBuffer.bind();
				glVertexAttribPointer(positionAttrib, 2, GL_FLOAT, false, 0, 0);
				this.vertexPositionBuffer.unbind();

				vertexArray.unbind();

				upToDate = true;
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	@Override
	public void draw(LwjglWindow window, Matrix transform) {
		LwjglProgram program = window.getProgram(LwjglProgramPreset.CRAFT_COLORED_VERTEX2D);
		program.use();

		update(program);

		vertexArray.bind();
		indexBuffer.bind();

		int colorPos = glGetUniformLocation(program.get(), "color");
		glUniform4fv(colorPos, this.getUIUniformColorPolygon().getColor().vector().getFloatArray());

		int model = glGetUniformLocation(program.get(), "model");
		glUniformMatrix3fv(model, false, transform.getFloatColumnArray());

		glDrawElements(GL_TRIANGLE_STRIP,
				indexNbr,
				GL_UNSIGNED_INT, 0);

		this.indexBuffer.unbind();
		vertexArray.unbind();

		program.unuse();
	}

}
