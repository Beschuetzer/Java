package major.adam;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(Arrays.toString(reverse(new int[] {1,2,3,4})));
        System.out.println(Arrays.toString(reverse(new Integer[] {1,2,3,4})));

        while (true) {
            System.out.print("Enter the number of Integers to enter: ");
            boolean hasNextInt = scanner.hasNextInt();

            if(hasNextInt) {
                int count = scanner.nextInt();
                Integer[] integers = readIntegers(count);
                System.out.println(findMin(integers));
                break;
            } else {
                System.out.println("Please enter a valid number.\n");
            }
            scanner.nextLine();
        }
        scanner.close();


    }

    public static Integer[] readIntegers(int count) {
        Integer[] integers = new Integer[count];

        int numbersRead = 0;
        while (numbersRead < count) {
            System.out.print("Enter a number: ");

            boolean hasNextInt = scanner.hasNextInt();
            if (hasNextInt) {
                integers[numbersRead] = scanner.nextInt();
                numbersRead++;
            } else {
                System.out.println("Invalid Number.");
            }

            scanner.nextLine();
        }
        System.out.println(Arrays.toString(integers));
        return integers;
    }

    public static int findMin(Integer[] integers) {
        return Collections.min(Arrays.asList(integers));
    }

    public static Integer[] reverse(Integer[] arr) {
        Collections.reverse(Arrays.asList(arr));
        return  arr;
    }

    public static int[] reverse(int[] arr) {
        int stopNumber = arr.length / 2;
        int startNumber = arr.length - 1;
        int iterationCount = 0;

        for(int i = startNumber; i >= stopNumber; i--) {
            int temp = arr[i];
            arr[i] = arr[iterationCount];
            arr[iterationCount] = temp;
            iterationCount++;
        }

        return arr;
    }
}
