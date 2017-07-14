package view.lwjglUi.buffer;

import view.ui.Image;
import view.ui.ImageException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

public class LwjglTexture2D extends LwjglTexture {

	public LwjglTexture2D() throws LwjglBufferException {
		super(GL_TEXTURE_2D);
	}

	public void set(Image image) throws ImageException {
		glTexImage2D(target, 0, GL_RGBA,
				image.width(),
				image.height(),
				0, GL_RGBA,
				GL_UNSIGNED_SHORT,
				image.getData(Image.PixelFormat.RGBA));
	}
}
