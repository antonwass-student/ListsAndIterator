package oop.designpatterns.lab1;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
	    OurArrayList<Integer> numbers = new OurArrayList<>();

        for(int i = 0; i < 100; i++){
            numbers.add(i);
        }

        Iterator<Integer> it = numbers.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }

        for(Integer i : numbers)



    }
}
