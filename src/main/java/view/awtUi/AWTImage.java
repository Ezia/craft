package view.awtUi;

import view.ui.Image;
import view.ui.ImageException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
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
	public float[] getFloatData(PixelFormat format) throws ImageException {
		assert(loaded);
		Raster raster = img.getData();
		switch (format) {
			case RGBA:
				float[] data = new float[width()*height()*4];
				switch (img.getType()) {
					case BufferedImage.TYPE_3BYTE_BGR :
						for (int i = 0; i < height(); ++i) {
							for (int j = 0; j < width(); ++j) {
								float[] pixel = raster.getPixel(j, i, (float[])null);
								data[(i*width() + j)*4] = pixel[0]/Byte.MAX_VALUE;
								data[(i*width() + j)*4 + 1] = pixel[1]/Byte.MAX_VALUE;
								data[(i*width() + j)*4 + 2] = pixel[2]/Byte.MAX_VALUE;
								data[(i*width() + j)*4 + 3] = 1f;
							}
						}
						break;
					case BufferedImage.TYPE_BYTE_GRAY :
						for (int i = 0; i < height(); ++i) {
							for (int j = 0; j < width(); ++j) {
								float[] pixel = raster.getPixel(j, i, (float[])null);
								data[(i*width() + j)*4] = pixel[0]/Byte.MAX_VALUE;
								data[(i*width() + j)*4 + 1] = pixel[0]/Byte.MAX_VALUE;
								data[(i*width() + j)*4 + 2] = pixel[0]/Byte.MAX_VALUE;
								data[(i*width() + j)*4 + 3] = 1f;
							}
						}
						break;
					default:
						throw new ImageException("Unsupported image format.");
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
