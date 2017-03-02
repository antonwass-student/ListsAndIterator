package oop.designpatterns.lab1;

public class OurLinkedList<T> implements IList {
	private OurNode head;
	private int count;

	public OurLinkedList(){
		head = new OurNode(null);
		count = 0;
	}

	@Override
	public void add(Object o) {

	}

	@Override
	public void add(int index, Object o) {

	}

	@Override
	public Object get(int index) {
		return null;
	}

	@Override
	public void remove(Object o) {

	}

	@Override
	public void remove(int index) {

	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}