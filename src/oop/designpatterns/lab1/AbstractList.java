package oop.designpatterns.lab1;

import java.util.Collection;
import java.util.List;

/**
 * Created by Anton on 2017-03-02.
 */
public abstract class AbstractList<T> implements List<T> {
    int size = 0;

    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
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
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public int indexOf(Object o) {
        int counter = 0;
        for(Object obj : this){
            if(obj.equals(o))
                return counter;
            counter++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastOccurence = -1;
        int counter = 0;
        for(Object obj : this){
            if(obj.equals(o))
                lastOccurence = counter;
            counter++;
        }

        return lastOccurence;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {

        if(a.length < size()){
            a = (T1[]) new Object[size()];
        }
        else if(a.length > size()){
            a[size()] = null;
        }

        int counter = 0;

        for(Object obj : this){
            a[counter++] = (T1)obj;
        }

        return a;
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size()];

        int counter = 0;

        for(Object o : this){
            arr[counter++] = o;
        }

        return arr;
    }

    @Override
    public boolean equals(Object o){

        if(!(o instanceof List))
            return false;

        List other = (List)o;

        if(other.size()!=size())
            return false;

        int counter = 0;
        for(Object obj : this){
            if(!obj.equals(other.get(counter++)))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode(){
        int hashCode = 1;

        for(T t : this)
            hashCode = 31*hashCode + (t==null? 0 : t.hashCode());

        return hashCode;
    }
}
