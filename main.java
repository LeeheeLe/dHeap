import java.util.Scanner;

class DHeap {
    private int d;
    private int[] A;
    private int size; //counter for filled valid cells in the array
    public static final int ARRAY_LENGTH = 1000;

    public DHeap(int d) { //heap constructor 
        if (d < 2) {
            System.out.println("D value is less than 2. Setting d = 3 by default.");
            this.d = 3;
        } else {
            this.d = d;
        }
        this.A = new int[ARRAY_LENGTH];
        this.size = 0;
    }

    // Getter and Setter for size
    public int get_size() {
        return size;
    }

    public void set_size(int size) {
        this.size = size;
    }

    // Getter and Setter for A
    public int[] get_a() {
        return A;
    }

    public void set_a(int[] A) {
        this.A = A;
    }

    public void build_heap(int d, int[] heapValues) {
        this.d = d;
        if (d < 2){
            this.d = 3;
        }
        this.size = heapValues.length;
        System.arraycopy(heapValues, 0, A, 0, size); //copy heapValues array to A array
        for (int i = (size - 1) / d; i >= 0; i--) {
            heapify_down(i);
        }
        print_heap();
    }

    public void change_d(int d) {
        if (d < 2) {
            System.out.println("D must be at least 2. Returning to main menu.");
            return; //return to main menu
        }
        int[] values = new int[size];
        System.arraycopy(A, 0, values, 0, size);
        build_heap(d, values);
    }

    public void remove_max() {
        if (size == 0) {
            System.out.println("Heap is empty. Returning to main menu.");
            return; //return to main menu
        }
        System.out.println("Removed max: " + A[0]);
        A[0] = A[size - 1];
        size--; //update size
        heapify_down(0);
        print_heap();
    }

    public void insert(int x) {
        if (size >= 1000) {
            System.out.println("Heap is full. Returning to main menu.");
            return; //return to main menu
        }
        if (x < -9999 || x > 9999) {
            System.out.println("Value must be between -9999 and 9999. Returning to main menu.");
            return; //return to main menu
        }
        A[size] = x;
        heapify_up(size);
        size++;
        print_heap();
    }

        public void print_heap() {
        if (size == 0) {
            System.out.println("Heap is empty. Returning to main menu.");
            return;
        }
    
        System.out.println(this.d + " - ary Heap:");
    
        int index = 0;
        int level = 0;
        while (index < size) {
            int nodesInLevel = (int) Math.pow(d, level); // d^level nodes at this level
            int count = 0;
            while (count < nodesInLevel && index < size) {
                System.out.print(A[index] + " ");
                count++;
                index++;
            }
            System.out.println(); // Move to next line after each level
            level++;
        }
    }

    public void exit_program() {
        System.out.println("Exiting program.");
    }

    private void heapify_down(int i) {
        while (true) {
            int maxIndex = i;
            for (int j = 1; j <= d; j++) {
                int child = d * i + j;
                if (child < size && A[child] > A[maxIndex]) {
                    maxIndex = child;
                }
            }
            if (maxIndex == i) break;
            int temp = A[i];
            A[i] = A[maxIndex];
            A[maxIndex] = temp;
            i = maxIndex;
        }
    }

    private void heapify_up(int i) {
        while (i > 0) {
            int parent = (i - 1) / d;
            if (A[i] > A[parent]) {
                int temp = A[i];
                A[i] = A[parent];
                A[parent] = temp;
                i = parent;
            } else break;
        }
    }
}

class Main {
    public static void main(String[] args) {
        System.out.println("Hello! Welcome to the d-heap interface");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter d for your d-ary heap: ");
        int d = scanner.nextInt();

        DHeap heap = new DHeap(d);
        int command = 0;

        while (command != 6) {
            System.out.println("\nPlease enter number of command:\n1 - build_heap\n2 - change_d\n3 - remove_max\n4 - insert\n5 - print_heap\n6 - exit_program");
            command = scanner.nextInt();

            switch(command) {
                case 1:
                    System.out.print("Enter the amount of values in your heap: ");
                    int numOfVal = scanner.nextInt();
                    if (numOfVal > 1000) {
                        System.out.println("Cannot have more than 1000 values. Returning to main menu.");
                        break;
                    }
                    heap.set_size(numOfVal);  // Use setter for size
                    System.out.println("Enter values:");
                    int[] values = new int[numOfVal];  // Create an array to hold values
                    boolean validInput = true;
                    for (int i = 0; i < numOfVal; i++) {
                        int val = scanner.nextInt();
                        if (val < -9999 || val > 9999) {
                            System.out.println("Invalid input! Value must be between -9999 and 9999. Returning to main menu.");
                            validInput = false;
                            break;
                        }
                        values[i] = val;
                    }
                    if (validInput) {
                        heap.set_a(values);  // Use setter for A
                        heap.build_heap(d, values);  // Pass 'values' to build_heap
                    }
                    break;
                case 2:
                    System.out.print("Enter new d value: ");
                    int newD = scanner.nextInt();
                    heap.change_d(newD);
                    break;
                case 3:
                    heap.remove_max();
                    break;
                case 4:
                    System.out.print("Enter value to insert to heap: ");
                    int val = scanner.nextInt();
                    heap.insert(val);
                    break;
                case 5:
                    heap.print_heap();
                    break;
                case 6:
                    heap.exit_program();
                    break;
                default:
                    System.out.println("Error, invalid input. Please choose a valid command number.");
            }
        }

        scanner.close();
    }
}
