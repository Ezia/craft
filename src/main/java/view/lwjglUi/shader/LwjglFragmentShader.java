package view.lwjglUi.shader;


import static org.lwjgl.opengl.GL20.*;

public class LwjglFragmentShader extends LwjglShader {

	public LwjglFragmentShader(String file) throws LwjglShaderException {
		super(file, GL_FRAGMENT_SHADER);
	}
}
