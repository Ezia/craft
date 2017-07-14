package view.ui.element;

import util.shape.Polygon;

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
