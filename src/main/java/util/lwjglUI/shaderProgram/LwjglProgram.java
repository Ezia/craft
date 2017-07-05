package util.lwjglUI.shaderProgram;

import util.lwjglUI.shader.LwjglFragmentShader;
import util.lwjglUI.shader.LwjglGeometryShader;
import util.lwjglUI.shader.LwjglShaderException;
import util.lwjglUI.shader.LwjglVertexShader;

import static org.lwjgl.opengl.ARBShaderObjects.*;
import static org.lwjgl.opengl.GL11.*;


public class LwjglProgram {

	public final String name;
	private int program;
	private LwjglFragmentShader fragmentShader;
	private LwjglVertexShader vertexShader;
	private LwjglGeometryShader geometryShader;
	private boolean loaded, compiled, linked;

	public LwjglProgram(LwjglProgramPreset preset) throws LwjglShaderException, LwjglProgramException {
		this(preset.name, preset.vertexFile, preset.fragmentFile, preset.geometryFile);
	}

	public LwjglProgram(String name,
						String vertexShader,
						String fragmentShader,
						String geometryShader) throws LwjglShaderException, LwjglProgramException {
		try {
			this.name = name;
			loaded = false;
			compiled = false;
			linked = false;

			this.program = glCreateProgramObjectARB();
			if (this.program == 0) {
				throw new LwjglProgramException("Could not allocate memory for program.");
			}

			this.vertexShader = new LwjglVertexShader(vertexShader);
			this.fragmentShader = new LwjglFragmentShader(fragmentShader);
			if (!geometryShader.isEmpty()) {
				this.geometryShader = new LwjglGeometryShader(geometryShader);
			} else {
				this.geometryShader = null;
			}
			glAttachObjectARB(program, this.vertexShader.get());
			glAttachObjectARB(program, this.fragmentShader.get());

		} catch (LwjglShaderException e) {
			glDeleteObjectARB(this.program);
			if (this.vertexShader != null) {
				this.vertexShader.delete();
			}
			if (this.fragmentShader != null) {
				this.fragmentShader.delete();
			}
			if (this.geometryShader != null) {
				this.geometryShader.delete();
			}
			throw e;
		}
	}

	public void load() throws LwjglShaderException {
		compiled = false;
		linked = false;
		vertexShader.load();
		fragmentShader.load();
		if (geometryShader != null) {
			geometryShader.load();
		}
		loaded = true;
	}

	public void compile() throws LwjglShaderException {
		assert(loaded);
		linked = false;
		vertexShader.compile();
		fragmentShader.compile();
		if (geometryShader != null) {
			geometryShader.compile();
		}
		compiled = true;
	}

	public void link() throws LwjglProgramException {
		assert(compiled);
		glLinkProgramARB(program);
		if (glGetObjectParameteriARB(program, GL_OBJECT_LINK_STATUS_ARB) == GL_FALSE) {
			throw new LwjglProgramException("Program link exception :\n" + glGetInfoLogARB(program));
		}
		linked = true;
	}

	public void delete() {
		vertexShader.delete();
		fragmentShader.delete();
		if (geometryShader != null) {
			geometryShader.delete();
		}
	}

	public void use() {
		assert(linked);
		glUseProgramObjectARB(program);
	}

	public int get() {
		return program;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public boolean isCompiled() {
		return compiled;
	}

	public boolean isLinked() {
		return linked;
	}
}
