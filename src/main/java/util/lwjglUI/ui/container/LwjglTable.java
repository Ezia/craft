package util.lwjglUI.ui.container;

import util.lwjglUI.ui.LwjglElement;
import util.lwjglUI.ui.topObjects.LwjglWindow;
import util.ui.container.UITable;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;

public class LwjglTable extends LwjglElement {

	public LwjglTable(int lineNbr, int columnNbr) {
		super(new UITable<LwjglElement>(lineNbr, columnNbr));
	}

	public UITable<LwjglElement> getUITable() {
		return (UITable<LwjglElement>)ui;
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
