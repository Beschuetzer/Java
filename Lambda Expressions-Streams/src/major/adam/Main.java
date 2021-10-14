package major.adam;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<String> bingoNumbers = new ArrayList<>();
        for (char letter = 'A'; letter <= 'Z'; letter++) {
//            System.out.println(letter);
            for (int i = 1; i < 100; i++) {
                String number = String.format("%2s", i).replace(' ', '0');
//                System.out.println(number);
                String joined = letter + number;
                bingoNumbers.add(joined);
            }
        }

//        bingoNumbers.forEach(num -> System.out.println(num));

        //Using Streams
        Spliterator<String> spliterator = bingoNumbers
                .stream()
                .map(String::toUpperCase)
                .filter(str -> {
                    char firstChar = str.charAt(0);
                    return firstChar >= 'G' && firstChar <= 'I';
                })
                .sorted()
                .spliterator();
//                .forEach(System.out::println);

        //System.out::println is the Action form of the statement
        spliterator.forEachRemaining(s -> System.out.println(s));
        spliterator.forEachRemaining(System.out::println);


        //creating a stream using .of()
        Stream<String> numStream = Stream.of(
                "I23",
                "I25",
                "I24",
                "N42"
        );
        Stream<String> numStream2 = Stream.of(
                "N40",
                "N42",
                "N41",
                "I24"
        );

        System.out.println("-".repeat(50));
        Stream<String> concatStream = Stream.concat(numStream, numStream2);
        System.out.println("Distinct Strings: " + concatStream
                .peek((str) -> {
                    System.out.println("peeking: " + str);
                })
                .distinct()
                .count()
        );


        //Flat Map and best practices
        //used to map a single obj to more than one obj (.map() is one-to-one)
        //requires you to return a stream
        //USE WHEN WANT TO PERFORM OPERATIONS ON A LIST, BUT THE LIST ISN'T THE SOURCE
        Employee[] employees = {
                new Employee("John Doe", 30),
                new Employee("Jack Stalk", 40),
                new Employee("Jill Hill", 32),
                new Employee("Adam Minor", 19),
                new Employee("Tom Major", 19),
                new Employee("Jon Jeffries", 40)
        };

        Department hr = new Department("Human Resources");
        hr.addEmployees(employees);

        Department accounting = new Department("Accounting");
        accounting.addEmployee(employees[1]);

        List<Department> departments = new ArrayList<>();
        departments.add(hr);
        departments.add(accounting);

        departments.stream()
                .flatMap(department -> {
                    System.out.println("-".repeat(50));
                    System.out.println(department.getName());
                    return department.getEmployees().stream();
                })
                .forEach(System.out::println);

        //.collect() = terminal operation that collects the
        //elements in a stream into a different type of result
        //used to effectively store the results of stream as a list or some other data structure
        System.out.println("-".repeat(50));

        //Using the more basic .collect() constructor
        List<String> sortedNumbers = bingoNumbers.stream()
                .map(String::toUpperCase)
                .filter(s -> {
                    char firstChar = s.charAt(0);
                    Integer number = Integer.parseInt(s.substring(1));
                    return firstChar >= 'I' && firstChar <= 'L' && number >= 50 && number <= 59;
                })
                .sorted()
                .collect(Collectors.toList());

        //Using the more full .collect() constructor
        List<String> sortedNumbers2 = bingoNumbers.stream()
                .map(String::toUpperCase)
                .filter(s -> {
                    char firstChar = s.charAt(0);
                    Integer number = Integer.parseInt(s.substring(1));
                    return firstChar >= 'I' && firstChar <= 'L' && number >= 50 && number <= 59;
                })
                .sorted()
                //.collect(Supplier, accumulator (how items are added) (ArrayList::add is a method reference), combiner (how two collections are combined)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        //Mapping/Grouping all employees by age (department list as source and each department has list of employees)
        Map<Integer, List<Employee>> groupedByAge = departments.stream()
                .flatMap(department -> department.getEmployees().stream())
                .distinct()
                .collect(Collectors.groupingBy(employee -> employee.getAge()));

        sortedNumbers2.forEach(System.out::println);
        groupedByAge.forEach((age, employeeList) -> {
            System.out.println("-".repeat(50));
            System.out.println("People aged " + age);
            employeeList.forEach(employee -> System.out.println(employee.getName()));
        });


        System.out.println("-".repeat(50));
        System.out.println("Last Person with youngest age");
        //.reduce()
        //three versions
        //without identity and combiner
        departments.stream()
                .flatMap(department -> department.getEmployees().stream())
                .reduce((e1, e2) -> e1.getAge() < e2.getAge() ? e1 : e2)
                .ifPresent(System.out::println);

        //like js (with identity [initial value] and accumulator)
        //getting total age of all employees in all departments
        Integer result = departments.stream()
                .flatMap(department -> department.getEmployees().stream())
                .distinct()
                .map(employee -> employee.getAge())
//                .reduce(0, (totalAge, currentEmployeeAge) -> totalAge + currentEmployeeAge);
                .reduce(0, Integer::sum);

        //using in parallel
        Integer resultParallel = departments.stream()
                .flatMap(department -> department.getEmployees().parallelStream())
                .distinct()
                .map(employee -> employee.getAge())
                        .reduce(0, (a, b) -> a + b, Integer::sum);

        System.out.println(result);
        System.out.println(resultParallel);


        //storing intermediate stream, then executing later using a terminal operator
        Stream<String> distinctLengthThree = Stream.of("A", "C","DDA","ACCA","ABBA")
                .filter(s -> {
                    System.out.println(s);
                    return s.length() <= 3;
                })
                .distinct();

        System.out.println(distinctLengthThree.count());

        //this throws an exception as stream has already been terminated
        System.out.println(distinctLengthThree.count());

    }
}
