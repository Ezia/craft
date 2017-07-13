package util;

import util.math.Vector;

import java.util.Collection;

/**
 * Created by esia on 13/07/17.
 */
public class Test {

	///// INTEGER /////

	public static int greater(int value, int min) {
		if (value <= min) {
			throw new IllegalArgumentException();
		} else {
			return value;
		}
	}

	public static int greaterOrEqual(int value, int min) {
		if (value < min) {
			throw new IllegalArgumentException();
		} else {
			return value;
		}
	}

	public static int less(int value, int max) {
		if (value >= max) {
			throw new IllegalArgumentException();
		} else {
			return value;
		}
	}

	public static int lessOrEqual(int value, int max) {
		if (value > max) {
			throw new IllegalArgumentException();
		} else {
			return value;
		}
	}

	public static int equal(int value1, int value2) {
		if (value1 != value2) {
			throw new IllegalArgumentException();
		} else {
			return value1;
		}
	}

	/**
	 * min <= value < max
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static int range(int value, int min, int max) {
		return less(greaterOrEqual(value, min), max);
	}

	///// DOUBLE /////

	public static double greater(double value, double min) {
		if (value <= min) {
			throw new IllegalArgumentException();
		} else {
			return value;
		}
	}

	public static double greaterOrEqual(double value, double min) {
		if (value < min) {
			throw new IllegalArgumentException();
		} else {
			return value;
		}
	}

	public static double less(double value, double max) {
		if (value >= max) {
			throw new IllegalArgumentException();
		} else {
			return value;
		}
	}

	public static double lessOrEqual(double value, double max) {
		if (value > max) {
			throw new IllegalArgumentException();
		} else {
			return value;
		}
	}

	public static double equal(double value1, double value2) {
		if (value1 != value2) {
			throw new IllegalArgumentException();
		} else {
			return value1;
		}
	}

	/**
	 * min <= value < max
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static double range(double value, double min, double max) {
		return less(greaterOrEqual(value, min), max);
	}

	///// REFERENCE /////

	public static <T> T notNull(T value) {
		if (value == null) {
			throw new IllegalArgumentException();
		} else {
			return value;
		}
	}

	///// Vector /////

	public static Vector sizeEqual(Vector vector, int size) {
		if (vector.size() == size) {
			throw new IllegalArgumentException();
		} else {
			return vector;
		}
	}

	public static Vector sizeEqual(Vector vector1, Vector vector2) {
		if (vector1.size() == vector2.size()) {
			throw new IllegalArgumentException();
		} else {
			return vector1;
		}
	}

	public static Vector sizeGreater(Vector vector, int min) {
		if (vector.size() <= min) {
			throw new IllegalArgumentException();
		} else {
			return vector;
		}
	}

	public static Vector sizeGreaterOrEqual(Vector vector, int min) {
		if (vector.size() < min) {
			throw new IllegalArgumentException();
		} else {
			return vector;
		}
	}

	public static Vector sizeLess(Vector vector, int max) {
		if (vector.size() >= max) {
			throw new IllegalArgumentException();
		} else {
			return vector;
		}
	}

	public static Vector sizeLessOrEqual(Vector vector, int max) {
		if (vector.size() > max) {
			throw new IllegalArgumentException();
		} else {
			return vector;
		}
	}

	///// COLLECTION /////

	public static <T> T notContained(T value, Collection<T> collection) {
		if (collection.contains(value)) {
			throw new IllegalArgumentException();
		} else {
			return value;
		}
	}

	public static <T> T contained(T value, Collection<T> collection) {
		if (!collection.contains(value)) {
			throw new IllegalArgumentException();
		} else {
			return value;
		}
	}

}
