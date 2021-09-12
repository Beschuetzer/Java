package major.adam;
import java.util.Scanner;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) {
        System.out.println(getBucketCount(2.75, 3.25, 2.5, 1));
        
        // ReadingUserInputChallenge();
        // MinAndMaxInputChallenge();
        // inputThenPrintSumAndAverage();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your birth year: ");
        int birthYear = scanner.nextInt();
        
        //NOTE: .nextInt() does not handle the enter key press; as a general rule of thumb, need to call nextLine() whenever the enter or carriage return character is pressed otherwise it will not function as expected
        scanner.nextLine();

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();


        int age = (Calendar.getInstance().get(Calendar.YEAR) - birthYear);
        String response = String.format("You are either %s or will turn %s this year and your name is %s.", age, age, name);
        System.out.println(response);
        
        //NOTE: have to call .close() when done with scanner to free up memory taken up by scanner obj.
        scanner.close();
    }

    public static void ReadingUserInputChallenge() {
        int count = 1;
        int sum = 0;
        Scanner scanner = new Scanner(System.in);

        while (count <= 10) {
            System.out.print(String.format("Enter number #%s: ", count));
            
            boolean hasNextInt = scanner.hasNextInt();

            if (hasNextInt) {
                int nextNumber = scanner.nextInt();
                count++;
                sum += nextNumber;
                System.out.println("Sum is " + sum);
            } else {
                System.out.println("Invalid Number");
            }

            scanner.nextLine();
        }

        scanner.close();
    }

    public static void MinAndMaxInputChallenge() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Enter a number: ");
            
            boolean hasNextInt = scanner.hasNextInt();

            if (hasNextInt) {
                int numberEntered = scanner.nextInt();
                if (numberEntered < min) min = numberEntered;
                if (numberEntered > max) max = numberEntered;
            } else {
                String format = "Min: %s and Max: %s";
                System.out.println(String.format(format, min, max));
                break;
            }

            // scanner.nextLine();
        }
        
        scanner.close();
    }

    public static void inputThenPrintSumAndAverage() {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        int count = 0;
        long avg = 0;
        
        while (true) {
            System.out.print("Enter an integer: ");
            boolean hasNextInt = scanner.hasNextInt();

            if (hasNextInt) {
                int numberEntered = scanner.nextInt();
                
                count++;
                sum += numberEntered;
                avg = sum / count;

                System.out.println("avg: " + avg);
                System.out.println("sum: " + sum);
            } else {
                String format = "SUM = %s AVG = %S";
                System.out.println(String.format(format, sum, avg));
                break;
            }

            scanner.nextLine();
        }

        scanner.close();
    }

    public static int getBucketCount(double width, double height, double areaPerBucket, int extraBuckets) {
        if (width <= 0 || height <= 0 || areaPerBucket <= 0 || extraBuckets < 0) return -1;

        double areaToCover = width * height;
        double areaCanCoverFromExtras = extraBuckets * areaPerBucket;

        double areaNeedToGetBucketsFor = (areaToCover - areaCanCoverFromExtras);

        if (areaNeedToGetBucketsFor <= 0) return 0;
        return (int) Math.ceil(areaNeedToGetBucketsFor / areaPerBucket);
    }
}
