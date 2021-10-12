package major.adam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {
        Employee[] employees = {
                new Employee("Tom", 21),
                new Employee("Adam", 25),
                new Employee("Jon", 40),
                new Employee("Jonny", 29),
                new Employee("Jon Doe", 31)
        };

        List<Employee> employeeList = new ArrayList<>();

        for (Employee player : employees) {
            employeeList.add(player);
        }

        employeeList.forEach((employee -> {
            System.out.println(employee.getName());
            System.out.println(employee.getAge());
        }));

        employeeList.stream().filter((employee -> {
            return employee.getAge() == 21;
        })).forEach(employee -> System.out.println(employee.getName()));

        //Consumer and Predicate interfaces
        //args are just lambda expressions that match the predicate interface
        printEmployeesByAge(employeeList, "Employees Younger than 30", employee -> employee.getAge() < 30);
        printEmployeesByAge(employeeList, "Employees Older than or equal to 30", employee -> employee.getAge() >= 30);

        IntPredicate greaterThanFifteen = i -> i > 15;
        IntPredicate lessThanOneHundred = i -> i < 100;
        System.out.println(greaterThanFifteen.test(20));

        //predicate chaining (chain using .and() then one .test() at expression's end)
        System.out.println(greaterThanFifteen.and(lessThanOneHundred).test(100));
        System.out.println(greaterThanFifteen.or(lessThanOneHundred).test(100));
        System.out.println(greaterThanFifteen.negate().test(100));


        //Supplier Interface (doesn't accept args but returns a value)
        //more specific types of suppliers exist too (like Predicates/Consumers) e.g. IntSuppplier
        System.out.println("-".repeat(50));
        Random random = new Random();
        Supplier<Integer> randomSupplier = () -> random.nextInt(100);
        for (int i = 1; i <= 100; i++) {
            String suffix = i == 1 ? "st" : i == 2 ? "nd" : "th";
            System.out.println(i + suffix + " number: " + randomSupplier.get());
        }
    }

    public static void printEmployeesByAge(List<Employee> employees, String ageText, Predicate<Employee> ageCondition) {
        System.out.println("\n" + ageText);
        System.out.println("===========");
        for (Employee employee : employees) {
            if (ageCondition.test(employee)) System.out.println(employee.getName());
        }
    }
}
