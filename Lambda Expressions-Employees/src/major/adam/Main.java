package major.adam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.*;

public class Main {

    public static void main(String[] args) {
        Employee[] employees = {
                new Employee("Tom Thompson", 21),
                new Employee("Adam Minor", 25),
                new Employee("Jon Doe", 40),
                new Employee("Jonny Be Good", 29),
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
        for (int i = 1; i <= 10; i++) {
            String suffix = i == 1 ? "st" : i == 2 ? "nd" : "th";
            System.out.println(i + suffix + " number: " + randomSupplier.get());
        }

        //getting last name using .forEach
        employeeList.forEach(employee -> {
            String name = employee.getName();
            String lastName = name.substring(name.indexOf(' ') + 1);
            System.out.println("lastName = " + lastName);
        });

        //using a functional interface with lambda expression
        System.out.println("-".repeat(50));
        Function<Employee, String> getLastName = (Employee employee) -> {
          return employee.getName().substring(employee.getName().indexOf(' ') + 1);
        };
        String lastName = getLastName.apply(employeeList.get(1));
        System.out.println("2nd employee's lastName = " + lastName);

        Function<Employee, String> getFirstName = (employee) -> {
            return employee.getName().substring(0, employee.getName().indexOf(' '));
        };

        System.out.println("-".repeat(50));
        System.out.println("3nd Employees's first name " + getName(getFirstName, employeeList.get(2)));
        System.out.println("3nd Employees's last name " + getName(getLastName, employeeList.get(2)));
        System.out.println("-".repeat(50));


        //function chaining using .andThen()
        //the first function runs first then that output gets piped to the input of the next function in the chain
        Function<Employee, String> firstName = (employee -> employee.getName().substring(0, employee.getName().indexOf(' ')));
        Function<String, String> toUpper = (s -> s.toUpperCase());
        Function<Employee, String> firstNameUpperAndThen = firstName.andThen(toUpper);
        Function<Employee, String> firstNameUpperCompose = toUpper.compose(firstName);
        String upperFirstNameChained = firstNameUpperAndThen.apply(employeeList.get(employeeList.size() - 1));
        String upperFirstNameComposed = firstNameUpperCompose.apply(employeeList.get(employeeList.size() - 1));
        System.out.println("Upper cased first name of last employee using upperFirstNameChained = " + upperFirstNameChained);
        System.out.println("Upper cased first name of last employee using upperFirstNameComposed = " + upperFirstNameComposed);


        //Bi version (accepts two args)
        BiFunction<Employee, String, String> nameAndAge = (employee, name) -> {
            return name.concat(" " + employee.getAge());
        };

        String firstNameAndAgeUpper = nameAndAge.apply(employeeList.get(0), firstNameUpperAndThen.apply(employeeList.get(0)));
        System.out.println("firstNameAndAgeUpper = " + firstNameAndAgeUpper);


        //unary operator (accepts one arg and returns same type as input)
        UnaryOperator<Integer> incBy5 = i -> i + 5;
        IntUnaryOperator incBy10 = i -> incBy5.apply(i) * 2;
        System.out.println(incBy5.apply(10));
        System.out.println(incBy10.applyAsInt(10));

        //binary operator as a special case of BiFunction where all types are the same
        BinaryOperator<Double> multiply = (n1, n2) -> n1 * n2;
        System.out.println(multiply.apply(5d, 10.2));

        //Binary Predicate
        BiPredicate<Integer, String> isSame = (num, str) -> num == Integer.parseInt(str);
        System.out.println(isSame.test(1, "1"));
        System.out.println(isSame.test(2, "1"));
    }

    //using a method that accepts an <Employee, Sring> arg to either get first or last name
    private static String getName(Function<Employee, String> function, Employee employee) {
        return function.apply(employee);
    }

    public static void printEmployeesByAge(List<Employee> employees, String ageText, Predicate<Employee> ageCondition) {
        System.out.println("\n" + ageText);
        System.out.println("===========");
        for (Employee employee : employees) {
            if (ageCondition.test(employee)) System.out.println(employee.getName());
        }
    }
}
