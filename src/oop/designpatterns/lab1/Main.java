package oop.designpatterns.lab1;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {



        OurLinkedList<Integer> linkedNumbers = new OurLinkedList<>();
        for(int i = 0; i < 11; i++){
            linkedNumbers.add(i);
        }

        System.out.println("SIZE: "+ linkedNumbers.size());
        linkedNumbers.add(4,10);
        for (int i = 1; i < linkedNumbers.size(); i++){
            System.out.println(linkedNumbers.get(i));
        }
        System.out.println("----------");

        linkedNumbers.remove((Integer)10);
        for (int i = 1; i < linkedNumbers.size(); i++){
            System.out.println(linkedNumbers.get(i));
        }


    }
}
