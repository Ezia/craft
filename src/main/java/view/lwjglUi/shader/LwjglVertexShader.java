package view.lwjglUi.shader;


import static org.lwjgl.opengl.GL20.*;

public class LwjglVertexShader extends LwjglShader {

	public LwjglVertexShader(String file) throws LwjglShaderException {
		super(file, GL_VERTEX_SHADER);
	}
}
