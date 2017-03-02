package oop.designpatterns.lab1;

import java.util.Iterator;

/**
 * Created by Anton on 2017-03-02.
 */
public class OurArrayList<T> implements IList<T>, Iterable{

    private T[] arr = (T[])new Object[10];
    private int size = 0;

    @Override
    public void add(T o) {
        if(size==arr.length)
            increaseSize();
        arr[size] = o;
        size++;
    }

    @Override
    public void add(int index, T o) {
        if(size==arr.length)
            increaseSize();

        for(int i = size; i > index; i--){
            arr[i] = arr[i-1];
        }

        arr[index] = o;
    }

    @Override
    public T get(int index) {
        return arr[index];
    }

    @Override
    public void remove(T o) {
        for(int i = 0; i < size; i++){
            if(o.equals(arr[i])){
                remove(i);
            }
        }
    }

    @Override
    public void remove(int index) {
        for(int i = index; i < size-1; i++){
            arr[i] = arr[i+1];
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    private void increaseSize(){

        T[] newArr = (T[])new Object[(arr.length*2)];

        for(int i = 0; i < size(); i++){
            newArr[i] =  arr[i];
        }

        arr = newArr;
    }

    @Override
    public Iterator iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator{
        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor<size();
        }

        @Override
        public T next() {
            return arr[cursor++];
        }
    }

}
