package util.lwjglUI.buffer;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glGenBuffers;

/**
 * Created by esia on 13/06/17.
 */
public class LwjglVertexBuffer extends LwjglBuffer {

	public LwjglVertexBuffer(int usage) throws LwjglBufferException {
		super(GL_ARRAY_BUFFER, usage);
	}
}
