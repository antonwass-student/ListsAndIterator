package oop.designpatterns.lab1;

import javax.naming.OperationNotSupportedException;
import java.util.*;

/**
 * Created by Anton on 2017-03-02.
 */
public class OurArrayList<T> extends AbstractList<T> {

    private T[] arr = (T[])new Object[10];
    private int size = 0;

    @Override
    public boolean add(T o) {
        if(size==arr.length)
            increaseSize();
        arr[size] = o;
        size++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        for(int i = 0; i < size; i++){
            if(o.equals(arr[i])){
                remove(i);
                size--;
                return true;
            }
        }
        return false;
    }


    @Override
    public void clear() {
        arr = (T[])new Object[10];
        size = 0;
    }

    @Override
    public void add(int index, T o) {

        if(index > size() || index < 0)
            throw new IndexOutOfBoundsException();

        for(int i = size; i > index; i--){
            arr[i] = arr[i-1];
        }

        arr[index] = o;
    }

    @Override
    public T get(int index) {

        if(index < 0 || index > size())
            throw new IndexOutOfBoundsException();

        return arr[index];
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {

        if(index < 0 || index > size())
            throw new IndexOutOfBoundsException();

        T temp = arr[index];

        System.arraycopy(arr, index, arr, index+1, size-index);

        /*
        for(int i = index; i < size-1; i++){
            arr[i] = arr[i+1];
        }
        */

        size--;
        return temp;
    }


    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if(index < 0  || index > size())
            throw new IndexOutOfBoundsException();


        return new MyListIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {

        if(fromIndex > toIndex || toIndex > size() || fromIndex < 0)
            throw new IndexOutOfBoundsException();

        return null;
    }


    public boolean equals(Object o){

        if(!(o instanceof List))
            return false;

        List other = (List)o;

        if(other.size()!=size())
            return false;

        for(int i = 0; i < size(); i++){
            if(!arr[i].equals(other.get(i)))
                return false;
        }

        return true;
    }

    private void increaseSize(){

        T[] newArr = (T[])new Object[(arr.length*2)];

        for(int i = 0; i < size(); i++){
            newArr[i] =  arr[i];
        }

        arr = newArr;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class SubList extends AbstractList<T>{

        private int floor, roof;

        public SubList(int fromIndex, int toIndex){
            this.floor = fromIndex;
            this.roof = toIndex;
        }

        @Override
        public int size() {
            return roof - floor;
        }

        @Override
        public Iterator<T> iterator() {
            return new SubIterator();
        }

        @Override
        public boolean add(T t) {
            OurArrayList.this.add(roof, t);
            roof++;
            return true;
        }

        @Override
        public boolean remove(Object o) {
            int index = OurArrayList.this.indexOf(o);
            if(index > floor && index < roof){
                roof--;
                return OurArrayList.this.remove(o);
            }
            else{
                return false;
            }
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            return OurArrayList.this.addAll(c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends T> c) {
            return OurArrayList.this.addAll(index, c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return OurArrayList.this.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return OurArrayList.this.retainAll(c);
        }

        @Override
        public void clear() {
            Iterator it = iterator();

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
            if(floor + index > roof)
                throw new IndexOutOfBoundsException();

            return OurArrayList.this.get(floor+index);
        }

        @Override
        public T set(int index, T element) {
            return OurArrayList.this.set(index, element);
        }

        @Override
        public void add(int index, T element) {
            if(floor + index > roof)
                throw new IndexOutOfBoundsException();

            OurArrayList.this.add(floor+index, element);
            roof++;
        }

        @Override
        public T remove(int index) {
            if(floor + index > roof)
                throw new IndexOutOfBoundsException();
            roof--;
            return OurArrayList.this.remove(floor+index);
        }

        @Override
        public ListIterator<T> listIterator() {
            return new MyListIterator();
        }

        @Override
        public ListIterator<T> listIterator(int index) {
            return new MyListIterator(index);
        }

        @Override
        public List<T> subList(int fromIndex, int toIndex) {
            return new SubList(fromIndex, toIndex);
        }

        private class SubIterator implements Iterator<T>{
            int cursor = floor;
            boolean removed = false;

            @Override
            public boolean hasNext() {
                return cursor < roof;
            }

            @Override
            public T next() {
                if(cursor == roof)
                    throw new NoSuchElementException();

                return arr[cursor++];
            }

            @Override
            public void remove() {
                if(removed)
                    throw new IllegalStateException();
                roof--;
                OurArrayList.this.remove(cursor - 1);
                removed = true;
            }
        }

        private class SubListIterator implements ListIterator<T>{
            boolean removed = false, added = false, traversed = false;

            int cursor = floor;

            @Override
            public boolean hasNext() {
                return cursor < roof;
            }

            @Override
            public T next() {
                if(cursor==roof)
                    throw new NoSuchElementException();

                removed = false;
                added = false;
                traversed = true;
                return arr[cursor++];
            }

            @Override
            public boolean hasPrevious() {
                return cursor > floor;
            }

            @Override
            public T previous() {
                if(cursor == floor)
                    throw new NoSuchElementException();
                removed = false;
                added = false;
                traversed = true;
                return arr[--cursor];
            }

            @Override
            public int nextIndex() {
                if(cursor==roof)
                    return roof - floor;
                return cursor + 1 - floor;
            }

            @Override
            public int previousIndex() {
                if(cursor == floor)
                    return -1;
                return cursor - 1 - floor;
            }

            @Override
            public void remove() {
                if(added)
                    throw new IllegalStateException();
                roof--;
                OurArrayList.this.remove(cursor);
                removed = true;
            }

            @Override
            public void set(T t) {
                if(removed || added || !traversed)
                    throw new IllegalStateException();

                OurArrayList.this.remove(cursor - 1);
                OurArrayList.this.add(cursor - 1, t);
            }

            @Override
            public void add(T t) {
                OurArrayList.this.add(cursor, t);
                roof++;
                added = true;
            }
        }
    }

    private class MyIterator implements Iterator<T>{
        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor<size();
        }

        @Override
        public T next() {
            return arr[cursor++];
        }

        @Override
        public void remove() {
            OurArrayList.this.remove(cursor-1);
        }
    }

    private class MyListIterator implements ListIterator<T>{

        boolean added = false,
                removed = false,
                isTraversedBackward=false,
                isTraversedForward=false;

        int cursor = 0;

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

            isTraversedForward = true;
            removed = false;
            added = false;

            return arr[cursor++];
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public T previous() {
            if(cursor <= 0)
                throw new NoSuchElementException();

            isTraversedBackward = true;
            removed = false;
            added = false;

            return arr[--cursor];
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

            OurArrayList.this.remove(cursor);
            removed = true;
        }

        @Override
        public void set(T t) {
            if(removed || added)
                throw new IllegalStateException();
            if(!isTraversedBackward || !isTraversedForward)
                throw new IllegalStateException();

            OurArrayList.this.remove(cursor);
            OurArrayList.this.add(cursor, t);
        }

        @Override
        public void add(T t) {
            OurArrayList.this.add(cursor, t);
            added = true;
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

}
