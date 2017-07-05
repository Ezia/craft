package util.datastructure;

/**
 * Created by esia on 05/07/17.
 */
public abstract class Image {

	public enum PixelFormat {
		RGB
	};

	public final String file;

	public Image(String file) {
		this.file = file;
	}

	public abstract void load() throws ImageException;

	public abstract boolean isLoaded();

	public abstract int[] getData(PixelFormat format) throws ImageException;

}
