package view.lwjglUi.buffer;

import view.ui.Image;
import view.ui.ImageException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

public class LwjglTexture2D extends LwjglTexture {

	public LwjglTexture2D() throws LwjglBufferException {
		super(GL_TEXTURE_2D);
	}

	public void set(Image image) throws ImageException {
		this.bind();
		glTexImage2D(target, 0, GL_RGBA,
				image.width(),
				image.height(),
				0, GL_RGBA,
				GL_FLOAT,
				image.getFloatData(Image.PixelFormat.RGBA));
		glTexParameteri(target, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(target, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S,  GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T,  GL_CLAMP_TO_BORDER);
		this.unbind();
	}
}
