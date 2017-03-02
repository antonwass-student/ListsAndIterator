package oop.designpatterns.lab1;

/**
 * Created by chris on 2017-03-02.
 */
public class OurNode<T>{

    private T data;
    private OurNode<T> next;
    private OurNode<T> prev;

    public OurNode(T item){
        data = item;
        next = null;
    }

    public T getData(){
        return data;
    }

    public OurNode<T> getNext(){
        return next;
    }


    public void setNext(OurNode<T> next) {
        this.next = next;
    }

    public OurNode<T> getPrev() {
        return prev;
    }

    public void setPrev(OurNode<T> prev) {
        this.prev = prev;
    }
}