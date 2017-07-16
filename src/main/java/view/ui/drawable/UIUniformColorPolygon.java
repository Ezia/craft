package view.ui.drawable;

import util.math.Color;
import util.shape.Polygon;

/**
 * Created by esia on 16/07/17.
 */
public class UIUniformColorPolygon extends UIShape<Polygon> {
	protected final Color color;

	public UIUniformColorPolygon(Polygon shape, Color color) {
		super(shape);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}
