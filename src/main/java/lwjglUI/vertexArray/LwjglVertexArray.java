package lwjglUI.vertexArray;

import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.*;

public class LwjglVertexArray {
	private int vao;

	public LwjglVertexArray() throws LwjglVertexArrayException {
		vao = glGenVertexArrays();
		if (vao == 0) {
			throw new LwjglVertexArrayException("Could not allocate memory for VAO.");
		}
	}

	public int get() {
		return vao;
	}

	public void bind() {
		glBindVertexArray(vao);
	}

	public void unbind() {
		glBindVertexArray(vao);
	}

	public void delete() {
		glDeleteVertexArrays(vao);
	}
}
