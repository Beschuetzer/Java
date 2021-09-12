package major.adam;
import java.util.Scanner;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) {
        // ReadingUserInputChallenge();
        MinAndMaxInputChallenge();

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
}
