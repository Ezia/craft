package ui.container;

import util.datastructure.Table;
import ui.UIContainer;
import util.math.shape.shape2d.Rectangle;
import ui.UIObject;
import util.math.Vector;

public class UITable<T extends UIObject> extends UIContainer<T> {
	private Table<T> table;
	private float[] lineWidth;
	private float[] columnHeight;

	public UITable(int lineNbr, int columnNbr) {
		table = new Table<T>(lineNbr, columnNbr);
		lineWidth = new float[lineNbr];
		columnHeight = new float[columnNbr];
		for (int i = 0; i < lineNbr; ++i) {
			lineWidth[i] = 0;
		}
		for (int i = 0; i < columnNbr; ++i) {
			columnHeight[i] = 0;
		}
	}

	@Override
	public Rectangle getLocalBoundingBox() {
		// TODO
		return null;
	}

	@Override
	public Rectangle getBoundingBox() {
		float width = 0;
		float height = 0;
		for (int l = 0; l < table.lineNbr(); ++l) {
			width = Math.max(lineWidth[l], width);
		}
		for (int c = 0; c < table.columnNbr(); ++c) {
			height = Math.max(columnHeight[c], width);
		}
		return new Rectangle(
				new Vector(0, 0),
				new Vector(width, height)
		);
	}

	public int columnNbr() {
		return table.columnNbr();
	}

	public int lineNbr() {
		return table.lineNbr();
	}

	public void set(int l, int c, T value) {
		if (table.get(l, c) != null) {
			Rectangle box = get(l, c).getBoundingBox();
			lineWidth[l] -= box.diag.x();
			columnHeight[c] -= box.diag.y();
		}
		if (value != null) {
			Rectangle box = value.getBoundingBox();
			lineWidth[l] += box.diag.x();
			columnHeight[c] += box.diag.y();
		}
		table.set(l, c, value);
	}

	public T get(int l, int c) {
		return table.get(l, c);
	}

	public void remove(int l, int c) {
		table.remove(l, c);
	}

	public void fill(T value) {
		table.fill(value);
	}

	public void clear() {
		table.clear();
	}
}
