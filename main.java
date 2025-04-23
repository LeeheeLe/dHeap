// maman 12 - Implementation of a d-ary max-heap with interactive command-line interface

import java.util.Scanner;

class DHeap {
    private int d; // Number of children per node in the d-ary heap
    private int[] A; // Array representation of the heap
    private int size; // Number of elements in the heap
    public static final int ARRAY_LENGTH = 1000; // Maximum number of elements in the heap

    // Constructor: initializes the heap with a given d value
    // If d < 2, defaults to 3
    public DHeap(int d) {
        if (d < 2) {
            System.out.println("D value is less than 2. Setting d = 3 by default.");
            this.d = 3;
        } else {
            this.d = d;
        }
        this.A = new int[ARRAY_LENGTH];
        this.size = 0;
    }

    // Getter for size
    public int get_size() {
        return size;
    }

    // Setter for size
    public void set_size(int size) {
        this.size = size;
    }

    // Getter for array A
    public int[] get_a() {
        return A;
    }

    // Setter for array A
    public void set_a(int[] A) {
        this.A = A;
    }

    // Builds the heap from an input array of values using a bottom-up heapify approach (linear time)
    public void build_heap(int d, int[] heapValues) {
        this.d = d;
        if (d < 2){
            this.d = 3;
        }
        this.size = heapValues.length;
        System.arraycopy(heapValues, 0, A, 0, size); // Copy input values into heap array
        for (int i = (size - 1) / d; i >= 0; i--) {
            heapify_down(i); // Restore heap property from the bottom up
        }
        print_heap();
    }

    // Changes the value of d and rebuilds the heap
    public void change_d(int d) {
        if (d < 2) {
            System.out.println("D must be at least 2. Returning to main menu.");
            return;
        }
        int[] values = new int[size];
        System.arraycopy(A, 0, values, 0, size); // Preserve current heap values
        build_heap(d, values); // Rebuild with new d
    }

    // Removes and prints the maximum value from the heap, then re-heapifies
    public void remove_max() {
        if (size == 0) {
            System.out.println("Heap is empty. Returning to main menu.");
            return;
        }
        System.out.println("Removed max: " + A[0]);
        A[0] = A[size - 1]; // Move last element to root
        size--;
        heapify_down(0); // Restore heap property
        print_heap();
    }

    // Inserts a new value into the heap and maintains heap property
    public void insert(int x) {
        if (size >= 1000) {
            System.out.println("Heap is full. Returning to main menu.");
            return;
        }
        if (x < -9999 || x > 9999) {
            System.out.println("Value must be between -9999 and 9999. Returning to main menu.");
            return;
        }
        A[size] = x;
        heapify_up(size); // Restore heap property
        size++;
        print_heap();
    }

    // Prints the heap level by level
    public void print_heap() {
        if (size == 0) {
            System.out.println("Heap is empty. Returning to main menu.");
            return;
        }

        System.out.println(this.d + " - ary Heap:");

        int index = 0;
        int level = 0;
        while (index < size) {
            int nodesInLevel = (int) Math.pow(d, level); // Number of nodes in the current level
            int count = 0;
            while (count < nodesInLevel && index < size) {
                System.out.print(A[index] + " ");
                count++;
                index++;
            }
            System.out.println();
            level++;
        }
    }

    // Prints exit message
    public void exit_program() {
        System.out.println("Exiting program.");
    }

    // Maintains heap property by pushing a node down the tree
    private void heapify_down(int i) {
        while (true) {
            int maxIndex = i; // Track largest value index
            for (int j = 1; j <= d; j++) {
                int child = d * i + j; // Calculate index of j-th child
                if (child < size && A[child] > A[maxIndex]) {
                    maxIndex = child;
                }
            }
            if (maxIndex == i) break; // Heap property is satisfied
            int temp = A[i];
            A[i] = A[maxIndex];
            A[maxIndex] = temp;
            i = maxIndex;
        }
    }

    // Maintains heap property by pushing a node up the tree
    private void heapify_up(int i) {
        while (i > 0) {
            int parent = (i - 1) / d; // Get parent index
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
        // Main method to run the d-ary heap interface
        System.out.println("Hello! Welcome to the d-heap interface");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter d for your d-ary heap: ");
        int d = scanner.nextInt();

        DHeap heap = new DHeap(d); // Create heap
        int command = 0;

        // Command interface loop
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
                    heap.set_size(numOfVal);
                    System.out.println("Enter values:");
                    int[] values = new int[numOfVal];
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
                        heap.set_a(values);
                        heap.build_heap(d, values);
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

        scanner.close(); // Close scanner
    }
}
