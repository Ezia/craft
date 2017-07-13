package view.ui.element.shape2D;

import util.math.Vector;
import util.shape.Polygon;
import util.shape.Rectangle;
import util.shape.Shape;
import view.ui.UIElement;

/**
 * Created by esia on 13/07/17.
 */
public class UIPolygon extends UIShape {

	public UIPolygon(Polygon polygon) {
		super(polygon);
	}

	public Polygon getPolygon() {
		return (Polygon)getShape();
	}
}
