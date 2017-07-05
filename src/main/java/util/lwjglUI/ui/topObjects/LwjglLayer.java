package util.lwjglUI.ui.topObjects;


import util.lwjglUI.shaderProgram.LwjglProgram;
import util.lwjglUI.ui.LwjglObject;
import util.ui.topContainers.UILayer;

public class LwjglLayer extends UILayer<LwjglObject> {

	public void draw(LwjglProgram program) {
		for (LwjglObject obj : elements) {
			obj.draw(program);
		}
	}
}
