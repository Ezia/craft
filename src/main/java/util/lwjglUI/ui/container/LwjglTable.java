package util.lwjglUI.ui.container;

import util.lwjglUI.shaderProgram.LwjglProgram;
import util.lwjglUI.shaderProgram.LwjglProgramException;
import util.lwjglUI.ui.LwjglObject;
import util.math.Matrix;
import util.ui.UIObject;
import util.ui.container.UITable;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;

public class LwjglTable extends UITable<LwjglObject> implements LwjglObject {

	public LwjglTable(int lineNbr, int columnNbr) {
		super(lineNbr, columnNbr);
	}

	@Override
	public void draw(LwjglProgram program) throws LwjglProgramException {
		for (int i = 0; i < lineNbr(); ++i)  {
			for (int j = 0; j < columnNbr(); ++j)  {

				if (get(i, j) != null) {
					switch (program.preset) {
						case CRAFT_COLORED_VERTEX2D:
							if (get(i, j).supports(program)) {
								float[] tab = getMatrix(i, j).getFloatColumnArray();
								int model = glGetUniformLocation(program.get(), "model");
								glUniformMatrix3fv(model, false, getMatrix(i, j).getFloatColumnArray());

								get(i, j).draw(program);
							}
							break;
						default:
							throw new LwjglProgramException("Unknown program.");
					}

				}



			}
		}
	}

	@Override
	public boolean supports(LwjglProgram program) {
		return true;
	}
}
