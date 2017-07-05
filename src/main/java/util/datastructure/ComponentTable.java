package util.datastructure;

import util.math.Vector;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

public class ComponentTable<T extends Component> {

	private Table<Component<T>> table;
	private TreeSet<Component> components = new TreeSet<>();

	public ComponentTable(int lineNbr, int columnNbr) {
		table = new Table<Component<T>>(lineNbr, columnNbr, null);
	}

	public int columnNbr() {
		return table.columnNbr();
	}

	public int lineNbr() {
		return table.lineNbr();
	}

	public T get(int l, int c) {
		if (table.get(l, c) == null) {
			return null;
		}
		return table.get(l, c).value;
	}

	public void set(int l, int c, Bitmap bitmap, T value) {
		set(new Component<>(l, c, bitmap, value));
	}

	private void set(Component<T> comp) {
		remove(comp.l, comp.c, comp.occupation);
		for (int l = comp.l; l < comp.occupation.lineNbr(); ++l) {
			for (int c = comp.c; c < comp.occupation.columnNbr(); ++c) {
				table.set(l, c, comp);
			}
		}
		components.add(comp);
	}

	private void remove(Component<T> comp) {
		if (comp != null) {
			for (int l = comp.l; l < comp.occupation.lineNbr(); ++l) {
				for (int c = comp.c; c < comp.occupation.columnNbr(); ++c) {
					table.set(l, c, null);
				}
			}
			components.remove(comp);
		}
	}

	public void remove(int l0, int c0) {
		remove(table.get(l0, c0));
	}

	public void remove(int l0, int c0, int lNbr, int cNbr) {
		for (int l = l0; l < lNbr; ++l) {
			for (int c = c0; c < cNbr; ++c) {
				remove(l, c);
			}
		}
	}

	public void remove(int l0, int c0, Bitmap bitmap) {
		for (int l = l0; l < bitmap.lineNbr(); ++l) {
			for (int c = c0; c < bitmap.columnNbr(); ++c) {
				remove(l, c);
			}
		}
	}

	public void clear() {
		table.clear();
		components.clear();
	}

	public boolean isOccupied(int l, int c) {
		return table.isOccupied(l, c);
	}

	public boolean isOccupied(int l0, int c0, int lNbr, int cNbr) {
		for (int l = l0; l < lNbr; ++l) {
			for (int c = c0; c < cNbr; ++c) {
				if (isOccupied(l, c)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isOccupied(int l0, int c0, Bitmap bitmap) {
		for (int l = l0; l < bitmap.lineNbr(); ++l) {
			for (int c = c0; c < bitmap.columnNbr(); ++c) {
				if (isOccupied(l, c)) {
					return true;
				}
			}
		}
		return false;
	}
}
