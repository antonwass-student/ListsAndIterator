package oop.designpatterns.lab1;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {



        OurLinkedList<Integer> linkedNumbers = new OurLinkedList<>();
        for(int i = 0; i < 100; i++){
            linkedNumbers.add(i);
        }

        linkedNumbers.add(2,4);
        System.out.println("SIZE: "+ linkedNumbers.size());

        for (int i = 0; i < linkedNumbers.size(); i++){
            System.out.println(linkedNumbers.get(i));
        }


    }
}
