import java.util.Scanner;

class DHeap {
    private int d;
    private int[] A;
    private int size;

    public DHeap(int d) {
        if (d < 2) {
            System.out.println("D value is less than 2. Setting d = 3 by default.");
            this.d = 3;
        } else {
            this.d = d;
        }
        this.A = new int[1000];
        this.size = 0;
    }

    // Getter and Setter for size
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    // Getter and Setter for A
    public int[] getA() {
        return A;
    }

    public void setA(int[] A) {
        this.A = A;
    }

    public void build_heap(int d, int[] values) {
        if (d < 2) {
            System.out.println("D value entered is less than 2, setting d = 3 by default");
            this.d = 3;
        } else {
            this.d = d;
        }

        this.size = values.length;
        for (int i = 0; i < size; i++) {
            A[i] = values[i];
        }

        for (int i = (size - 1) / d; i >= 0; i--) {
            heapifyDown(i);
        }
        print_heap();
    }

    public void change_d(int d) {
        if (d < 2) {
            System.out.println("D must be at least 2. Keeping previous value.");
            return;
        }
        int[] values = new int[size];
        System.arraycopy(A, 0, values, 0, size);
        build_heap(d, values);
    }

    public void remove_max() {
        if (size == 0) {
            System.out.println("Heap is empty.");
            return;
        }
        System.out.println("Removed max: " + A[0]);
        A[0] = A[size - 1];
        size--;
        heapifyDown(0);
        print_heap();
    }

    public void insert(int x) {
        if (size >= 1000) {
            System.out.println("Heap is full.");
            return;
        }
        if (x < -9999 || x > 9999) {
            System.out.println("Value must be between -9999 and 9999. Returning to main menu.");
            return;
        }
        A[size] = x;
        heapifyUp(size);
        size++;
        print_heap();
    }

    public void print_heap() {
        if (size == 0) {
            System.out.println("Heap is empty.");
            return;
        }

        System.out.print("d - ary Heap: ");
        int level = 0;
        int currentLevelCount = 1;
        int nextLevelCount = 0;
        for (int i = 0; i < size; i++) {
            System.out.print(A[i] + " ");
            currentLevelCount--;
            nextLevelCount++;
            if (currentLevelCount == 0) {
                System.out.println();
                currentLevelCount = nextLevelCount;
                nextLevelCount = 0;
                level++;
            }
        }
        System.out.println();
    }

    public void exit_program() {
        System.out.println("Exiting program..");
    }

    private void heapifyDown(int i) {
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

    private void heapifyUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / d;
            if (A[i] > A[parent]) {
                int temp = A[i];
                A[i] = A[parent];
                A[parent] = temp;
                i = parent;
            } else {
                break;
            }
        }
    }
}

class Main {
    public static void main(String[] args) {
        System.out.println("Hello! Welcome to d-heap interface");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter d for your d-ary heap: ");
        int d = scanner.nextInt();

        DHeap heap = new DHeap(d); // user-supplied d!
        int command = 0;

        while (command != 6) {
            System.out.println("\nPlease enter number of command:\n1 - build_heap\n2 - change_d\n3 - remove_max\n4 - insert\n5 - print_heap\n6 - exit_program");
            command = scanner.nextInt();

            switch (command) {
                case 1:
                    System.out.print("Enter number of values: ");
                    int n = scanner.nextInt();
                    if (n > 1000) {
                        System.out.println("Cannot have more than 1000 values. Returning to main menu.");
                        break;
                    }
                    heap.setSize(n);  // Use setter for size
                    System.out.println("Enter values:");
                    int[] values = new int[n];  // Create an array to hold values
                    boolean validInput = true;
                    for (int i = 0; i < n; i++) {
                        int val = scanner.nextInt();
                        if (val < -9999 || val > 9999) {
                            System.out.println("Invalid input! Value must be between -9999 and 9999. Returning to main menu.");
                            validInput = false;
                            break;
                        }
                        values[i] = val;
                    }
                    if (validInput) {
                        heap.setA(values);  // Use setter for A
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
                    System.out.print("Enter value to insert: ");
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