package oop.designpatterns.lab1;

/**
 * Created by chris on 2017-03-02.
 */
public class OurLinkedList<T> implements IList<T> {
	private OurNode<T> head;
	private int count;

	public OurLinkedList(){
		head = new OurNode(null);
		count = 0;
	}


    @Override
    public void add(T o) {

        OurNode<T> temp = new OurNode(o);
        OurNode<T> curr = head;

        while (curr.getNext() != null) {
            curr = curr.getNext();
        }

        curr.setNext(temp);
        count++;
    }

    @Override
    public void add(int index, T o) {
        OurNode<T> temp = new OurNode(o);
        OurNode<T> curr = head;

        for (int i = 1; (i < index && curr.getNext() != null) ; i++) {
            curr = curr.getNext();
        }
        temp.setNext(curr.getNext());
        curr.setNext(temp);
        count++;
    }

    @Override
    public T get(int index) {
        if(index <= 0)
            return null;
	    OurNode<T> curr = head.getNext();
	    for (int i = 1; i < index; i++){
	        if (curr.getNext() == null){
                return null;
            }

            curr = curr.getNext();
        }
        return curr.getData();
    }

    @Override
    public void remove(T o) {


    }


    @Override
    public void remove(int index) {
        if (index < 1 || index > size()){
            return;
        }

        OurNode<T> curr = head;

        for (int i = 1; i < index; i++){
            if (curr.getNext() == null)
                return;

            curr = curr.getNext();
        }
        curr.setNext(curr.getNext().getNext());
        count--;
    }

    @Override
	public int size() {
		return count;
	}

	@Override
	public boolean isEmpty() {
	    return head == null;
	}
}