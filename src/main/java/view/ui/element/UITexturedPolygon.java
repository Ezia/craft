package view.ui.element;

import static util.Test.*;

import util.math.Matrix;
import util.math.Vector;
import util.shape.Polygon;
import util.shape.Rectangle;
import view.ui.Image;
import view.ui.ImageException;

import java.util.LinkedList;

/**
 * Textured shape uniformally textured
 */
public class UITexturedPolygon extends UIPolygon {

	private Image texture;
	private Rectangle texturePosition;

	private Matrix projection = null;

	public UITexturedPolygon(Polygon polygon, Image texture, Rectangle texturePosition) {
		super(polygon);
		this.texture = notNull(texture);
		this.texturePosition = notNull(texturePosition);
	}

	public UITexturedPolygon(Polygon polygon, Image texture) {
		this(polygon, texture, notNull(polygon).getBoundingBox());
	}

	private void update() throws ImageException {
		if (!texture.isLoaded()) {
			texture.load();
		}
		if (projection == null) {
			projection = Matrix.projection2D(texturePosition);
		}
	}

	public Image getTexture() throws ImageException {
		update();
		return texture;
	}

	public Vector getPointTextureCoord(Vector point) throws ImageException {
		update();
		return new Vector(
				2,
				new Vector(sizeEqual(notNull(point), 2), 1.).times(projection)
		);
	}
}
