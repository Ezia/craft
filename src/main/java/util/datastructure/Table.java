package util.datastructure;

public class Table<T> {

	private Object[][] elements;

	public Table(int lineNbr, int columnNbr) {
		assert(columnNbr > 0 && lineNbr > 0);
		elements = new Object[lineNbr][columnNbr];
		for (int l = 0; l < lineNbr; l++) {
			for (int c = 0; c < columnNbr; c++) {
				elements[l][c] = null;
			}
		}
	}

	public Table(int lineNbr, int columnNbr, T initValue) {
		assert(columnNbr > 0 && lineNbr > 0);
		elements = new Object[lineNbr][columnNbr];
		for (int l = 0; l < lineNbr; l++) {
			for (int c = 0; c < columnNbr; c++) {
				elements[l][c] = initValue;
			}
		}
	}

	public int columnNbr() {
		return elements[0].length;
	}

	public int lineNbr() {
		return elements.length;
	}

	public void set(int l, int c, T value) {
		assert(l >= 0 && l < lineNbr()
				&& c >= 0 && c < columnNbr()
		);
		elements[l][c] = value;
	}

	public T get(int l, int c) {
		assert(l >= 0 && l < lineNbr()
				&& c >= 0 && c < columnNbr()
		);
		return (T)elements[l][c];
	}

	public void remove(int l, int c) {
		assert(l >= 0 && l < lineNbr()
				&& c >= 0 && c < columnNbr()
		);
		elements[l][c] = null;
	}

	public void fill(T value) {
		for (int l = 0; l < lineNbr(); l++) {
			for (int c = 0; c < columnNbr(); c++) {
				set(l, c, value);
			}
		}
	}

	public void clear() {
		for (int l = 0; l < lineNbr(); l++) {
			for (int c = 0; c < columnNbr(); c++) {
				remove(l, c);
			}
		}
	}

	public boolean isOccupied(int l, int c) {
		return get(l, c) == null;
	}
}
