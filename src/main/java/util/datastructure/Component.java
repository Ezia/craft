package util.datastructure;

import util.math.Vector;

public class Component<T> {

	public final int l, c;
	public final Bitmap occupation;
	public final T value;

	public Component(int l, int c, Bitmap occupation, T value) {
		this.l = l;
		this.c = c;
		this.occupation = occupation;
		this.value = value;
	}
}
