package view.lwjglUi.shader;

import static org.lwjgl.opengl.ARBGeometryShader4.*;

public class LwjglGeometryShader extends LwjglShader {

	public LwjglGeometryShader(String file) throws LwjglShaderException {
		super(file, GL_GEOMETRY_SHADER_ARB);
	}
}
