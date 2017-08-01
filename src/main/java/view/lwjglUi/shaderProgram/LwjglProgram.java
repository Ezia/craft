package view.lwjglUi.shaderProgram;

import view.lwjglUi.shader.LwjglFragmentShader;
import view.lwjglUi.shader.LwjglGeometryShader;
import view.lwjglUi.shader.LwjglShaderException;
import view.lwjglUi.shader.LwjglVertexShader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;


public class LwjglProgram {

	public final LwjglProgramPreset preset;
	private int program;
	private LwjglFragmentShader fragmentShader;
	private LwjglVertexShader vertexShader;
	private LwjglGeometryShader geometryShader;
	private boolean loaded, compiled, linked;

	public LwjglProgram(LwjglProgramPreset preset) throws LwjglShaderException, LwjglProgramException {
		create(preset.name, preset.vertexFile, preset.fragmentFile, preset.geometryFile);
		this.preset = preset;
	}

	private void create(String name,
						String vertexShader,
						String fragmentShader,
						String geometryShader) throws LwjglShaderException, LwjglProgramException {
		try {
			loaded = false;
			compiled = false;
			linked = false;

			this.program = glCreateProgram();
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
			glAttachShader(program, this.vertexShader.get());
			glAttachShader(program, this.fragmentShader.get());

		} catch (LwjglShaderException e) {
			glDeleteProgram(this.program);
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
		glLinkProgram(program);
		if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
			throw new LwjglProgramException("Program link exception :\n" + glGetProgramInfoLog(program));
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
		glUseProgram(program);
	}

	public void unuse() {
		glUseProgram(0);
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
