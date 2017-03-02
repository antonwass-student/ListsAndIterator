package oop.designpatterns.lab1;

/**
 * Created by chris on 2017-03-02.
 */
public class OurNode<T>{

    private T data;
    private OurNode next;

    public OurNode(T item){
        data = item;
        next = null;
    }

    public T getData(){
        return data;
    }

    public OurNode getNext(){
        return next;
    }

    public void setNext(OurNode next){
        this.next = next;
    }
}