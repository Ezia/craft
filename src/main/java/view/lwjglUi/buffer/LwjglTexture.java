package view.lwjglUi.buffer;

import view.ui.Image;
import view.ui.ImageException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

/**
 * Created by esia on 14/07/17.
 */
public class LwjglTexture {

	protected int texture;
	protected int target;

	public LwjglTexture(int target) throws LwjglBufferException {
		this.target = target;
		this.texture = glGenTextures();
		if (this.texture == 0) {
			throw new LwjglBufferException("Could not allocate memory for texture.");
		}
	}

	public int get() {
		return this.texture;
	}

	public void bind() {
		glBindTexture(this.target, this.texture);
	}

	public void unbind() {
		glBindTexture(this.target, 0);
	}

	public void delete() {
		glDeleteTextures(this.texture);
	}

}
