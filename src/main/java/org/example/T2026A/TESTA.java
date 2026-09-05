package org.example.T2026A;

import org.example.BinNode;
import org.example.Node;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("ALL")
public class TESTA {
    // 1 - O(n)
// O(n)
    public static boolean isUpDown(Stack<Integer> stk) {
        // validate that the stack is not empty.
        if (stk.isEmpty()) return false;

        // create a temp stack
        var temp = new Stack<Integer>();

        // create a variable to check if part of the stack is sorted ascending
        var goUp = false;

        // create a variable to check if part of the stack is sorted descending
        var goDown = false;


        // check ascending order //
        while (!stk.isEmpty()) {

            // pop the top element
            var current = stk.pop();

            // if this is the last element,
            // push it to temp and finish
            if (stk.isEmpty()) {
                temp.push(current);
                break;
            }

            // peek the next element
            var next = stk.peek();

            // check if the current element is less than the next element
            if (current < next) {

                // set the goUp variable to true
                if (!goUp) {
                    goUp = true;
                }

                // push the current element to the temp stack
                temp.push(current);
            }

            // equality is not allowed
            else if (current.equals(next)) {

                // push current element to temp
                temp.push(current);

                // move all remaining elements to temp
                while (!stk.isEmpty()) {
                    temp.push(stk.pop());
                }

                // restore the original stack
                while (!temp.isEmpty()) {
                    stk.push(temp.pop());
                }

                return false;
            }

            // current > next:
            // reached the point where the sequence starts descending
            else {
                temp.push(current);
                break;
            }
        }


        // check in the middle //
        if (stk.isEmpty()) {
            while (!temp.isEmpty()) {
                stk.push(temp.pop());
            }

            return false;
        }


        // check descending order //
        while (!stk.isEmpty()) {

            // pop the top element
            var current = stk.pop();

            // if this is the last element,
            // push it to temp and finish
            if (stk.isEmpty()) {
                temp.push(current);
                break;
            }

            // peek the next element
            var next = stk.peek();

            // check if the current element is greater than the next element
            if (current > next) {

                if (!goDown) {
                    goDown = true;
                }

                // push the current element to the temp stack
                temp.push(current);
            }

            // current <= next is invalid in the descending part
            else {

                // push current element to temp
                temp.push(current);

                // empty the original stack into temp
                while (!stk.isEmpty()) {
                    temp.push(stk.pop());
                }

                // restore the original stack
                while (!temp.isEmpty()) {
                    stk.push(temp.pop());
                }

                return false;
            }
        }


        // restore the stack
        while (!temp.isEmpty()) {
            stk.push(temp.pop());
        }

        // return true only if there was both ascending and descending movement
        return goUp && goDown;
    }

    // 2 - a - O(n)
    public static int valueAt(Node<Integer> ch, int pos){

        // validate that pos is positive
        // and that the linked list is not empty
        if (pos <= 0 || ch == null) return -1;

        // if pos is 1, return the value
        // of the first node
        if (pos == 1) return ch.getValue();

        // create a counter that represents
        // the current position in the linked list
        var count = 1;

        // go through the linked list
        while (ch != null){

            // get the value of the current node
            var value = ch.getValue();

            // if the current position is equal to pos,
            // return the value of the current node
            if (count == pos) return value;

            // move to the next node
            ch = ch.getNext();

            // increase the position counter
            count++;
        }

        // if pos does not exist in the linked list,
        // return -1
        return -1;
    }

    // 2 - b
    // This function receives the first node of a singly linked list
    // and creates a new doubly linked list with the same values.
    //
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    private static <T> BinNode<T> makeBinFromNod(Node<T> chain){
        // if the original list is empty, return null
        if(chain == null) return null;

        // create head pointer to the new doubly linked list
        BinNode<T> newey = null;

        // create tail pointer to the new doubly linked list
        BinNode<T> tail = null;

        // create a pointer to iterate through the original list
        var pos = chain;

        // iterate through the singly linked list
        while(pos != null){

            // create a new node with the current value
            var toAdd = new BinNode<>(pos.getValue());

            // if the new list is empty,
            // set both head and tail to the new node
            if(newey == null){
                newey = toAdd;
                tail = toAdd;
            }

            // if the new list is not empty,
            // connect the new node to the end of the list
            else{

                // connect the current tail to the new node
                tail.setRight(toAdd);

                // connect the new node back to the current tail
                tail.getRight().setLeft(tail);

                // update tail to the new last node
                tail = toAdd;
            }

            // move to the next node in the original list
            pos = pos.getNext();
        }

        // return the head of the new doubly linked list
        return newey;
    }


    // This function receives two singly linked lists of equal length.
    // It creates a new list by taking:
    // one value from the beginning of ch1,
    // then one value from the end of ch2,
    // and continues this way until all values are added.
    //
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static Node<Integer> merge(Node<Integer> ch1, Node<Integer> ch2){
        // if one of the lists is empty, return null
        if (ch1 == null || ch2 == null) return null;

        // create a doubly linked copy of the second list
        var reversedSecChain = makeBinFromNod(ch2);

        // move to the last node of the second list
        while (reversedSecChain.getRight() != null){
            reversedSecChain = reversedSecChain.getRight();
        }

        // pointer that moves forward through the first list
        var pos1 = ch1;

        // pointer that moves backward through the second list
        var pos2 = reversedSecChain;

        // head pointer of the result list
        Node<Integer> resultHead = null;

        // tail pointer of the result list
        Node<Integer> resultTail = null;

        // continue while both pointers are valid
        while (pos1 != null && pos2 != null){

            // get the current value from the first list
            var value1 = pos1.getValue();

            // get the current value from the second list
            var value2 = pos2.getValue();

            // create a new node from the first list value
            var firstToAdd = new Node<>(value1);

            // create a new node from the second list value
            var secondToAdd = new Node<>(value2);

            // if the result list is empty,
            // initialize the head and tail
            if (resultTail == null){

                // set the first node as the head
                resultHead = firstToAdd;

                // connect the first node to the second node
                resultHead.setNext(secondToAdd);

                // update the tail to the second node
                resultTail = secondToAdd;
            }

            // if the result list is not empty,
            // add both new nodes to the end
            else {

                // connect the current tail to the first new node
                resultTail.setNext(firstToAdd);

                // connect the first new node to the second new node
                resultTail.getNext().setNext(secondToAdd);

                // update the tail to the second new node
                resultTail = resultTail.getNext().getNext();
            }

            // move forward in the first list
            pos1 = pos1.getNext();

            // move backward in the second list
            pos2 = pos2.getLeft();
        }

        // return the head of the merged list
        return resultHead;
    }

    // 3
    // 3

    public static void q3(){

        // -----------------------------
        // Steering interface
        // -----------------------------
        interface Steering {

            // turn the car to the left
            void turnLeft();

            // turn the car to the right by a given number of degrees
            void turnRight(int degrees);
        }


        // -----------------------------
        // Navigation interface
        // -----------------------------
        interface Navigation {

            // set the destination of the car
            void setDestination(String dest);
        }


        // -----------------------------
        // Sensing interface
        // -----------------------------
        interface Sensing {

            // activate the car's Lidar sensor
            void activateLidar();
        }


        // -----------------------------
        // 3 - a
        // AutonomousCar class
        //
        // The class implements all three interfaces:
        // Steering, Navigation and Sensing.
        //
        // It also contains the constructors required
        // by the code in main.
        // -----------------------------
        class AutonomousCar implements Steering, Navigation, Sensing {

            private String modelName;
            private double batteryLevel;


            // constructor that receives
            // both model name and battery level
            public AutonomousCar(String modelName, double batteryLevel) {}


            // constructor that receives
            // only the model name
            public AutonomousCar(String modelName) {}


            // implementation of Sensing
            @Override
            public void activateLidar() {}


            // implementation of Steering
            @Override
            public void turnLeft() {}


            // implementation of Steering
            @Override
            public void turnRight(int degrees) {}


            // implementation of Navigation
            @Override
            public void setDestination(String dest) {}
        }


        // -----------------------------
        // Simulation of the main method
        // -----------------------------
        Runnable miniMain = () -> {

            // create an AutonomousCar using
            // the constructor with two parameters
            AutonomousCar car1 =
                    new AutonomousCar("Tesla Y", 98.5);

            // create an AutonomousCar using
            // the constructor with one parameter
            AutonomousCar car2 =
                    new AutonomousCar("Waymo One");


            // -----------------------------
            // 1 - RIGHT
            // -----------------------------

            // AutonomousCar implements Steering,
            // therefore car1 can be assigned
            // to a Steering reference.
            //
            // Steering contains turnRight(),
            // therefore this call is valid.

            // Steering s1 = car1;
            // s1.turnRight(45);



            // -----------------------------
            // 2 - WRONG
            // Compilation Error
            // -----------------------------

            // Navigation is an interface.
            // An interface cannot be instantiated
            // directly using "new".
            //
            // Error:
            // 'Navigation' is abstract;
            // cannot be instantiated.

            // Navigation n2 = new Navigation();
            // n2.setDestination("Tel Aviv");



            // -----------------------------
            // 3 - RIGHT
            // -----------------------------

            // n3 is declared as Navigation,
            // but the actual object is AutonomousCar.
            //
            // AutonomousCar implements Navigation,
            // so the first line is valid.
            //
            // The object can also be cast back
            // to AutonomousCar.
            //
            // AutonomousCar implements Steering,
            // therefore it can be assigned
            // to a Steering reference.

            // Navigation n3 =
            //         new AutonomousCar("Audi A8", 60.0);

            // Steering s3 =
            //         (AutonomousCar)n3;



            // -----------------------------
            // 4 - WRONG
            // Compilation Error
            // -----------------------------

            // n4 is declared as Navigation.
            //
            // Although the actual object is
            // an AutonomousCar,
            // the compiler only allows methods
            // that exist in the declared type.
            //
            // Navigation does not contain
            // activateLidar().
            //
            // Therefore this is a compilation error.

            // Navigation n4 =
            //         new AutonomousCar("Mercedes");

            // n4.activateLidar();



            // -----------------------------
            // 5 - RIGHT
            // -----------------------------

            // The actual object is AutonomousCar.
            //
            // AutonomousCar implements Steering,
            // so it can be assigned to s5.
            //
            // AutonomousCar also implements Sensing.
            //
            // Therefore the cast from Steering
            // to Sensing succeeds at runtime.

            // Steering s5 =
            //         new AutonomousCar("Ford");

            // Sensing sn5 =
            //         (Sensing)s5;

            // sn5.activateLidar();



            // -----------------------------
            // 6 - RIGHT
            // -----------------------------

            // sn6 is declared as Sensing,
            // but the actual object is AutonomousCar.
            //
            // Therefore it can be cast
            // to AutonomousCar.
            //
            // AutonomousCar contains turnLeft(),
            // so the first call is valid.
            //
            // AutonomousCar also implements Navigation,
            // therefore the cast to Navigation is valid
            // and setDestination() can be called.

            // Sensing sn6 =
            //         new AutonomousCar("Nissan", 70.0);

            // ((AutonomousCar)sn6).turnLeft();

            // ((Navigation)sn6)
            //         .setDestination("Haifa");
        };
    }

    // 4
    // 4 - a O(n⋅m)
    // This function receives two positive integers
    // and checks if they are "strangers".
    // Two numbers are strangers if they do not have
    // any digit in common.
    //
    // Time Complexity: O(n * m)
    // n = number of digits in num1
    // m = number of digits in num2
    public static boolean areStrangers(int num1, int num2){
        // convert the first number to a String
        var s1 = String.valueOf(num1);

        // convert the second number to a String
        var s2 = String.valueOf(num2);

        // go through every digit in the first number
        for (int i = 0; i < s1.length(); i++) {

            // get the current digit
            var curent = s1.charAt(i);

            // check if the current digit also exists
            // in the second number
            if (s2.indexOf(curent) != -1) return false;
        }

        // go through every digit in the second number
        for (int i = 0; i < s2.length(); i++) {

            // get the current digit
            var curent = s2.charAt(i);

            // check if the current digit also exists
            // in the first number
            if (s1.indexOf(curent) != -1) return false;
        }

        // no common digits were found
        return true;
    }

    // 4 - b
    // This function receives a queue of positive integers.
    // It checks whether every pair of numbers in the queue
    // are strangers to each other.
    //
    // Two numbers are strangers if they do not share
    // any common digit.
    //
    // The function preserves the original queue.
    //
    // Time Complexity: O(n^2)
    // n = number of elements in the queue
    //
    // Space Complexity: O(n)
    // because two helper queues are used.
    public static boolean isStrangersQueue(Queue<Integer> q){

        // external queue used to store elements
        // that were already fully checked
        var EXTERNAL_RESTORE = new LinkedList<Integer>();

        // internal queue used to temporarily store
        // the remaining elements during each comparison round
        var INTERNAL_RESTORE = new LinkedList<Integer>();

        // flag that remains true only if
        // all pairs are strangers
        var allStrangers = true;


        // iterate through the queue
        while (!q.isEmpty()){

            // remove the current element
            var current = q.poll();

            // store the current element in the external queue
            // so it can be restored later
            EXTERNAL_RESTORE.offer(current);


            // compare the current element
            // with all remaining elements in the queue
            while (!q.isEmpty()){

                // remove the next element
                var next = q.poll();

                // store the next element temporarily
                // so the queue can be restored after this round
                INTERNAL_RESTORE.offer(next);

                // check if current and next are strangers
                var areStrangers = areStrangers(current, next);

                // if they are not strangers,
                // set the flag to false
                if (!areStrangers){
                    allStrangers = false;
                }
            }


            // restore all remaining elements
            // back into the original queue
            while (!INTERNAL_RESTORE.isEmpty()){

                q.offer(INTERNAL_RESTORE.poll());
            }
        }


        // restore all elements that were already checked
        // back into the original queue
        while (!EXTERNAL_RESTORE.isEmpty()){

            q.offer(EXTERNAL_RESTORE.poll());
        }


        // return true if every pair of numbers
        // is stranger to each other
        return allStrangers;
    }

    // 5
    public static void q5(){
        /*
        Inheritance Tree:
                       Object
                        |
                    Application
                    /          \
                WebService      MobileApp
                /      \             |
        BackendService FrontendApp DataQuery
        */

        // Given objects from main:
        // Application a1 = new Application();
        // Application a2 = new FrontendApp();
        // Application a3 = new MobileApp();
        // WebService ws1 = new BackendService();
        // MobileApp ma1 = new DataQuery();


        // --------------------------------------------------
        // 1 - WRONG
        // Compilation Error
        // --------------------------------------------------
        //
        // Application is the parent class of WebService.
        // A parent object cannot be assigned directly
        // to a child reference.
        //
        // WebService ws2 = new Application();


        // --------------------------------------------------
        // 2 - WRONG
        // Compilation Error
        // --------------------------------------------------
        //
        // a2 is declared as Application.
        // Even though the actual object is FrontendApp,
        // the compiler only sees the reference type Application.
        //
        // Therefore an explicit cast is required.
        //
        // WebService ws3 = a2;


        // --------------------------------------------------
        // 3 - RIGHT
        // --------------------------------------------------
        //
        // a2 is declared as Application,
        // but the actual object is FrontendApp.
        //
        // FrontendApp extends WebService,
        // therefore casting a2 to WebService is valid.
        //
        // WebService ws4 = (WebService)a2;


        // --------------------------------------------------
        // 4 - WRONG
        // Runtime Error - ClassCastException
        // --------------------------------------------------
        //
        // a3 is declared as Application,
        // and the cast is allowed by the compiler.
        //
        // However, the actual object is MobileApp.
        // MobileApp is NOT a WebService.
        //
        // Therefore the program compiles,
        // but the cast fails at runtime.
        //
        // WebService ws5 = (WebService)a3;


        // --------------------------------------------------
        // 5 - RIGHT
        // --------------------------------------------------
        //
        // ws1 is declared as WebService,
        // but the actual object is BackendService.
        //
        // Therefore casting ws1 to BackendService is valid.
        //
        // BackendService bs1 = (BackendService)ws1;


        // --------------------------------------------------
        // 6 - WRONG
        // Runtime Error - ClassCastException
        // --------------------------------------------------
        //
        // a1 is declared as Application.
        // The compiler allows the cast to MobileApp
        // because MobileApp extends Application.
        //
        // However, the actual object inside a1
        // is a plain Application object.
        //
        // Therefore the cast fails at runtime.
        //
        // MobileApp ma2 = (MobileApp)a1;


        // --------------------------------------------------
        // 7 - RIGHT
        // --------------------------------------------------
        //
        // new DataQuery() creates a DataQuery object.
        //
        // DataQuery extends MobileApp,
        // so casting it to MobileApp is valid.
        //
        // MobileApp also extends Application,
        // therefore the result can be assigned
        // to an Application reference.
        //
        // Application a4 = (MobileApp)(new DataQuery());


        // --------------------------------------------------
        // 8 - WRONG
        // Compilation Error
        // --------------------------------------------------
        //
        // new BackendService() creates a BackendService object.
        //
        // BackendService extends WebService,
        // therefore this cast is valid:
        //
        // (WebService)(new BackendService())
        //
        // However, the result is still a WebService reference.
        //
        // It cannot be assigned directly to FrontendApp,
        // because WebService is a parent of FrontendApp.
        //
        // FrontendApp fa1 = (WebService)(new BackendService());

        // 5 - c - 1
        //
        // The getCost() method should be defined in WebService,
        // because the question asks to calculate the cost
        // of all applications that are WebService objects.
        //
        // BackendService and FrontendApp inherit from WebService,
        // so they can override getCost() if each service type
        // has a different way to calculate its cost.
        //
        // MobileApp and DataQuery do not need getCost(),
        // because they are not WebService objects.
        /*
        class WebService extends Application {
            public double getCost() { return 0; }
        }

        class BackendService extends WebService {
            @Override
            public double getCost() { return 0; }
        }

        class FrontendApp extends WebService {
            @Override
            public double getCost() { return 0; }
        }
        */
        // 5 - c - 2
        //
        // This function receives an array of Application objects
        // and returns the sum of the costs of all objects
        // that are WebService objects.
        //
        // Time Complexity: O(n)
        // Space Complexity: O(1)
        /*
        public static double sumWebServiceCost(Application[] arr){

            // variable that stores the total cost
            double sum = 0;

            // iterate through the array
            for (int i = 0; i < arr.length; i++){

                // check if the current object is a WebService
                if (arr[i] instanceof WebService){

                    // cast the current object to WebService
                    // and add its cost to the total sum
                    sum += ((WebService)arr[i]).getCost();
                }
            }

            // return the total cost
            return sum;
        }
        */
    }

    // 6
    public static void q6(){
        class SpecNode {
            private int value;
            private int numOfSmallers;
            private SpecNode next;

            public SpecNode (int val) {
                this.value = val;
                this. numOfSmallers = 0;
                this.next = null;
            }

            public int getValue() { return value; }
            public void setValue(int value) { this.value = value; }
            public int getNumOfSmallers() { return numOfSmallers; }
            public void setNumOfSmallers(int numOfSmallers) { this.numOfSmallers = numOfSmallers; }
            public SpecNode getNext() { return next; }
            public void setNext(SpecNode next) { this.next = next; }
        }

        // O(n)^2
        // receives: Object[] objects = {SpecNode list, int val, int pos};
        // returns: SpecNode
        //
        // This function adds a new SpecNode with the given value
        // at the given position in the linked list.
        //
        // After the insertion, it updates the numOfSmallers field
        // of every relevant node.
        //
        // Time Complexity: O(n^2)
        // Space Complexity: O(1)
        //
        Function<Object[], SpecNode> addNum = (objects) -> {
            // extract the SpecNode list
            var list = (SpecNode) objects[0];
            // extract the value to add
            var val = (int) objects[1];
            // extract the position
            var pos = (int) objects[2];

            // --------------------------------------------------
            // Add the new node to the beginning of the list
            // --------------------------------------------------
            if (pos == 1) {
                // create the new head node
                SpecNode head = new SpecNode(val);
                // connect the new head to the original list
                head.setNext(list);
                // pointer used to iterate through the nodes
                // that come after the new head
                var p = head.getNext();
                // count how many values after the new node
                // are smaller than val
                var count = 0;

                while (p != null) {
                    // if the current value is smaller
                    // than the new node's value
                    if (p.getValue() < val) { count++; }
                    // move to the next node
                    p = p.getNext();
                }

                // update numOfSmallers of the new head
                head.setNumOfSmallers(count);

                // the existing nodes do not need to be updated,
                // because the new node was inserted before them
                return head;
            }


            // --------------------------------------------------
            // Add the new node somewhere after the head
            // --------------------------------------------------
            // pointer used to iterate through the list
            var p = list;
            // current position of p
            var count = 1;

            // move until reaching the node
            // that should come before the new node
            while (p.getNext() != null) {
                // keep track of the node
                // that currently comes after p
                var nextToCurrent = p.getNext();

                // if p is located one position
                // before the requested position
                if (count == pos - 1) {
                    // create and connect the new node
                    p.setNext(new SpecNode(val));
                    // connect the new node to
                    // the rest of the list
                    p.getNext().setNext(nextToCurrent);
                    // insertion completed
                    break;
                } else {
                    // move to the next node
                    p = p.getNext();
                }
                // update the current position
                count++;
            }


            // --------------------------------------------------
            // Add the value to the end of the list
            // --------------------------------------------------
            // if p is the last node,
            // the requested position is the end of the list
            if (p.getNext() == null) {
                // create the new last node
                p.setNext(new SpecNode(val));
                // the new last node points to null
                p.getNext().setNext(null);
            }

            // --------------------------------------------------
            // Update all numOfSmallers values
            // --------------------------------------------------
            // return p to the beginning of the list
            p = list;

            // iterate through every node
            while (p != null) {
                // keep track of the current node
                var current = p;
                // start checking from the node after current
                var next = p.getNext();
                // count how many nodes after current
                // contain a smaller value
                var counter = 0;

                // iterate through all nodes after current
                while (next != null) {
                    // if the next value is smaller
                    // than the current value
                    if (next.getValue() < current.getValue()) {
                        counter++;
                    }
                    // move to the next node
                    next = next.getNext();
                }

                // update numOfSmallers
                current.setNumOfSmallers(counter);
                // move to the next node
                p = p.getNext();
            }

            // return the head of the updated list
            return list;
        };
    }

    // Question 7
    public static void Q7() {

        // --------------------------------------------------
        // Main idea
        // --------------------------------------------------
        //
        // Employee contains check(Employee).
        //
        // Manager defines check(Manager).
        // This is OVERLOADING, not overriding,
        // because the parameter type is different.
        //
        // Intern defines check(Employee).
        // This IS OVERRIDING,
        // because the method signature is the same.


        // --------------------------------------------------
        // Employee class
        // --------------------------------------------------

        class Employee {

            private int seniority;

            public Employee(int seniority) {
                this.seniority = seniority;
            }

            public int getSeniority() {
                return this.seniority;
            }

            public boolean check(Employee e) {
                System.out.println("EmpCheck");
                return this.seniority > e.seniority;
            }
        }


        // --------------------------------------------------
        // Manager class
        // --------------------------------------------------

        class Manager extends Employee {

            public Manager(int seniority) {
                super(seniority);
            }

            // This is NOT overriding check(Employee).
            // This is overloading because the parameter is Manager.
            public boolean check(Manager m) {
                System.out.println("MgrCheck");
                return this.getSeniority() == m.getSeniority();
            }
        }


        // --------------------------------------------------
        // Intern class
        // --------------------------------------------------

        class Intern extends Employee {

            private int mentorId;

            public Intern(int seniority, int mentorId) {
                super(seniority);
                this.mentorId = mentorId;
            }

            // This overrides Employee.check(Employee).
            @Override
            public boolean check(Employee e) {

                System.out.println("IntCheck");

                // if e is not an Intern,
                // return true
                if (!(e instanceof Intern)) {
                    return true;
                }

                // cast e to Intern
                Intern other = (Intern)e;

                // return true only if both Intern objects
                // have the same mentorId
                return this.mentorId == other.mentorId;
            }
        }


        // --------------------------------------------------
        // Objects from main
        // --------------------------------------------------

        Employee e1 = new Manager(15);

        Manager m1 = new Manager(15);

        Employee e2 = new Employee(10);

        Employee i1 = new Intern(2, 800);

        Intern i2 = new Intern(2, 900);

        Intern i3 = new Intern(5, 800);


        // --------------------------------------------------
        // 1
        // --------------------------------------------------
        //
        // e1 is declared as Employee.
        // Employee.check(Employee) is selected.
        //
        // Manager does not override check(Employee).
        //
        // 15 > 15 -> false
        //
        // Output:
        // EmpCheck
        // false

        System.out.println(e1.check(m1));


        // --------------------------------------------------
        // 2
        // --------------------------------------------------
        //
        // m1 is Manager,
        // but e1 is declared as Employee.
        //
        // check(Manager) cannot receive an Employee reference.
        //
        // Therefore Employee.check(Employee) is selected.
        //
        // 15 > 15 -> false
        //
        // Output:
        // EmpCheck
        // false

        System.out.println(m1.check(e1));


        // --------------------------------------------------
        // 3
        // --------------------------------------------------
        //
        // m1 is Manager
        // and the argument is also Manager.
        //
        // Therefore Manager.check(Manager) is selected.
        //
        // 15 == 15 -> true
        //
        // Output:
        // MgrCheck
        // true

        System.out.println(m1.check(m1));


        // --------------------------------------------------
        // 4
        // --------------------------------------------------
        //
        // i1 is declared as Employee,
        // but the actual object is Intern.
        //
        // Intern overrides check(Employee),
        // therefore Intern.check(Employee) runs.
        //
        // e2 is not an Intern,
        // so the method returns true.
        //
        // Output:
        // IntCheck
        // true

        System.out.println(i1.check(e2));


        // --------------------------------------------------
        // 5
        // --------------------------------------------------
        //
        // i1 actual object:
        // Intern(seniority = 2, mentorId = 800)
        //
        // i2:
        // Intern(seniority = 2, mentorId = 900)
        //
        // 800 == 900 -> false
        //
        // Output:
        // IntCheck
        // false

        System.out.println(i1.check(i2));


        // --------------------------------------------------
        // 6
        // --------------------------------------------------
        //
        // i1 mentorId = 800
        // i3 mentorId = 800
        //
        // 800 == 800 -> true
        //
        // Output:
        // IntCheck
        // true

        System.out.println(i1.check(i3));


        // --------------------------------------------------
        // 7
        // --------------------------------------------------
        //
        // e2 is an actual Employee object.
        //
        // Employee.check(Employee) runs.
        //
        // e2 seniority = 10
        // i1 seniority = 2
        //
        // 10 > 2 -> true
        //
        // Output:
        // EmpCheck
        // true

        System.out.println(e2.check(i1));


        // --------------------------------------------------
        // Summary
        // --------------------------------------------------
        //
        // 1)
        // EmpCheck
        // false
        //
        // 2)
        // EmpCheck
        // false
        //
        // 3)
        // MgrCheck
        // true
        //
        // 4)
        // IntCheck
        // true
        //
        // 5)
        // IntCheck
        // false
        //
        // 6)
        // IntCheck
        // true
        //
        // 7)
        // EmpCheck
        // true


        // --------------------------------------------------
        // Part B
        // --------------------------------------------------
        //
        // Given:
        //
        // i1.check(XXX);
        //
        // We want:
        //
        // IntCheck
        // false
        //
        // i1 is an actual Intern object,
        // therefore Intern.check(Employee) runs.
        //
        // To return false,
        // XXX must be an Intern with a different mentorId.
        //
        // i1 mentorId = 800
        // i2 mentorId = 900
        // i3 mentorId = 800
        //
        // Therefore:
        //
        // XXX = i2

        // Example:
        // System.out.println(i1.check(i2));
    }

    // 8
    // a
    public static Node<Integer> first(Node<Integer> ch, int x){
        if(ch == null) return new Node<Integer>(x);
        ch.setNext(first(ch.getNext(), x));
        return ch;
    }

    // a - 1
    // first(ch, 3)
    // 8 -> 7 -> 9 -> 15  |->|  8 -> 7 -> 9 -> 15 -> 3

    // a - 2
    // add new node to the end of the list

    // b
    public static Node<Integer> first_1(Node<Integer> ch, int x){
        // if the list is empty,
        // create and return a new node
        if (ch == null) { return new Node<Integer>(x); }

        // keep the original head
        Node<Integer> head = ch;

        // move to the last node
        while (ch.getNext() != null) { ch = ch.getNext(); }

        // add the new node at the end
        ch.setNext(new Node<Integer>(x));

        // return the original head
        return head;
    }
    // Recursive version: O(n)
    // Iterative version: O(n)

    // c
    // --------------------------------------------------
    // second
    // --------------------------------------------------
    //
    // This function receives the root of a binary tree
    // and a positive integer k.
    //
    // It returns a linked list containing the values
    // of all nodes whose distance from the root
    // is exactly k edges.
    //
    // The nodes are added from left to right.
    //
    // Example:
    // second(bt, 3)
    //
    // For the tree in the question:
    //
    //                    7
    //                  /   \
    //                 4     5
    //                / \     \
    //               1   7     2
    //                    \   / \
    //                     6 2   3
    //                       /
    //                      6
    //
    // The nodes at distance 3 from the root are:
    //
    // 6, 2, 3
    //
    // Therefore the returned list is:
    //
    // 6 -> 2 -> 3
    public static Node<Integer> second(BinNode<Integer> bt, int k) {
        // create the list using the recursive helper function
        Node<Integer> list = third(bt, null, k);
        // return the head of the created list
        return list;
    }


    // --------------------------------------------------
    // third
    // --------------------------------------------------
    //
    // Recursive helper function for second.
    //
    // The function goes down the binary tree.
    // Every recursive call decreases k by 1.
    //
    // When k reaches 0, the current node is exactly
    // at the requested distance from the root.
    //
    // Its value is then added to the END of the list
    // using first().
    public static Node<Integer> third(BinNode<Integer> bt, Node<Integer> list, int k) {
        // continue only if the current tree node exists
        if (bt != null) {
            // if we have not yet reached
            // the requested distance
            if (k > 0) {
                // search the left subtree, one level deeper
                list = third(bt.getLeft(), list, k - 1);
                // search the right subtree, one level deeper
                list = third(bt.getRight(), list, k - 1);
            }

            // k == 0 means that the current node
            // is exactly at the requested distance
            // add the current node's value
            // to the end of the linked list
            else { list = first(list, bt.getValue()); }
        }

        return list; // return the updated list
    }

    // c - 1 - 2
    // --------------------------------------------------
    // Answer for second(bt, 3)
    // --------------------------------------------------
    // third starts from:
    // root -> k = 3
    //
    // After going down one level:
    // level 1 -> k = 2
    //
    // After going down another level:
    // level 2 -> k = 1
    //
    // After going down another level:
    // level 3 -> k = 0
    // When k == 0, the values are added to the list.
    //
    // The nodes at distance 3 are:
    // 6, 2, 3
    //
    // Therefore:
    // second(bt, 3)
    // returns:
    // 6 -> 2 -> 3 -> null

    // --------------------------------------------------
    // What does second do in general?
    // --------------------------------------------------
    // second(bt, k) returns a linked list containing
    // the values of all nodes whose distance from
    // the root of the binary tree is exactly k edges.
    // The values are inserted into the list
    // from left to right.


    // c - 3
    //
    // This function receives the root of a binary tree
    // and an integer k.
    //
    // It uses BFS to traverse the tree level by level,
    // creates a linked list containing the values of the nodes at each level,
    // and returns the list that represents level k.
    //
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    BiFunction<BinNode<Integer>, Integer, Node<Integer>> third_b = (root, k) -> {
        // if the tree is empty, return null
        if (root == null) return null;

        // create a deque for BFS traversal
        var searchDeque = new ArrayDeque<BinNode<Integer>>();
        // offer the root node to the deque
        searchDeque.offer(root);

        // create a map to store the linked list that represents each level
        var map = new LinkedHashMap<Integer, Node<Integer>>();
        // current level in the tree
        var level = 0;

        // iterate through the tree level by level
        while (!searchDeque.isEmpty()) {
            // number of nodes in the current level
            var size = searchDeque.size();
            // head of the linked list that represents the current level
            Node<Integer> neweyHead = null;
            // tail of the linked list that represents the current level
            Node<Integer> neweyTail = null;

            // iterate through all nodes in the current level
            for (int i = 0; i < size; i++) {
                // get the next node from the current level
                var currentBin = searchDeque.poll();

                // if the current level list is empty, create its first node
                if (neweyHead == null) { neweyHead = neweyTail = new Node<>(currentBin.getValue()); }
                // otherwise, add the current value to the end of the level list
                else {
                    neweyTail.setNext(new Node<>(currentBin.getValue()));
                    neweyTail = neweyTail.getNext();
                }

                // add the left child to the next BFS level
                if (currentBin.getLeft() != null) { searchDeque.offer(currentBin.getLeft()); }
                // add the right child to the next BFS level
                if (currentBin.getRight() != null) { searchDeque.offer(currentBin.getRight()); }
            }

            // store the linked list that represents the current level
            map.put(level++, neweyHead);
        }

        // get the linked list that represents level k
        // if level k does not exist, return null
        var chain = map.getOrDefault(k, null);

        // return the values of all nodes at level k
        return chain;
    };
}
