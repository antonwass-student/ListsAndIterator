package oop.designpatterns.lab1;

import java.util.*;

/**
 * Created by Anton on 2017-03-02.
 */
public class OurArrayList<T> implements List<T> {

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
    public boolean containsAll(Collection<?> c) {

        for(Object o : c){
            if(!contains(o))
                return false;
        }

        return true;
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
    public int indexOf(Object o) {
        for(int i = 0; i < size(); i++)
        {
            if(o.equals(arr[i]))
                return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for(int i = size()-1; i > -1 ; i--)
        {
            if(o.equals(arr[i]))
                return i;
        }
        return -1;
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

        List<T> sub = new OurArrayList<T>();

        for(int i = fromIndex; i < toIndex;i++){
            sub.add(arr[i]);
        }

        return sub;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
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

    public int hashCode(){
        int hashCode = 1;

        for(T t : this)
            hashCode = 31*hashCode + (t==null? 0 : t.hashCode());

        return hashCode;
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

    @Override
    public Object[] toArray() {
        Object[] newArr = new Object[size()];

        for(int i = 0; i < size; i++)
            newArr[i] = arr[i];

        return newArr;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if(a.length < size())
            a = (T1[]) new Object[size()];

        if(a.length > size()){
            a[size()] = null;
        }

        for(int i = 0; i < size(); i++){
            a[i] = (T1)arr[i];
        }

        return a;
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

}
