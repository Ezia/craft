package lwjglUI.shader;


import static org.lwjgl.opengl.ARBVertexShader.*;

public class LwjglVertexShader extends LwjglShader {

	public LwjglVertexShader(String file) throws LwjglShaderException {
		super(file, GL_VERTEX_SHADER_ARB);
	}
}
