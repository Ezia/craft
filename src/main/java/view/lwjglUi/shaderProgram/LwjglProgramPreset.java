package view.lwjglUi.shaderProgram;

public enum LwjglProgramPreset {

	CRAFT_COLORED_VERTEX2D(
			"craft_colored_vertex",
			"renderV.glsl",
			"renderF.glsl",
			""
	);

	public final String name;
	public final String vertexFile;
	public final String fragmentFile;
	public final String geometryFile;

	private LwjglProgramPreset(String name, String vertexFile, String fragmentFile, String geometryFile) {
		this.name = name;
		this.vertexFile = vertexFile;
		this.fragmentFile = fragmentFile;
		this.geometryFile = geometryFile;
	}
}
