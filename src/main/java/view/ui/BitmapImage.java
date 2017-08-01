package view.ui;

import util.datastructure.Bitmap;

/**
 * Created by esia on 18/07/17.
 */
public class BitmapImage extends Image {
	private Bitmap bitmap;

	public BitmapImage(Bitmap bitmap) {
		super(null);
		this.bitmap = bitmap;
	}

	@Override
	public void load() throws ImageException {

	}

	@Override
	public boolean isLoaded() {
		return false;
	}

	@Override
	public float[] getFloatData(PixelFormat format) throws ImageException {
		return new float[0];
	}

	@Override
	public int width() {
		return 0;
	}

	@Override
	public int height() {
		return 0;
	}

	// TODO
}
