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
		columnTransforms = new Transform[columnNbr];

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

	protected void update() {
		for (int i = 0; i < lineNbr(); ++i) {
			if (!lineUpToData[i] && i > 0) {
				double maxWidth = 0;
				for (int j = 0; j < columnNbr(); ++j) {
					if (get(i-1, j) != null) {
						maxWidth = Math.max(maxWidth, get(i-1, j).width());
					}
				}
				lineTransforms[i].setMatrix(Matrix.translation(new Vector(maxWidth)));
			}
			lineUpToData[i] = true;
		}
		for (int i = 0; i < columnNbr(); ++i) {
			if (!columnUpToData[i] && i > 0) {
				double maxHeight = 0;
				for (int j = 0; j < lineNbr(); ++j) {
					if (get(j, i-1) != null) {
						maxHeight = Math.max(maxHeight, get(j, i-1).height());
					}
				}
				columnTransforms[i].setMatrix(Matrix.translation(new Vector(maxHeight)));
			}
			columnUpToData[i] = true;
		}
		return;
	}

	protected Matrix getMatrix(int l, int c) {
		update();
		return Matrix.translation(new Vector(
				lineTransforms[l].globalMatrix().get(1, 0),
				columnTransforms[c].globalMatrix().get(1, 0)
		));
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
		lineUpToData[l] = false;
		columnUpToData[c] = false;

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
				lineTransforms[i].setMatrix(Matrix.translation(new Vector(value.width())));
				lineUpToData[i] = true;
			}
			for (int i = 0; i < columnNbr(); ++i) {
				columnTransforms[i].setMatrix(Matrix.translation(new Vector(value.height())));
				columnUpToData[i] = true;
			}
		}

		table.fill(value);
	}

	@Override
	public void removeChild(UIObject child) {
		for (int i = 0; i < lineNbr(); i++) {
			for (int j = 0; j < columnNbr(); ++j) {
				if (get(i, j) == child) {
					remove(i, j);
					return;
				}
			}
		}
	}

	public void clear() {
		fill(null);
	}
}
