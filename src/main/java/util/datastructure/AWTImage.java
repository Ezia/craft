package util.datastructure;

import javafx.scene.image.PixelReader;

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
	public int[] getData(PixelFormat format) throws ImageException {
		assert(loaded);
		switch (format) {
			case RGB:
				return img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, 0);
				break;
			default:
				throw new ImageException("Unsupported image format.");
		}
		return null;
	}
}
