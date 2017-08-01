package util.math;

import util.Test;

import static util.Test.*;

/**
 * Created by esia on 16/07/17.
 */
public class Color {
	public enum Type {
		RGB(3),
		RGBA(4);

		private final int size;

		private Type(int size) {
			this.size = size;
		}

		public int size() {
			return size;
		}
	};

	private final Vector color;
	private final Type type;

	public Color(Type type, Vector color) {
		notNull(color);
		notNull(type);
		equal(color.size(), type.size());
		this.color = color;
		this.type = type;
	}

	public Vector vector() {
		return color;
	}

	public Type type() {
		return type;
	}
}
