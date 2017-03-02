package oop.designpatterns.lab1;

/**
 * Created by Anton on 2017-03-02.
 */
public interface IList<T> {
    void add(T o);
    void add(int index, T o);
    T get(int index);
    void remove(T o);
    void remove(int index);
    int size();
    boolean isEmpty();

}
