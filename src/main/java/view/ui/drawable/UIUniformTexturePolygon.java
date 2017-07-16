package view.ui.drawable;

import util.math.Color;
import util.math.Matrix;
import util.shape.Polygon;
import util.shape.Rectangle;
import view.ui.Image;

/**
 * Created by esia on 16/07/17.
 */
public class UIUniformTexturePolygon extends UIShape<Polygon> {
	protected final Image texture;
	protected final Matrix uvMatrix;

	public UIUniformTexturePolygon(Polygon shape, Image texture, Rectangle textureBox) {
		super(shape);
		this.texture = texture;
		this.uvMatrix = Matrix.corneredProjection2D(textureBox);
	}

	public UIUniformTexturePolygon(Polygon shape, Image texture) {
		this(shape, texture, shape.getBoundingBox());
	}

	public Image getTexture() {
		return texture;
	}

	public Matrix getUvMatrix() {
		return uvMatrix;
	}
}
