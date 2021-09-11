package major.adam;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        int value = 4;

        //NOTE: value must be of type byte, short, char, or int
        switch(value) {
            case 1: 
                System.out.println("Value is 1");
                break;
            case 2:
                System.out.println("Value is 2");
                break;
            case 3: case 4: case 5:
                System.out.println("Was a 3, 4, or 5");
                break;

            default:
                System.out.println("Value is default");
                break;
                
        }
    
        System.out.println(printNumberInWord(91));

       System.out.println(getDaysInMonth(4, 1600));

       System.out.println(sumDigits(11022));
       System.out.println(isPalindrome(1101));
       System.out.println(sumFirstAndLastDigit(-3105));
       System.out.println(getEvenDigitSum(2000));
       System.out.println(getGreatestCommonDivisor(21, 16));
       printFactors(90);
       System.out.println(isPerfectNumber(100));
       System.out.println(getDigitCount(0));
       System.out.println(reverse(107));
       numberToWords(11);
       
    }

    public static String printNumberInWord(int number) {
        int positive = Math.abs(number);
        switch (positive) {
            case 0:
                return "ZERO";
            case 1:
                return "ONE";
            case 2:
                return "TWO";
            case 3:
                return "THREE";
            case 4:
                return "FOUR";
            case 5:
                return "FIVE";
            case 6:
                return "SIX";
            case 7:
                return "SEVEN";
            case 8:
                return "EIGHT";
            case 9:
                return "NINE";
            default:
                return "OTHER";
        }
    }

    public static boolean isLeapYear(int year) {
        if (year < 0 || year > 9999) return false;
        return (year % 400 == 0)  || (year % 4 == 0 && year % 100 != 0);
    }

    public static int getDaysInMonth(int month, int year) {
        if (month < 1 || month > 12 || year < 1 || year > 9999) return -1;

        boolean isLeapYear = false;
        if (month == 2) isLeapYear = isLeapYear(year);

        switch(month) {
            case 2:
                return !isLeapYear ? 28 : 29;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    public static boolean isOdd(int number) {
        if (number <= 0) return false;
        return number % 2 == 1;
    }

    public static boolean isEven(int number) {
        if (number <= 0) return false;
        return number % 2 == 0;
    }

    public static int sumOdd(int start, int end) {
        if (start > end || start <= 0 || end <= 0 ) return -1;

        int sumTotal = 0;
        for(int i = start; i <= end; i++) {
            if (isOdd(i)) sumTotal += i;
        }

        return sumTotal;
    }

    public static int sumDigits(int number){
        if (number < 0) return -1;
        else if (number > 0 && number < 10) return number;
        
        int sum = 0;
        while(number > 0) {
            int lastDigit = number % 10;
            sum += lastDigit;
            number /= 10;
        }

        return sum;
    }

    public static boolean isPalindrome(int number) {
       String string = Integer.toString(number).replace('-', ' ').trim();
       String reversed = new StringBuilder(string).reverse().toString().replace('-', ' ').trim();
       return Integer.parseInt(string) == Integer.parseInt(reversed);
    }

    public static int sumFirstAndLastDigit(int number) {
        if (number < 0) return -1;
        int lastDigit = number % 10;
        int newNumber = number / 10;
        int sumResult = lastDigit;
        if (newNumber == 0) sumResult += lastDigit;

        while (newNumber != 0) {
            if (newNumber < 10) {
                sumResult += newNumber;
                break;
            }
            newNumber /= 10;
        }

        return sumResult;
    }

    public static int getEvenDigitSum(int number) {
        if (number < 0) return -1;

        int sum = 0;
        int lastDigit = number % 10;
        int newNumber = number / 10;
        if (lastDigit % 2 == 0) sum += lastDigit;

        while (newNumber != 0) {
            lastDigit = newNumber % 10;
            newNumber /= 10;
            if (lastDigit % 2 == 0) sum += lastDigit;
        }

        System.out.println("newNumber: " + newNumber);
        

        return sum;
    }

    public static boolean hasSharedDigit(int number1, int number2) {
        if (number1 < 10 || number1 > 99 || number2 < 10 || number2 > 99) return false;
        
        int number1FirstDigit = number1 / 10;
        int number1LastDigit = number1 % 10;
        int number2FirstDigit = number2 / 10;
        int number2LastDigit = number2 % 10;

        boolean firstDigitsTheSame = number1FirstDigit == number2FirstDigit;
        boolean lastDigitsTheSame = number1LastDigit == number2LastDigit;
        boolean mix1TheSame = number1FirstDigit == number2LastDigit;
        boolean mix2TheSame = number2FirstDigit == number1LastDigit;

        return firstDigitsTheSame || lastDigitsTheSame || mix1TheSame || mix2TheSame;
    }

    public static int getGreatestCommonDivisor(int first, int second) {
        if (first < 10 || second < 10) return -1;

        int larger = first;
        int smaller = second;
        if (second > larger) {
            larger = second;
            smaller = first;
        }

        for(int i = smaller; i > 0; i--) {
            boolean largerIsDivisible = larger % i == 0;
            boolean smallerIsDivisible = smaller % i == 0;

            if (largerIsDivisible && smallerIsDivisible) return i;
        }

        return 1;
    }

    public static void printFactors(int number) {
        if (number < 1 ) {
            System.out.println("Invalid Value");
            return;
        }
        
        double stop = Math.floor((double) number / 2.0d);
        for (double i = 0; i <= stop; i++) {
            if (number % i == 0) System.out.println((int)i);
        }

        System.out.println(number);
    }

    public static ArrayList<Integer> getFactors(int number) {
        ArrayList<Integer> factors = new ArrayList<Integer>();
        if (number < 1 ) {
            factors.add(-1);
            return factors;
        }
        
        double stop = Math.floor((double) number / 2.0d);
        for (double i = 0; i <= stop; i++) {
            if (number % i == 0) factors.add((int)i);
        }

        factors.add(number);

       return factors;
    }

    public static boolean isPerfectNumber(int number) {
        if (number < 1) return false;

        ArrayList<Integer> factors = getFactors(number);
        
        factors.remove(factors.size() - 1);
        int sum = factors.stream().reduce((a, b) -> a+b).get();
        
        return sum == number;
    }

    public static int getDigitCount(int number) {
        if (number < 0) return -1;
        int count = 0;
        int newNumber = number / 10;

        while (newNumber != 0) {
            count++;
            newNumber /= 10;
        }

        count++;
        return count;
    }

    public static int reverse(int number) {
        //get the factor that is power of 10
        int reversed = 0;
        int newNumber = number / 10;
        int lastDigit = number % 10;
        int numberOfDigits = getDigitCount(Math.abs(number));


        newNumber = number / 10;
        lastDigit = number % 10;

        int i = 0;
        while (lastDigit != 0 || newNumber != 0){
            int nthPower = numberOfDigits - i - 1;
            reversed += Math.pow(10, (nthPower)) * lastDigit;
            
            lastDigit = newNumber % 10;
            newNumber /= 10;
            i++;
        }

        return reversed;
    }

    public static void numberToWords(int number) {
        if (number < 0) {
            System.out.println("Invalid Value");
            return;            
        }

        int reversed = reverse(number);
        int digitCount = getDigitCount(number);
        int lastDigit = reversed % 10;
        int newNumber = reversed / 10;
        int count = 0;

        do {
            switch (lastDigit) {
                case 0:
                    System.out.println("Zero");
                    break;
                case 1:
                    System.out.println("One");
                    break;
                case 2:
                    System.out.println("Two");
                    break;
                case 3:
                    System.out.println("Three");
                    break;
                case 4:
                    System.out.println("Four");
                    break;
                case 5:
                    System.out.println("Five");
                    break;
                case 6:
                    System.out.println("Six");
                    break;
                case 7:
                    System.out.println("Seven");
                    break;
                case 8:
                    System.out.println("Eight");
                    break;
                case 9:
                    System.out.println("Nine");
                    break;
                default:
                    System.out.println("Invalid Value");
                    break;
            }
            lastDigit = newNumber % 10;
            newNumber /= 10;
            count++;

        } while (count != digitCount);

    }

    public static String getWordForNumber(int number) {
        switch (number) {
            case 0:
                return "Zero";
            case 1:
                return "One";
            case 2:
                return "Two";
            case 3:
                return "Three";
            case 4:
                return "Four";
            case 5:
                return "Five";
            case 6:
                return "Six";
            case 7:
                return "Seven";
            case 8:
                return "Eight";
            case 9:
                return "Nine";
            default:
                return "Invalid Value";
        }
    }
}
