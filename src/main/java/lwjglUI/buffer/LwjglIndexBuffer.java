package lwjglUI.buffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class LwjglIndexBuffer extends LwjglBuffer {
	public LwjglIndexBuffer(int usage) throws LwjglBufferException {
		super(GL_ELEMENT_ARRAY_BUFFER, usage);
	}
}
