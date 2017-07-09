package util.ui.topContainers;

import util.math.shape.shape2d.Rectangle;

import java.util.LinkedList;

/**
 * Created by esia on 19/06/17.
 */
public class UIWindow<T, L extends UILayer<T>> {
	protected LinkedList<L> layers = new LinkedList<>();
	private Rectangle box;
	private String name;

	public UIWindow(Rectangle box, String name) {
		this.box = box;
		this.name = name;
	}

	public Rectangle getBox() {
		return box;
	}

	public void setBox(Rectangle box) {
		this.box = box;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int layerNbr() {
		return layers.size();
	}

	public void addOnTop(L obj) {
		layers.addLast(obj);
	}

	public void addOnBottom(L obj) {
		layers.addFirst(obj);
	}

	public void insert(int i, L obj) {
		layers.add(i, obj);
	}

	public boolean remove(L obj) {
		return layers.remove(obj);
	}

	public L remove(int i) {
		return layers.remove(i);
	}

	public int indexOf(L obj) {
		return layers.indexOf(obj);
	}

	public L get(int i) {
		return layers.get(i);
	}
}
