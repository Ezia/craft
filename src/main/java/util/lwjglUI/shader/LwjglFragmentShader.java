package util.lwjglUI.shader;


import static org.lwjgl.opengl.ARBFragmentShader.*;

public class LwjglFragmentShader extends LwjglShader {

	public LwjglFragmentShader(String file) throws LwjglShaderException {
		super(file, GL_FRAGMENT_SHADER_ARB);
	}
}
