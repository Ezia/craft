package util.ui.topContainers;

import util.ui.UIObject;

import java.util.LinkedList;

/**
 * Created by esia on 19/06/17.
 */
public class UILayer<T extends UIObject> {
	protected LinkedList<T> elements = new LinkedList<>();

	public int objectNbr() {
		return elements.size();
	}

	public void addOnTop(T obj) {
		elements.addLast(obj);
	}

	public void addOnBottom(T obj) {
		elements.addFirst(obj);
	}

	public void insert(int i, T obj) {
		elements.add(i, obj);
	}

	public boolean remove(T obj) {
		return elements.remove(obj);
	}

	public T remove(int i) {
		return elements.remove(i);
	}

	public int indexOf(T obj) {
		return elements.indexOf(obj);
	}

	public T get(int i) {
		return elements.get(i);
	}
}
