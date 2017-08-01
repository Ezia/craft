package view.lwjglUi.shader;

import static org.lwjgl.opengl.GL32.*;

public class LwjglGeometryShader extends LwjglShader {

	public LwjglGeometryShader(String file) throws LwjglShaderException {
		super(file, GL_GEOMETRY_SHADER);
	}
}
