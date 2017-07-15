package view.lwjglUi.ui.element;

import org.lwjgl.opengl.GL;
import util.shape.Polygon;
import util.shape.PolygonalChain;
import view.lwjglUi.buffer.LwjglIndexBuffer;
import view.lwjglUi.buffer.LwjglTexture2D;
import view.lwjglUi.buffer.LwjglVertexBuffer;
import view.lwjglUi.shaderProgram.LwjglProgram;
import view.lwjglUi.shaderProgram.LwjglProgramPreset;
import view.lwjglUi.ui.LwjglElement;
import view.lwjglUi.ui.topObjects.LwjglWindow;
import view.lwjglUi.vertexArray.LwjglVertexArray;
import view.lwjglUi.vertexArray.LwjglVertexArrayException;
import view.ui.Image;
import view.ui.UIElement;
import view.ui.element.UITexturedPolygon;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL13.*;

/**
 * Created by esia on 14/07/17.
 */
public class LwjglTexturedPolygon extends LwjglElement {
	protected LwjglTexture2D texture = null;
	protected LwjglVertexBuffer vertexPositionBuffer = null;
	protected LwjglVertexBuffer vertexTexCoordBuffer = null;
	protected LwjglIndexBuffer indexBuffer = null;
	protected int indexNbr = 0;
	protected LwjglVertexArray vertexArray = null;


	private boolean init = false;

	public LwjglTexturedPolygon(Polygon polygon, Image image) {
		super(new UITexturedPolygon(polygon, image));
	}

	public UITexturedPolygon getUITexturedPolygon() {
		return (UITexturedPolygon)ui;
	}

	private void updateBuffers(LwjglProgram program) {
		if (!init) {
			try {
				PolygonalChain chain = getUITexturedPolygon().getPolygon().getTriangleChain();

				this.vertexPositionBuffer = new LwjglVertexBuffer(GL_STATIC_DRAW);
				float[] positions = chain.getFloatPointArray();
				this.vertexPositionBuffer.set(positions);

				this.vertexTexCoordBuffer = new LwjglVertexBuffer(GL_STATIC_DRAW);
				float[] texCoords = {1, 0, 0, 0, 1, 1, 0, 1};
				this.vertexTexCoordBuffer.set(texCoords);

				this.indexBuffer = new LwjglIndexBuffer(GL_STATIC_DRAW);
				int[] indices = chain.getIndexArray();
				this.indexBuffer.set(indices);
				indexNbr = indices.length;

				this.texture = new LwjglTexture2D();
				this.texture.set(getUITexturedPolygon().getTexture());

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

			int texAttrib = glGetAttribLocation(program.get(), "tex");
			glEnableVertexAttribArray(texAttrib);
			this.vertexTexCoordBuffer.bind();
			glVertexAttribPointer(texAttrib, 2, GL_FLOAT, false, 0, 0);
			this.vertexTexCoordBuffer.unbind();

			vertexArray.unbind();
		} catch (LwjglVertexArrayException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void draw(LwjglWindow window) {
		// TODO
		LwjglProgram program = window.getProgram(LwjglProgramPreset.CRAFT_TEXTURED_VERTEX2D);
		program.use();

		updateBuffers(program);
		updateVertexArray(program);

		vertexArray.bind();
		indexBuffer.bind();

//		int model = glGetUniformLocation(program.get(), "texture");
//		glUniformMatrix3fv(model, false, getUITexturedPolygon().transform.globalMatrix().getFloatColumnArray());

		int model = glGetUniformLocation(program.get(), "model");
		glUniformMatrix3fv(model, false, getUITexturedPolygon().transform.globalMatrix().getFloatColumnArray());

		texture.bind();

		glDrawElements(GL_TRIANGLE_STRIP,
				indexNbr,
				GL_UNSIGNED_INT, 0);

		texture.unbind();
		this.indexBuffer.unbind();
		vertexArray.unbind();

		program.unuse();
	}

}
