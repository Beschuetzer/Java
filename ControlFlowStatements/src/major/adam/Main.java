package major.adam;

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

}
