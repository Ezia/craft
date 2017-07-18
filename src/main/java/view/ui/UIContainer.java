package view.ui;

import util.math.Vector;

/**
 * Created by esia on 18/06/17.
 */
public abstract class UIContainer extends UIElement {
	public abstract void remove(UIObject child);

	public UIContainer(boolean proportionnal) {
		super(proportionnal);
	}

	public abstract void wasDimensionned(UIObject child, Vector newDimension);
}
