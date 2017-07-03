package lwjglUI.ui.topObjects;


import lwjglUI.shaderProgram.LwjglProgram;
import lwjglUI.ui.LwjglObject;
import lwjglUI.ui.element.LwjglRectangle;
import ui.topContainers.UILayer;
import util.math.shape.shape2d.Rectangle;

public class LwjglLayer extends UILayer<LwjglObject> {

	public void draw(LwjglProgram program) {
		for (LwjglObject obj : elements) {
			obj.draw(program);
		}
	}
}
