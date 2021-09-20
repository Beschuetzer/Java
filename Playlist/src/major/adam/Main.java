package major.adam;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Album lateralus = new Album("Lateralus", "Tool");

        List testList = new ArrayList<Song>();
        testList.add(new Song("test", 123));
        System.out.println(testList.contains(new Song("test", 123)));

                ArrayList<String> names = new ArrayList<String>(5);
                names.add("virat");
                names.add("dhoni");
                names.add("rohit");
                names.add("vijay");
                names.add("ajay");
                System.out.println("Names: " + names);
                System.out.print("Does list contains name dhoni?: ");
                System.out.println(names.contains("dhoni"));
                System.out.print("Does list contains name Sudhakar?: ");
                System.out.println(names.contains("Sudhakar"));
    }
}
