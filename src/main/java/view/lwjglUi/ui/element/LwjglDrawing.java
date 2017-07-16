package view.lwjglUi.ui.element;

import view.lwjglUi.ui.LwjglElement;
import view.lwjglUi.ui.drawable.LwjglDrawable;
import view.lwjglUi.ui.topObjects.LwjglWindow;
import view.ui.UIElement;
import view.ui.element.UIDrawing;

/**
 * Created by esia on 16/07/17.
 */
public class LwjglDrawing extends LwjglElement {

	public LwjglDrawing(LwjglDrawable ui) {
		super(new UIDrawing<LwjglDrawable>(ui));
	}

	public UIDrawing<LwjglDrawable> getUIDrawing() {
		return (UIDrawing<LwjglDrawable>)ui;
	}

	@Override
	public void draw(LwjglWindow window) {
		getUIDrawing().getDrawable().draw(window, getUIDrawing().transform.globalMatrix());
	}
}
