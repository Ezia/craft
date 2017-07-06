package util.ui.container;

import util.datastructure.Table;
import util.math.Matrix;
import util.math.Transform;
import util.math.Vector;
import util.ui.UIContainer;
import util.ui.UIObject;

public class UITable<T extends UIObject> extends UIContainer<T> {
	private Table<T> table;

	private Transform[] lineTransforms;
	private Transform[] columnTransforms;
	private boolean[] lineUpToData;
	private boolean[] columnUpToData;

	public UITable(int lineNbr, int columnNbr) {
		assert(lineNbr > 0 && columnNbr > 0);

		table = new Table<T>(lineNbr, columnNbr, null);

		lineUpToData = new boolean[lineNbr];
		columnUpToData = new boolean[columnNbr];
		lineTransforms = new Transform[lineNbr];
		lineTransforms = new Transform[columnNbr];

		lineTransforms[0] = new Transform(1, 1);
		lineUpToData[0] = true;
		for (int i = 1; i < lineNbr; ++i) {
			lineTransforms[i] = new Transform(1, 1);
			lineTransforms[i-1].addChild(lineTransforms[i]);
			lineUpToData[i] = true;
		}

		columnTransforms[0] = new Transform(1, 1);
		columnUpToData[0] = true;
		for (int i = 1; i < columnNbr; ++i) {
			columnTransforms[i] = new Transform(1, 1);
			columnTransforms[i-1].addChild(columnTransforms[i]);
			columnUpToData[i] = true;
		}
	}

	private void update() {
		for (int i = 0; i < lineNbr(); ++i) {
			if (!lineUpToData[i]) {
				double maxWidth = 0;
				for (int j = 0; j < columnNbr(); ++j) {
					maxWidth = Math.max(maxWidth, get(i, j).width());
				}
			}
		}
		for (int i = 0; i < columnNbr(); ++i) {
			if (!columnUpToData[i]) {
				double maxHeight = 0;
				for (int j = 0; j < lineNbr(); ++j) {
					maxHeight = Math.max(maxHeight, get(j, i).height());
				}
			}
		}
	}

	@Override
	public double width() {
		update();
		return lineTransforms[lineNbr()-1].applyToPoint(new Vector(0.)).x();
	}

	@Override
	public double height() {
		update();
		return columnTransforms[columnNbr()-1].applyToPoint(new Vector(0.)).x();
	}

	public int columnNbr() {
		return table.columnNbr();
	}

	public int lineNbr() {
		return table.lineNbr();
	}

	public void set(int l, int c, T value) {
		if (value != null) {
			if (lineUpToData[l] && value.width() > lineTransforms[l].localMatrix().get(1, 0)) {
				lineTransforms[l].setMatrix(Matrix.translation2D(new Vector(value.width())));
			} else {
				lineUpToData[l] = false;
			}

			if (columnUpToData[c] && value.height() > columnTransforms[c].localMatrix().get(1, 0)) {
				columnTransforms[c].setMatrix(Matrix.translation2D(new Vector(value.height())));
			} else {
				columnUpToData[c] = false;
			}
		} else {
			lineUpToData[l] = false;
			columnUpToData[c] = false;
		}

		table.set(l, c, value);
	}

	public T get(int l, int c) {
		return table.get(l, c);
	}

	public void remove(int l, int c) {
		table.set(l, c, null);
	}

	public void fill(T value) {
		if (value == null) {
			for (int i = 0; i < lineNbr(); ++i) {
				lineTransforms[i].setIdentity();
				lineUpToData[i] = true;
			}
			for (int i = 0; i < columnNbr(); ++i) {
				columnTransforms[i].setIdentity();
				columnUpToData[i] = true;
			}
		} else {
			for (int i = 0; i < lineNbr(); ++i) {
				lineTransforms[i].setMatrix(Matrix.translation2D(new Vector(value.width())));
				lineUpToData[i] = true;
			}
			for (int i = 0; i < columnNbr(); ++i) {
				columnTransforms[i].setMatrix(Matrix.translation2D(new Vector(value.height())));
				columnUpToData[i] = true;
			}
		}

		table.fill(value);
	}

	public void clear() {
		fill(null);
	}
}
