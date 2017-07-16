package view.lwjglUi.ui.drawable;

import util.math.Matrix;
import util.shape.Polygon;
import util.shape.PolygonalChain;
import util.shape.Rectangle;
import view.lwjglUi.buffer.LwjglIndexBuffer;
import view.lwjglUi.buffer.LwjglTexture2D;
import view.lwjglUi.buffer.LwjglVertexBuffer;
import view.lwjglUi.shaderProgram.LwjglProgram;
import view.lwjglUi.shaderProgram.LwjglProgramPreset;
import view.lwjglUi.ui.topObjects.LwjglWindow;
import view.lwjglUi.vertexArray.LwjglVertexArray;
import view.ui.Image;
import view.ui.drawable.UIUniformTexturePolygon;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;

public class LwjglUniformTexturePolygon extends LwjglDrawable<UIUniformTexturePolygon> {

	protected LwjglTexture2D texture = null;
	protected LwjglVertexBuffer vertexPositionBuffer = null;
	protected LwjglIndexBuffer indexBuffer = null;
	protected LwjglVertexArray vertexArray = null;
	protected int indexNbr = 0;

	private boolean upToDate = false;

	public LwjglUniformTexturePolygon(Polygon polygon, Image image) {
		super(new UIUniformTexturePolygon(polygon, image));
	}

	public LwjglUniformTexturePolygon(Polygon polygon, Image image, Rectangle textureBox) {
		super(new UIUniformTexturePolygon(polygon, image, textureBox));
	}

	private void update(LwjglProgram program) {
		if (!upToDate) {
			try {
				PolygonalChain chain = drawable.getShape().getTriangleChain();

				this.vertexPositionBuffer = new LwjglVertexBuffer(GL_STATIC_DRAW);
				float[] positions = chain.getFloatPointArray();
				this.vertexPositionBuffer.set(positions);

				this.indexBuffer = new LwjglIndexBuffer(GL_STATIC_DRAW);
				int[] indices = chain.getIndexArray();
				this.indexBuffer.set(indices);
				indexNbr = indices.length;

				if (!drawable.getTexture().isLoaded()) {
					drawable.getTexture().load();
				}
				this.texture = new LwjglTexture2D();
				this.texture.set(drawable.getTexture());

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
		LwjglProgram program = window.getProgram(LwjglProgramPreset.CRAFT_TEXTURED_VERTEX2D);

		program.use();

		update(program);

		vertexArray.bind();
		indexBuffer.bind();
		texture.bind();

		int model = glGetUniformLocation(program.get(), "model");
		glUniformMatrix3fv(model, false, transform.getFloatColumnArray());

		int texProj = glGetUniformLocation(program.get(), "texCoordProj");
		glUniformMatrix3fv(texProj, false, drawable.getUvMatrix().getFloatColumnArray());

		glDrawElements(GL_TRIANGLE_STRIP,
				indexNbr,
				GL_UNSIGNED_INT, 0);

		texture.unbind();
		indexBuffer.unbind();
		vertexArray.unbind();

		program.unuse();
	}

}
