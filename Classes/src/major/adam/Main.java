package major.adam;

public class Main {

    public static void main(String[] args) {
        SimpleCalculator calc = new SimpleCalculator(1,2);

        System.out.println(calc.getAdditionResult());
        System.out.println(calc.getSubtractionResult());
        System.out.println(calc.getMultiplicationResult());
        System.out.println(calc.getDivisionResult());
    }
}
