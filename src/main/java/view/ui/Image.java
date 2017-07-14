package view.ui;

/**
 * Created by esia on 05/07/17.
 */
public abstract class Image {

	public enum PixelFormat {
		RGB,
		RGBA
	};

	public final String file;

	public Image(String file) {
		this.file = file;
	}

	public abstract void load() throws ImageException;

	public abstract boolean isLoaded();

	public abstract short[] getData(PixelFormat format) throws ImageException;

	public abstract int width();

	public abstract int height();

}
