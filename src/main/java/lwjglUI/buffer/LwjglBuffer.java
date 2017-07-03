package lwjglUI.buffer;

import static org.lwjgl.opengl.GL15.*;

public class LwjglBuffer {
	public static final int OBJECT_SHELL_SIZE = 8;
	public static final int OBJECT_REF_SIZE = 4;
	public static final int LONG_SIZE = 8;
	public static final int INT_SIZE = 4;
	public static final int SHORT_SIZE = 2;
	public static final int CHAR_SIZE = 2;
	public static final int BYTE_SIZE = 1;
	public static final int BOOLEAN_SIZE = 1;
	public static final int DOUBLE_SIZE = 8;
	public static final int FLOAT_SIZE = 4;

	private int buffer;
	private int type;
	private int usage;

	public LwjglBuffer(int type, int usage) throws LwjglBufferException {
		this.type = type;
		this.usage = usage;
		this.buffer = glGenBuffers();
		if (this.buffer == 0) {
			throw new LwjglBufferException("Could not allocate memory for buffer.");
		}
	}

	public int get() {
		return buffer;
	}

	/**
	 *
	 * @param dataSize in bytes
	 */
	public void set(long dataSize) {
		glBindBuffer(this.type, buffer);
		glBufferData(this.type, dataSize, this.usage);
		glBindBuffer(this.type, 0);
	}

	public void set(int[] data) {
		glBindBuffer(this.type, buffer);
		glBufferData(this.type, data, this.usage);
		glBindBuffer(this.type, 0);
	}

	public void set(float[] data) {
		glBindBuffer(this.type, buffer);
		glBufferData(this.type, data, this.usage);
		glBindBuffer(this.type, 0);
	}

	public void set(short[] data) {
		glBindBuffer(this.type, buffer);
		glBufferData(this.type, data, this.usage);
		glBindBuffer(this.type, 0);
	}

	public void set(double[] data) {
		glBindBuffer(this.type, buffer);
		glBufferData(this.type, data, this.usage);
		glBindBuffer(this.type, 0);
	}

	public void subset(long offset, int[] data) {
		glBindBuffer(this.type, buffer);
		glBufferSubData(this.type, offset, data);
		glBindBuffer(this.type, 0);
	}

	public void subset(long offset, short[] data) {
		glBindBuffer(this.type, buffer);
		glBufferSubData(this.type, offset, data);
		glBindBuffer(this.type, 0);
	}

	public void subset(long offset, float[] data) {
		glBindBuffer(this.type, buffer);
		glBufferSubData(this.type, offset, data);
		glBindBuffer(this.type, 0);
	}

	public void subset(long offset, double[] data) {
		glBindBuffer(this.type, buffer);
		glBufferSubData(this.type, offset, data);
		glBindBuffer(this.type, 0);
	}

	public void bind() {
		glBindBuffer(this.type, buffer);
	}

	public void unbind() {
		glBindBuffer(this.type, 0);
	}

	public void delete() {
		glDeleteBuffers(this.buffer);
	}
}
