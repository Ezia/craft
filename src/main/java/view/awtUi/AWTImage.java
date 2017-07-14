package view.awtUi;

import view.ui.Image;
import view.ui.ImageException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by esia on 05/07/17.
 */
public class AWTImage extends Image {

	private boolean loaded = false;
	private BufferedImage img = null;

	public AWTImage(String file) {
		super(file);
	}

	@Override
	public void load() throws ImageException {
		try {
			img = ImageIO.read(new File(file));
			loaded = true;
		} catch (Exception e) {
			throw new ImageException(e.getMessage());
		}
	}

	@Override
	public boolean isLoaded() {
		return loaded;
	}

	@Override
	public short[] getData(PixelFormat format) throws ImageException {
		assert(loaded);
		switch (format) {
			case RGB:
				short[] data = new short[width()*height()*4];
				for (int i = 0; i < width(); ++i) {
					for (int j = 0; j < height(); ++j) {
						data[j*width() + i] = 0; // TODO
						data[j*width() + i + 1] = 0; // TODO
						data[j*width() + i + 2] = 0; // TODO
						data[j*width() + i + 3] = 0; // TODO
					}
				}
				return data;
			default:
				throw new ImageException("Unsupported image format.");
		}
	}

	public int width() {
		return img.getWidth();
	}


	public int height() {
		return img.getHeight();
	}
}
