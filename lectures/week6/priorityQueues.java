package week6;

import java.util.PriorityQueue;
import java.util.Scanner;

// a.k.a priority queue
public class priorityQueues {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        PriorityQueue<Patient> pq = new PriorityQueue<>();

        while (true) {
            System.out.println("\nA new patient just walked in! Enter their name, ");
            System.out.println("or type CLOCK IN to start helping patients.");
            System.out.print(">  ");
            String name = scan.nextLine();
            if (name.toUpperCase().equals("CLOCK IN")) {
                break;
            }
            System.out.println("\nEnter this patient's priority, from 1-10, 10 being most urgent.");
            System.out.print(">  ");
            int priority = scan.nextInt();
            String _buffer = scan.nextLine();

            pq.add(new Patient(name, priority));
        }

        System.out.println("\nHere's your patient list for today. Please see the top patient first.");
        while (!pq.isEmpty()) {
            System.out.println(pq.poll().name);
        }

    }
}

class Patient implements Comparable<Patient> {
    // data
    public String name;
    public int priority;

    public Patient(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    @Override
    public int compareTo(Patient p) {
        if (p.priority != this.priority) {
            return p.priority - this.priority;
        } else if (p.name.charAt(0) != this.name.charAt(0)) {
            return (p.name.charAt(0) - this.name.charAt(0)) * -1; // A comes first
        } else {
            // here's where i'd sort by 2nd letter alphabetically etc
            // unfortunately i am on a time crunch ¯\_(ツ)_/¯
            return 0;
        }
    }
}