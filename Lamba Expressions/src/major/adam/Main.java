package major.adam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

interface UpperConcat {
    String upperAndConcat(String s1, String s2);
}

public class Main {

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("Running from lambda!");
        }).start();

        Player tom = new Player("Tom", 21);
        Player adam = new Player("Adam", 22);
        Player jon = new Player("Jon", 23);

        Player[] players = {tom, adam, jon};
        List<Player> playersList = new ArrayList<>();

        for (Player player : players) {
            playersList.add(player);
        }

        playersList = sortPlayers(playersList, PlayerSort.BY_AGE);
        printArray(playersList);

        //Without Lambda Expression
//        String sillyString = doStringStuff(new UpperConcat() {
//            @Override
//            public String upperAndConcat(String s1, String s2) {
//                return s1.toUpperCase() + s2.toUpperCase();
//            }
//        }, playersList.get(0).getName(), playersList.get(1).getName());

        //Creating a separate variable as a lambda expression
        UpperConcat uc = (s1, s2) -> s1.toUpperCase() + s2.toUpperCase();
        String sillyString = doStringStuff(uc, playersList.get(0).getName(), playersList.get(1).getName());


        AnotherClass anotherClass = new AnotherClass();
        anotherClass.doSomething(playersList.get(0).getName(), playersList.get(1).getName());
        anotherClass.lambdaScopeExample(playersList.get(0).getName(), playersList.get(1).getName());


        //Enhanced for loop (player created by loop is effectively final for each iteration)
        for (Player player : playersList) {
            System.out.println(player.getName());
            new Thread(() -> System.out.println(player.getAge())).start();
        }

        //not effectively final
//        Player player;
//        for (int i = 0; i < playersList.size(); i++) {
//            player = playersList.get(i);
//            new Thread(() -> System.out.println(player.getAge())).start();
//        }

//        .foreach
        System.out.println("-".repeat(50));

          playersList.forEach((player -> {
              System.out.println(player.getName());
              System.out.println(player.getAge());
          }));
    }

    private static void printArray(List<? extends Object> array) {
        for (Object obj : array) {
            if (Pattern.matches(".*Player.*", obj.getClass().toString())) {
                Player player = (Player) obj;
                System.out.println(player.getName());
            } else {
                System.out.println(obj);
            }
        }
    }

    private static List<Player> sortPlayers(List<Player> players, PlayerSort sortMethod) {
        if (sortMethod == PlayerSort.BY_AGE) {
            Collections.sort(players, (p1, p2) -> {
                int p1Age = p1.getAge();
                int p2Age = p2.getAge();
                if (p1Age < p2Age) return -1;
                else if (p1Age > p2Age) return 1;
                else {
                    assert (p1 == p2);
                    return 0;
                }
            });
        } else if (sortMethod == PlayerSort.BY_NAME) {
            Collections.sort(players, (p1, p2) -> {
                return p1.getName().compareToIgnoreCase(p2.getName());
            });
        }

        return players;
    }

    public static final String doStringStuff(UpperConcat uc, String s1, String s2) {
        return uc.upperAndConcat(s1, s2);
    }

    public enum PlayerSort {
        BY_NAME,
        BY_AGE,
    }
}

class Player {
    private String name;
    private int age;

    public Player(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class AnotherClass {
    public String doSomething(String string1, String string2) {
        System.out.println("Class name of AnotherClass is " + getClass().getSimpleName());
        return Main.doStringStuff((s1, s2) -> {
                    System.out.println("Anonymous class name is " + getClass().getSimpleName());
            return s1.toUpperCase() + s2.toUpperCase();
        },
                string1,
                string2
        );
    }

    public String lambdaScopeExample(String s1, String s2) {
        int i = 0;
        UpperConcat uc = (str1, str2) -> {
            System.out.println("i = " + i);
            return str1.toUpperCase() + str2.toUpperCase();
        };

        //Can't change local variables that are also in the lambda expression
//        i++;
        return Main.doStringStuff(uc, s1, s2);
    }
}
