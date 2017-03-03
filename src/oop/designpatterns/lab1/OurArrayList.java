package oop.designpatterns.lab1;

import javax.naming.OperationNotSupportedException;
import java.util.*;

/**
 * Created by Anton on 2017-03-02.
 */
public class OurArrayList<T> extends AbstractList<T> {

    private T[] arr = (T[])new Object[10];

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

        for(int i = index; i < size-1; i++){
            arr[i] = arr[i+1];
        }


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

        return new SubList(fromIndex, toIndex);
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
            OurArrayList.this.add(roof, t);
            subSize++;
            return true;
        }

        @Override
        public boolean remove(Object o) {

            int index = OurArrayList.this.indexOf(o);

            if(index >= floor && index <= floor+subSize){
                if(OurArrayList.this.remove(o)){
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

            return OurArrayList.this.get(floor+index);
        }

        @Override
        public T set(int index, T element) {
            return OurArrayList.this.set(index, element);
        }

        @Override
        public void add(int index, T element) {
            if(index > subSize)
                throw new IndexOutOfBoundsException();

            OurArrayList.this.add(floor+index, element);
            //subSize++;
        }

        @Override
        public T remove(int index) {
            if(floor + index > roof)
                throw new IndexOutOfBoundsException();
            subSize--;
            return OurArrayList.this.remove(floor+index);
        }

        @Override
        public ListIterator<T> listIterator() {
            return new SubIterator();
        }

        @Override
        public ListIterator<T> listIterator(int index) {
            return new SubIterator(index);
        }

        @Override
        public List<T> subList(int fromIndex, int toIndex) {
            return new SubList(fromIndex, toIndex);
        }

        private class SubIterator implements ListIterator<T>{
            int cursor = 0;
            boolean removed = false, added = false, traversed = false;
            int lastRet = -1;

            int startSize = OurArrayList.this.size();

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

                if(startSize != OurArrayList.this.size())
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

                if(startSize != OurArrayList.this.size())
                    throw new ConcurrentModificationException();

                removed = false;
                added = false;
                traversed = true;
                lastRet = cursor;
                return SubList.this.get(--cursor);
            }

            @Override
            public void remove() {
                if(added || removed || !traversed)
                    throw new IllegalStateException();

                if(startSize != OurArrayList.this.size())
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

                if(startSize != OurArrayList.this.size())
                    throw new ConcurrentModificationException();

                SubList.this.remove(cursor - 1);
                SubList.this.add(cursor - 1, t);
            }

            @Override
            public void add(T t) {

                if(startSize != OurArrayList.this.size())
                    throw new ConcurrentModificationException();

                startSize++;
                SubList.this.add(cursor, t);
                added = true;
            }
        }
    }

    private class MyIterator implements Iterator<T>{
        int cursor = 0;

        int startSize = OurArrayList.this.size();

        @Override
        public boolean hasNext() {
            return cursor<size();
        }

        @Override
        public T next() {
            if(startSize != OurArrayList.this.size())
                throw new ConcurrentModificationException();
            return arr[cursor++];
        }

        @Override
        public void remove() {
            if(startSize != OurArrayList.this.size())
                throw new ConcurrentModificationException();
            startSize--;
            OurArrayList.this.remove(cursor-1);
        }
    }

    private class MyListIterator implements ListIterator<T>{

        boolean added = false,
                removed = false,
                isTraversedBackward=false,
                isTraversedForward=false;

        int cursor = 0;

        int startSize = OurArrayList.this.size();

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

            if(startSize != OurArrayList.this.size())
                throw new ConcurrentModificationException();

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

            if(startSize != OurArrayList.this.size())
                throw new ConcurrentModificationException();

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

            if(startSize != OurArrayList.this.size())
                throw new ConcurrentModificationException();

            startSize--;
            OurArrayList.this.remove(cursor);
            removed = true;
        }

        @Override
        public void set(T t) {
            if(removed || added)
                throw new IllegalStateException();
            if(!isTraversedBackward || !isTraversedForward)
                throw new IllegalStateException();

            if(startSize != OurArrayList.this.size())
                throw new ConcurrentModificationException();

            OurArrayList.this.remove(cursor);
            OurArrayList.this.add(cursor, t);
        }

        @Override
        public void add(T t) {

            if(startSize != OurArrayList.this.size())
                throw new ConcurrentModificationException();

            startSize++;
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
