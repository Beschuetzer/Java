package major.adam;

public class Main {

    public static void main(String[] args) {
        WeekDays monday = WeekDays.MONDAY;
        System.out.println(WeekDays.MONDAY.getDescription());
        System.out.println(monday.isWeekend());




        int num1 = 10;
        int num2 = 2;
        Operator operator = Operator.ADD;
        int result = operator.execute(num1, num2);
        int result2 = Operator.ADD.execute(num1, num2);
        System.out.println("result: " + result);
        System.out.println("result2: " + result2);


            }
}
