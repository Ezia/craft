package util.ui;

import util.math.Transform;
import util.math.shape.shape2d.Rectangle;

public abstract class UIObject {

	public final Transform transform = new Transform(2, 2);

	abstract public Rectangle getLocalBoundingBox();

	public Rectangle getGlobalBoundingBox() {
		Rectangle localBox = getLocalBoundingBox();
		return new Rectangle(transform.applyToPoint(localBox.pos),
				transform.applyToPoint(localBox.diag));
	}
}
