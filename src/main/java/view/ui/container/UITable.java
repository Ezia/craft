package view.ui.container;

import util.datastructure.Table;
import util.math.Matrix;
import util.math.Transform;
import util.math.Vector;
import util.shape.Rectangle;
import view.ui.UIContainer;
import view.ui.UIObject;

import java.util.Arrays;

public class UITable<T extends UIObject> extends UIContainer {

	private T background;
	private Transform backgroundTransform;
	private Transform tableTransform;
	private Table<T> table;
	private Table<Transform> transforms;

	/**
	 * transforms to translate of half cell width/height
	 */
	private Transform[] lineTransforms;
	private Transform[] columnTransforms;
	private boolean[] lineUpToData;
	private boolean[] columnUpToData;
	private boolean backgroundUpToDate;
	private boolean upToDate;

	public UITable(int lineNbr, int columnNbr) {
		super(false);

//		assert (lineNbr > 0 && columnNbr > 0);

		background = null;
		tableTransform = new Transform(2, 2, this.transform);
		table = new Table<T>(lineNbr, columnNbr, null);
		transforms = new Table<Transform>(lineNbr, columnNbr);
		for (int i = 0; i < lineNbr; ++i) {
			for (int j = 0; j < columnNbr; ++j) {
				transforms.set(i, j, new Transform(2, 2, tableTransform));
			}
		}

		// up-to-date flags

		backgroundUpToDate = true;
		upToDate = true;
		lineUpToData = new boolean[lineNbr];
		columnUpToData = new boolean[columnNbr];

		Arrays.fill(lineUpToData, true);
		Arrays.fill(columnUpToData, true);

		// transforms

		backgroundTransform = new Transform(2, 2, this.transform);
		lineTransforms = new Transform[lineNbr];
		columnTransforms = new Transform[columnNbr];

		lineTransforms[0] = new Transform(1, 1);
		for (int i = 1; i < lineNbr; ++i) {
			lineTransforms[i] = new Transform(1, 1);
			lineTransforms[i - 1].addChild(lineTransforms[i]);
		}

		columnTransforms[0] = new Transform(1, 1);
		for (int i = 1; i < columnNbr; ++i) {
			columnTransforms[i] = new Transform(1, 1);
			columnTransforms[i - 1].addChild(columnTransforms[i]);
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
		upToDate = false;

		if (get(l, c) != null) {
			get(l, c).setParent(null, null);
		}
		value.setParent(this, transforms.get(l, c));
		table.set(l, c, value);
	}

	public void setBackground(T value) {
		// TODO : optimize in certain cases
		backgroundUpToDate = false;
		upToDate = false;

		if (background != null) {
			background.setParent(null, null);
		}
		value.setParent(this, backgroundTransform);
		background = value;
	}

	public T get(int l, int c) {
		return table.get(l, c);
	}

	public T getBackground() {
		return background;
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
							maxWidth = Math.max(maxWidth, get(i, j).getDimension().x());
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
							maxHeight = Math.max(maxHeight, get(j, i).getDimension().y());
						}
					}
					columnTransforms[i].setMatrix(Matrix.translation(new Vector(maxHeight)));
				}
			}


			tableTransform.setIdentity();
			backgroundTransform.setIdentity();

			if (background != null) {
				Vector tableDimension = new Vector(
						lineTransforms[lineNbr()-1].applyToPoint(new Vector(0.)).x(),
						columnTransforms[columnNbr()-1].applyToPoint(new Vector(0.)).x()
				);

				Vector backgroundDimension = background.getDimension();

				if (backgroundDimension.x() > tableDimension.x()) {
					tableTransform.setMatrix(Matrix.translation(
							new Vector((backgroundDimension.x() - tableDimension.x())/2., 0.)
					));
				} else {
					backgroundTransform.setMatrix(Matrix.translation(
							new Vector((tableDimension.x() - backgroundDimension.x())/2., 0.)
					));
				}

				if (backgroundDimension.y() > tableDimension.y()) {
					tableTransform.postMultiply(Matrix.translation(
							new Vector(0., (backgroundDimension.y() - tableDimension.y())/2.)
					));
				} else {
					backgroundTransform.postMultiply(Matrix.translation(
							new Vector(0., (tableDimension.y() - backgroundDimension.y())/2.)
					));
				}
			}

			for (int l = 0; l < columnNbr(); ++l) {
				for (int c = 0; c < columnNbr(); ++c) {
					if ((!lineUpToData[l] || !columnUpToData[c]) && get(l, c) != null) {
						Vector diag = get(l, c).getDimension();

						transforms.get(l, c).setMatrix(Matrix.translation(new Vector(
								lineTransforms[l].globalMatrix().get(1, 0)
										- (lineTransforms[l].localMatrix().get(1, 0) + diag.x()) / 2,
								columnTransforms[c].globalMatrix().get(1, 0)
										- (columnTransforms[c].localMatrix().get(1, 0) + diag.y()) / 2
						)));
					}
				}
			}

			Arrays.fill(lineUpToData, true);
			Arrays.fill(columnUpToData, true);

			backgroundUpToDate = true;
			upToDate = true;
		}
	}

	@Override
	public void remove(UIObject child) {
		if (background == child) {
			background = null;
			upToDate = false;
			return;
		}
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

//	@Override
//	public Rectangle getBoundingBox() {
//		return transform.applyLocalToRectangle(
//				new Rectangle(new Vector(0., 0.), new Vector(width(), height()))
//		);
//	}

	//	@Override
	public double width() {
		update();
		return lineTransforms[lineNbr() - 1].applyToPoint(new Vector(0.)).x();
	}

	//	@Override
	public double height() {
		update();
		return columnTransforms[columnNbr() - 1].applyToPoint(new Vector(0.)).x();
	}

	@Override
	public void wasDimensionned(UIObject child, Vector newDimension) {
		if (child == background) {
			backgroundUpToDate = false;
			upToDate = false;
			return;
		}
		for (int i = 0; i < lineNbr(); i++) {
			for (int j = 0; j < columnNbr(); ++j) {
				if (get(i, j) == child) {
					lineUpToData[i] = false;
					columnUpToData[j] = false;
					upToDate = false;
				}
			}
		}
	}

	@Override
	public Vector getDimension() {
		return new Vector(width(), height());
	}

	@Override
	public void setDimension(Vector dimension) {
		// TODO
	}
}
