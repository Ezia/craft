package view.lwjglUi.shader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.ARBShaderObjects.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class LwjglShader {
	static final String shaderDir = "src/main/resources";

	private int shader;
	private String file;
	private boolean loaded;
	private boolean compiled;

	public LwjglShader(String file, int type) throws LwjglShaderException {
		this.file = file;
		loaded = false;
		compiled = false;
		this.shader = glCreateShader(type);
		if(shader == 0) {
			throw new LwjglShaderException("Could not allocate memory for shaderProgram.");
		}
	}

	public int get() {
		return shader;
	}

	public void load() throws LwjglShaderException {
		try {
			compiled = false;
			glShaderSourceARB(shader, loadSource());
			loaded = true;
		} catch (IOException e) {
			loaded = false;
			throw new LwjglShaderException("Shader loading exception on file " + file);
		}
	}


	public void compile() throws LwjglShaderException {
		assert(loaded);
		glCompileShaderARB(shader);
		if (glGetObjectParameteriARB(shader, GL_OBJECT_COMPILE_STATUS_ARB) == GL_FALSE) {
			throw new LwjglShaderException("Shader compilation exception on file " + file + " :\n" + glGetShaderInfoLog(shader));
		}
		compiled = true;
	}

	public boolean isCompiled() {
		return compiled;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void delete() {
		glDeleteObjectARB(shader);
	}

	private StringBuffer loadSource() throws IOException {
		BufferedReader lineReader = null;
		FileReader fileReader = null;
		StringBuffer buffer = new StringBuffer();

		try {
			fileReader = new FileReader(shaderDir + "/" + file);
			lineReader = new BufferedReader(fileReader);
			String currLine;
			while ((currLine = lineReader.readLine()) != null) {
				buffer.append(currLine + "\n");
			}
		} finally {
			try {
				if (lineReader != null) {
					lineReader.close();
				}
			} finally {
				if (fileReader != null) {
					fileReader.close();
				}
			}
		}

		return buffer;
	}
}
