package util.lwjglUI.ui.container;

import util.lwjglUI.shaderProgram.LwjglProgram;
import util.lwjglUI.shaderProgram.LwjglProgramException;
import util.lwjglUI.ui.LwjglObject;
import util.lwjglUI.ui.topObjects.LwjglWindow;
import util.ui.container.UITable;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;

public class LwjglTable extends LwjglObject {

	public LwjglTable(int lineNbr, int columnNbr) {
		super(new UITable<LwjglObject>(lineNbr, columnNbr));
	}

	public UITable<LwjglObject> getUITable() {
		return (UITable<LwjglObject>)ui;
	}

	@Override
	public void draw(LwjglWindow window) {
		getUITable().update();
		for (int i = 0; i < getUITable().lineNbr(); ++i)  {
			for (int j = 0; j < getUITable().columnNbr(); ++j)  {
				if (getUITable().get(i, j) != null) {
					getUITable().get(i, j).draw(window);
				}
			}
		}
	}

}
