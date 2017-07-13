package util.ui.container;

import util.datastructure.Table;
import util.math.Matrix;
import util.math.Transform;
import util.math.Vector;
import util.math.shape.shape2d.Rectangle;
import util.ui.UIContainer;
import util.ui.UIElement;

import java.util.Arrays;

public class UITable<T extends UIElement> extends UIContainer {

	private Table<T> table;
	private Table<Transform> transforms;

	/**
	 * transforms to translate of half cell width/height
	 */
	private Transform[] lineTransforms;
	private Transform[] columnTransforms;
	private boolean[] lineUpToData;
	private boolean[] columnUpToData;
	private boolean upToDate = true;

	public UITable(int lineNbr, int columnNbr) {
		assert(lineNbr > 0 && columnNbr > 0);

		table = new Table<T>(lineNbr, columnNbr, null);
		transforms = new Table<Transform>(lineNbr, columnNbr);
		for (int i = 0; i < lineNbr; ++i) {
			for (int j = 0; j < columnNbr; ++j) {
				transforms.set(i, j, new Transform(2, 2, transform));
			}
		}

		// up-to-date flags

		lineUpToData = new boolean[lineNbr];
		columnUpToData = new boolean[columnNbr];

		Arrays.fill(lineUpToData, true);
		Arrays.fill(columnUpToData, true);

		// transforms

		lineTransforms = new Transform[lineNbr];
		columnTransforms = new Transform[columnNbr];

		lineTransforms[0] = new Transform(1, 1);
		for (int i = 1; i < lineNbr; ++i) {
			lineTransforms[i] = new Transform(1, 1);
			lineTransforms[i-1].addChild(lineTransforms[i]);
		}

		columnTransforms[0] = new Transform(1, 1);
		for (int i = 1; i < columnNbr; ++i) {
			columnTransforms[i] = new Transform(1, 1);
			columnTransforms[i-1].addChild(columnTransforms[i]);
		}
	}

	public int columnNbr() {
		return table.columnNbr();
	}

	public int lineNbr() {
		return table.lineNbr();
	}

	public void set(int l, int c, T value) {
		// TODO : optimize in certain cases
		lineUpToData[l] = false;
		columnUpToData[c] = false;

		if (get(l, c) != null) {
			get(l, c).setParent(null);
		}

		value.setParent(this);
		value.setTransformParent(transforms.get(l, c));

		table.set(l, c, value);

		upToDate = false;
	}

	public T get(int l, int c) {
		return table.get(l, c);
	}

	public void fill(T value) {
		for (int i = 0; i < lineNbr(); ++i) {
			for (int j = 0; j < columnNbr(); ++j) {
				set(i, j, value);
			}
		}
		upToDate = false;
	}

	public void update() {
		if (!upToDate) {
			for (int i = 0; i < lineNbr(); ++i) {
				if (!lineUpToData[i]) {
					double maxWidth = 0;
					for (int j = 0; j < columnNbr(); ++j) {
						if (get(i, j) != null) {
							maxWidth = Math.max(maxWidth, get(i, j).width());
						}
					}
					lineTransforms[i].setMatrix(Matrix.translation(new Vector(maxWidth)));
				}
			}

			for (int i = 0; i < columnNbr(); ++i) {
				if (!columnUpToData[i]) {
					double maxHeight = 0;
					for (int j = 0; j < lineNbr(); ++j) {
						if (get(j, i) != null) {
							maxHeight = Math.max(maxHeight, get(j, i).height());
						}
					}
					columnTransforms[i].setMatrix(Matrix.translation(new Vector(maxHeight)));
				}
			}

			for (int l = 0; l < columnNbr(); ++l) {
				for (int c = 0; c < columnNbr(); ++c) {
					if ((!lineUpToData[l] || !columnUpToData[c]) && get(l, c) != null) {
						transforms.get(l, c).setMatrix(Matrix.translation(new Vector(
								lineTransforms[l].globalMatrix().get(1, 0)
										- (lineTransforms[l].localMatrix().get(1, 0) + get(l, c).width())/2,
								columnTransforms[c].globalMatrix().get(1, 0)
										- (columnTransforms[c].localMatrix().get(1, 0) + get(l, c).height())/2
						)));
					}
				}
			}

			Arrays.fill(lineUpToData, true);
			Arrays.fill(columnUpToData, true);

			upToDate = true;
		}
	}

	@Override
	public void remove(UIElement child) {
		for (int i = 0; i < lineNbr(); i++) {
			for (int j = 0; j < columnNbr(); ++j) {
				if (get(i, j) == child) {
					this.table.set(i, j, null);
					upToDate = false;
					return;
				}
			}
		}
	}

	@Override
	public Rectangle getLocalBoundingBox() {
		return transform.applyLocalToRectangle(
				new Rectangle(new Vector(0., 0.), new Vector(width(), height()))
		);
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
}
