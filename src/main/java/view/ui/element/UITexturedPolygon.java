package view.ui.element;

import static util.Test.*;

import util.math.Matrix;
import util.math.Transform;
import util.shape.Polygon;
import util.shape.Rectangle;
import view.ui.Image;
import view.ui.ImageException;

/**
 * Textured shape uniformally textured
 */
public class UITexturedPolygon extends UIPolygon {

	private Image texture;
	private Rectangle texturePosition;

	private Transform projection;

	public UITexturedPolygon(Polygon polygon, Image texture, Rectangle texturePosition) {
		super(polygon);
		this.texture = notNull(texture);
		this.texturePosition = notNull(texturePosition);
		projection = new Transform(2, 2);
		projection.setMatrix(Matrix.corneredProjection2D(texturePosition));
	}

	public UITexturedPolygon(Polygon polygon, Image texture) {
		this(polygon, texture, notNull(polygon).getBoundingBox());
	}

	private void update() throws ImageException {
		if (!texture.isLoaded()) {
			texture.load();
		}
	}

	public Image getTexture() throws ImageException {
		update();
		return texture;
	}

	public Matrix getTextureProjectionMatrix() {
		return projection.globalMatrix();
	}
}
