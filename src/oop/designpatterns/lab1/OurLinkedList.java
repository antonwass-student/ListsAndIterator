package oop.designpatterns.lab1;

import java.util.*;

/**
 * Created by chris on 2017-03-02.
 */
public class OurLinkedList<T> extends AbstractList<T> {
	private OurNode<T> head;


	@Override
    public boolean add(T o) {

        if(size() == 0){
            head = new OurNode<T>(o);
            size++;
            return true;
        }

        OurNode<T> temp = new OurNode(o);
        OurNode<T> curr = head;

        while (curr.getNext() != null) {
            curr = curr.getNext();
        }

        curr.setNext(temp);
        size++;

        return true;
    }

    @Override
    public void add(int index, T o) {

        if(index < 0 || index > size())
            throw new IndexOutOfBoundsException();

        OurNode<T> temp = new OurNode(o);
        OurNode<T> curr = head;

        for (int i = 1; (i < index && curr.getNext() != null) ; i++) {
            curr = curr.getNext();
        }
        temp.setNext(curr.getNext());
        curr.setNext(temp);
        size++;
    }



    @Override
    public T get(int index) {
        if(index < 0 || index >= size())
            throw new IndexOutOfBoundsException();

	    OurNode<T> curr = head;

	    for (int i = 0; i < index; i++){
	        if (curr.getNext() == null){
                return null;
            }

            curr = curr.getNext();
        }
        return curr.getData();
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public boolean remove(Object o) {
        OurNode<T> curr = head;
        OurNode<T> prev = null;

        while(!curr.getData().equals(o)){
            prev = curr;
            curr = curr.getNext();

            if(curr == null)
                return false;
        }

        prev.setNext(curr.getNext());
        size--;
        return true;
    }


    @Override
    public T remove(int index) {
        if (index < 0 || index > size()){
            throw new IndexOutOfBoundsException();
        }

        if(size() == 1){
            T temp = head.getData();
            head = null;
            size = 0;
            return temp;
        }

        OurNode<T> curr = head;

        for (int i = 0; i < index-1; i++){
            curr = curr.getNext();
        }
        T temp = curr.getNext().getData();
        curr.setNext(curr.getNext().getNext());
        size--;

        return temp;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }


    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if(fromIndex < 0 || toIndex > size() || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();

        return null;
    }

    private class MyIterator implements Iterator<T> {
        OurNode<T> cursor = head;
        OurNode<T> prev = null;
        boolean removed = false;

        @Override
        public boolean hasNext() {
            return cursor.getNext() != null;
        }

        @Override
        public T next() {
            if(cursor.getNext()==null)
                throw new NoSuchElementException();

            prev = cursor;
            cursor = cursor.getNext();

            removed = false;

            return cursor.getData();
        }

        @Override
        public void remove() {
            if(removed)
                throw new IllegalStateException();
            prev.setNext(cursor.getNext());
            removed = true;
        }
    }

    private class MyListIterator implements ListIterator<T>{

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public T previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(T t) {

        }

        @Override
        public void add(T t) {

        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }
}