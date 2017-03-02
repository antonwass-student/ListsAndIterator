package oop.designpatterns.lab1;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	    List<Integer> list = new OurArrayList<>();

        //funcTest(list);

        //performanceTest(new ArrayList<Integer>());
        //performanceTest(list);


        OurLinkedList<Integer> linkedList = new OurLinkedList<>();

        for(int i = 0; i < 100; i++)
            linkedList.add(i);

        linkedList.add(8, 10000);
        linkedList.add(2, 10000);
        linkedList.remove(3);
        linkedList.remove((Integer)5);

        for(Integer i : linkedList)
            System.out.println(i);


    }

    public static void funcTest(List<Integer> list){
        Collection c = new ArrayList<Integer>();

        for(int i = -10; i < 10;i++)
            c.add(i);


        for(int i = 0; i < 1000; i++){
            list.add(i);
        }

        System.out.println("Testing Iterator");
        Iterator<Integer> it = list.iterator();

        while(it.hasNext()){
            int i = it.next();
            if(i == 500)
                it.remove();
            System.out.println(i);
        }

        ListIterator<Integer> lit = list.listIterator();

        System.out.println("Testing ListIterator");
        System.out.println("hasNext() " + lit.hasNext());
        System.out.println("hasPrevious() " + lit.hasPrevious());
        System.out.println("next() " + lit.next());
        System.out.println("next() " + lit.next());
        System.out.println("previous() " + lit.previous());
        System.out.println("next() " + lit.next());
        System.out.println("next() " + lit.next());
        System.out.println("next() " + lit.next());


        System.out.println("Contains(597): " + list.contains(3));
        System.out.println("ContainsAll(-10....10)" + list.containsAll(c));
        System.out.println("IndexOf(10): " + list.indexOf(10));
        System.out.println("LastIndexOf(1): " + list.lastIndexOf(1));
        System.out.println("IsEmpty(): " + list.isEmpty());
        System.out.println("Size(): " + list.size());

        System.out.println("subList(100,110)");
        for(Integer i : list.subList(100,110)){
            System.out.println(i);
        }

        System.out.println("clear()"); list.clear();
        System.out.println("size() " + list.size());
    }



    public static void performanceTest(List<Integer> list){

        long startTimeAdd = System.currentTimeMillis();

        for(int i = 0; i < 1000000; i++){
            list.add(i);
        }

        long stopTimeAdd = System.currentTimeMillis();

        long startTimeRemove = System.currentTimeMillis();

        for(int i = 0; i < 100; i++)
            list.remove(i);

        long stopTimeRemove = System.currentTimeMillis();

        System.out.println("Add: " + (stopTimeAdd-startTimeAdd) + " ms");
        System.out.println("Remove: " + (stopTimeRemove-startTimeRemove) + " ms");

    }
}
