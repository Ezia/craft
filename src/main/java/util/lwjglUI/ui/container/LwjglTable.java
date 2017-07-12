package util.lwjglUI.ui.container;

import util.lwjglUI.shaderProgram.LwjglProgram;
import util.lwjglUI.shaderProgram.LwjglProgramException;
import util.lwjglUI.ui.LwjglObject;
import util.lwjglUI.ui.topObjects.LwjglWindow;
import util.ui.container.UITable;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;

public class LwjglTable extends UITable<LwjglObject> implements LwjglObject {

	public LwjglTable(int lineNbr, int columnNbr) {
		super(lineNbr, columnNbr);
	}

	@Override
	public void draw(LwjglWindow window) {
		update();
		for (int i = 0; i < lineNbr(); ++i)  {
			for (int j = 0; j < columnNbr(); ++j)  {
				if (get(i, j) != null) {
					get(i, j).draw(window);
				}
			}
		}
	}

}
