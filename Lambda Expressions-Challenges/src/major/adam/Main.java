package major.adam;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        //1
        Callable<Boolean> callable = () -> {
            System.out.println("-".repeat(50));

            String myString = "Let's split this up";
            String[] split = myString.split(" ");
            for (String str : split) {
                System.out.println(str);
            }
            return true;
        };

        FutureTask<Boolean> task = new FutureTask<>(callable);
        new Thread(task).start();
        try {
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //2
        Function<String, String> everySecondChar = (str -> {
            StringBuilder returnVal = new StringBuilder();
            for (int i = 0; i < str.length(); i += 2) {
                returnVal.append(str.charAt(i));
            }
            return returnVal.toString();
        });

        //3
        System.out.println(everySecondChar.apply("1234567890"));

        //4
        System.out.println(executeFunction("1234567890", everySecondChar));

        //5-7
        Supplier<String> iLoveJava = () -> {
            return "I love Java!";
        };
        String supplierResult = iLoveJava.get();
        System.out.println(supplierResult);

        //9
        System.out.println("-".repeat(50));
        List<String> topNames2015 = Arrays.asList(
                "Amelia",
                "Olivia",
                "emily",
                "Isla",
                "Ava",
                "oliver",
                "Jack",
                "Charlie",
                "harry",
                "Jacob"
        );

        //10-11
        topNames2015.stream()
                .map(str -> {
                    return str.substring(0, 1).toUpperCase() + str.substring(1);
                })
                .sorted()
                .forEach(System.out::println);

        //12
        Long countOfNamesStartingWithA = topNames2015.stream()
                .map(str -> {
                    return str.substring(0, 1).toUpperCase() + str.substring(1);
                })
                .filter(str -> {
                    return str.substring(0, 1).equalsIgnoreCase("A");
                })
                .count();
        System.out.println("countOfNamesStartingWithA = " + countOfNamesStartingWithA);


        //14
        List<String> sortedUpperList = topNames2015.stream()
                .map(str -> {
                    return str.substring(0, 1).toUpperCase() + str.substring(1);
                })
                .peek(str -> {
                    System.out.println("peeking: " + str);
                })
                .sorted()
                .collect(Collectors.toList());

        String niceConcat = IntStream
                .range(0, sortedUpperList.size())
                .mapToObj(i -> {
                    if (i == 0) return sortedUpperList.get(i);
                    if (i == sortedUpperList.size() - 1) return ", and " + sortedUpperList.get(i);
                    return ", " + sortedUpperList.get(i);
                })
                .reduce("", String::concat);
        System.out.println(niceConcat);



        //using IntStream to get indices, which are mapped to an item
        System.out.println("-".repeat(50));
        String concatAbomination2 = IntStream
                .range(0, topNames2015.size())
                .mapToObj(i -> {
                    String name = topNames2015.get(i);
                    return name.substring(0, 1).toUpperCase() + name.substring(1);
                })
                .sorted()
                .reduce("", (str1, str2) -> {
                    if (str1.equalsIgnoreCase("")) return str2;

                    return str1 + ", " + str2;
                });

        String replaced = concatAbomination2.replaceAll(",$", " ");
        System.out.println("replaced = " + replaced);
    }

    //4
    public static String executeFunction(String strToUse, Function<String, String> functionToUse) {
        return functionToUse.apply(strToUse);
    }


    public List<String> getEvenIndexedStrings(String[] names) {
        List<String> evenIndexedNames = IntStream
                .range(0, names.length)
                .filter(i -> i % 2 == 0)
                .mapToObj(i -> names[i])
                .collect(Collectors.toList());

        return evenIndexedNames;
    }
}
