package util.datastructure;

/**
 * Created by esia on 03/07/17.
 */
public class Bitmap {
	private boolean[][] elements;

	public Bitmap(int lineNbr, int columnNbr) {
		assert(lineNbr > 0 && columnNbr > 0);
		this.elements = new boolean[lineNbr][columnNbr];
	}

	public Bitmap(int lineNbr, int columnNbr, boolean initValue) {
		assert(lineNbr > 0 && columnNbr > 0);
		this.elements = new boolean[lineNbr][columnNbr];
		for (int l = 0; l < lineNbr; ++l) {
			for (int c = 0; c < columnNbr; ++c) {
				elements[l][c] = initValue;
			}
		}
	}

	public Bitmap(boolean[][] values) {
		assert(values.length > 0);
		int lineNbr = values.length;
		int columnMbr = values[0].length;
		elements = new boolean[lineNbr][columnMbr];
		for (int l = 0; l < lineNbr; l++) {
			assert(values[l].length == columnMbr);
			for (int c = 0; c < columnMbr; c++) {
				elements[l][c] = values[l][c];
			}
		}
	}

	public int lineNbr() {
		return elements.length;
	}

	public int columnNbr() {
		return elements[0].length;
	}

	public boolean get(int l, int c) {
		return elements[l][c];
	}

	private void set(int l, int c, boolean value) {
		elements[l][c] = value;
	}

	public Bitmap and(Bitmap map) {
		assert(lineNbr() == map.lineNbr() && columnNbr() == map.columnNbr());
		Bitmap result = new Bitmap(lineNbr(), columnNbr());
		for (int l = 0; l < lineNbr(); ++l) {
			for (int c = 0; c < columnNbr(); ++c) {
				result.elements[l][c] = get(l, c) && map.get(l, c);
			}
		}
		return result;
	}

	public Bitmap or(Bitmap map) {
		assert(lineNbr() == map.lineNbr() && columnNbr() == map.columnNbr());
		Bitmap result = new Bitmap(lineNbr(), columnNbr());
		for (int l = 0; l < lineNbr(); ++l) {
			for (int c = 0; c < columnNbr(); ++c) {
				result.elements[l][c] = get(l, c) || map.get(l, c);
			}
		}
		return result;
	}

	public Bitmap xor(Bitmap map) {
		assert(lineNbr() == map.lineNbr() && columnNbr() == map.columnNbr());
		Bitmap result = new Bitmap(lineNbr(), columnNbr());
		for (int l = 0; l < lineNbr(); ++l) {
			for (int c = 0; c < columnNbr(); ++c) {
				result.elements[l][c] = (get(l, c) && !map.get(l, c)) || (!get(l, c) && map.get(l, c));
			}
		}
		return result;
	}
}
