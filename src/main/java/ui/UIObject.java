package ui;

import util.math.Transform;
import util.math.shape.shape2d.Rectangle;

import java.lang.ref.WeakReference;

public abstract class UIObject {

	public final Transform transform = new Transform(2, 2);

	abstract public Rectangle getLocalBoundingBox();

	public Rectangle getBoundingBox() {
		Rectangle localBox = getLocalBoundingBox();
		return new Rectangle(transform.applyToPoint(localBox.pos),
				transform.applyToPoint(localBox.diag));
	}
}
