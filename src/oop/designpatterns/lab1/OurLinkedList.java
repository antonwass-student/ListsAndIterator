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
        temp.setPrev(curr);

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
        temp.setPrev(curr);
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
        prev.setPrev(curr.getPrev());
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
        curr.setPrev(curr.getPrev().getPrev());
        curr.setNext(curr.getNext().getNext());
        size--;

        return temp;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        return new MyListIterator(i);
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }


    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if(fromIndex < 0 || toIndex > size() || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();

        return new SubList(fromIndex, toIndex);
    }

    private class MyIterator implements Iterator<T> {
        OurNode<T> cursor = head;
        OurNode<T> prev = null;
        boolean removed = false;
        int startSize = size();

        @Override
        public boolean hasNext() {
            return cursor.getNext() != null;
        }

        @Override
        public T next() {
            if(cursor.getNext()==null)
                throw new NoSuchElementException();

            if(startSize != size())
                throw new ConcurrentModificationException();

            prev = cursor;
            cursor = cursor.getNext();

            removed = false;

            return cursor.getData();
        }

        @Override
        public void remove() {
            if(removed)
                throw new IllegalStateException();

            if(startSize != size())
                throw new ConcurrentModificationException();

            startSize--;
            prev.setNext(cursor.getNext());
            removed = true;
        }
    }

    private class MyListIterator implements ListIterator<T>{

        boolean added = false,
                removed = false,
                isTraversedBackward=false,
                isTraversedForward=false;

        int cursor = 0;

        int startSize = OurLinkedList.this.size();

        public MyListIterator(int index){
            cursor = index;
        }

        public MyListIterator(){}

        @Override
        public boolean hasNext() {
            return cursor < size();
        }

        @Override
        public T next() {
            if(cursor >= size)
                throw new NoSuchElementException();

            if(startSize != OurLinkedList.this.size())
                throw new ConcurrentModificationException();

            isTraversedForward = true;
            removed = false;
            added = false;

            return get(cursor++);
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public T previous() {
            if(cursor <= 0)
                throw new NoSuchElementException();

            if(startSize != OurLinkedList.this.size())
                throw new ConcurrentModificationException();

            isTraversedBackward = true;
            removed = false;
            added = false;

            return get(--cursor);
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            if(removed || added)
                throw new IllegalStateException();
            if(!isTraversedBackward || !isTraversedForward)
                throw new IllegalStateException();
            if(startSize != OurLinkedList.this.size())
                throw new ConcurrentModificationException();

            startSize--;
            OurLinkedList.this.remove(cursor);
            removed = true;
        }

        @Override
        public void set(T t) {
            if(removed || added)
                throw new IllegalStateException();
            if(!isTraversedBackward || !isTraversedForward)
                throw new IllegalStateException();
            if(startSize != OurLinkedList.this.size())
                throw new ConcurrentModificationException();

            OurLinkedList.this.remove(cursor);
            OurLinkedList.this.add(cursor, t);
        }

        @Override
        public void add(T t) {
            if(startSize != OurLinkedList.this.size())
                throw new ConcurrentModificationException();
            startSize++;
            OurLinkedList.this.add(cursor, t);
            added = true;
        }
    }

    // ---------------------------------------------------------
    // ---------------------------------------------------------
    // ---------------------------------------------------------
    // ---------------------------------------------------------

    private class SubList extends AbstractList<T>{

        private int floor, roof;
        private int subSize;

        public SubList(int fromIndex, int toIndex){
            this.floor = fromIndex;
            this.roof = toIndex;
            this.subSize = roof - floor;
        }

        @Override
        public Iterator<T> iterator() {
            return new SubIterator();
        }

        @Override
        public boolean add(T t) {
            OurLinkedList.this.add(roof, t);
            subSize++;
            return true;
        }

        @Override
        public boolean remove(Object o) {

            int index = OurLinkedList.this.indexOf(o);

            if(index >= floor && index <= floor+subSize){
                if(OurLinkedList.this.remove(o)){
                    subSize--;
                    return true;
                }else
                    return false;
            }
            else{
                return false;
            }
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            return OurLinkedList.this.addAll(c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends T> c) {
            return OurLinkedList.this.addAll(index, c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return OurLinkedList.this.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return OurLinkedList.this.retainAll(c);
        }

        @Override
        public void clear() {

            Iterator it = SubList.this.iterator();

            if(!it.hasNext())
                return;

            it.next();

            while(it.hasNext()){
                it.remove();
                it.next();
            }
        }

        @Override
        public T get(int index) {
            if(index > subSize)
                throw new IndexOutOfBoundsException();

            return OurLinkedList.this.get(floor+index);
        }

        @Override
        public T set(int index, T element) {
            return OurLinkedList.this.set(index, element);
        }

        @Override
        public void add(int index, T element) {
            if(index > subSize)
                throw new IndexOutOfBoundsException();

            OurLinkedList.this.add(floor+index, element);
            //subSize++;
        }

        @Override
        public T remove(int index) {
            if(floor + index > roof)
                throw new IndexOutOfBoundsException();
            subSize--;
            return OurLinkedList.this.remove(floor+index);
        }

        @Override
        public ListIterator<T> listIterator() {
            return new SubList.SubIterator();
        }

        @Override
        public ListIterator<T> listIterator(int index) {
            return new SubList.SubIterator(index);
        }

        @Override
        public List<T> subList(int fromIndex, int toIndex) {
            return new SubList(fromIndex, toIndex);
        }

        private class SubIterator implements ListIterator<T>{
            int cursor = 0;
            boolean removed = false, added = false, traversed = false;
            int lastRet = -1;

            int startSize = OurLinkedList.this.size();

            public SubIterator(int index){
                cursor = index;
            }

            public SubIterator(){
            }

            @Override
            public boolean hasNext() {
                return cursor <= subSize;
            }

            @Override
            public T next() {
                if(cursor > subSize)
                    throw new NoSuchElementException();

                System.out.println("next: " + startSize + " " + OurLinkedList.this.size());

                if(startSize != OurLinkedList.this.size())
                    throw new ConcurrentModificationException();

                removed = false;
                added = false;
                traversed = true;

                lastRet = cursor;
                return SubList.this.get(cursor++);
            }

            @Override
            public boolean hasPrevious() {
                return cursor > floor;
            }

            @Override
            public T previous() {
                if(cursor == 0)
                    throw new NoSuchElementException();

                if(startSize != OurLinkedList.this.size())
                    throw new ConcurrentModificationException();

                removed = false;
                added = false;
                traversed = true;
                lastRet = cursor;
                return SubList.this.get(--cursor);
            }

            @Override
            public void remove() {
                if(lastRet < 0 || added || removed || !traversed)
                    throw new IllegalStateException();

                if(startSize != OurLinkedList.this.size())
                    throw new ConcurrentModificationException();

                startSize--;

                SubList.this.remove(lastRet);
                cursor = lastRet;
                removed = true;
            }

            @Override
            public int nextIndex() {
                if(cursor==subSize)
                    return subSize;
                return cursor + 1;
            }

            @Override
            public int previousIndex() {
                if(cursor == floor)
                    return -1;
                return cursor - 1;
            }

            @Override
            public void set(T t) {
                if(removed || added || !traversed)
                    throw new IllegalStateException();

                if(startSize != OurLinkedList.this.size())
                    throw new ConcurrentModificationException();

                SubList.this.remove(cursor - 1);
                SubList.this.add(cursor - 1, t);
            }

            @Override
            public void add(T t) {

                if(startSize != OurLinkedList.this.size())
                    throw new ConcurrentModificationException();

                startSize++;
                SubList.this.add(cursor, t);
                added = true;
            }
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