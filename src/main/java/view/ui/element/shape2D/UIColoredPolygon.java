package view.ui.element.shape2D;

import util.math.Vector;
import util.shape.Polygon;

/**
 * Created by esia on 13/07/17.
 */
public class UIColoredPolygon extends UIPolygon {

	public final Vector color;

	public UIColoredPolygon(Polygon polygon, Vector color) {
		super(polygon);
		this.color = color;
	}
}
