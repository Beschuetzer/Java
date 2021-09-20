package major.adam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        MyLinkedList linkedList = new MyLinkedList(new Node(10));
        ListItem[] items = {
                new Node("1abvcdcd"),
                new Node(1234.5d),
                new Node(1234.8d),
                new Node("1234.5"),
        };

        for(ListItem item : items) {
            linkedList.addItem(item);
            linkedList.traverse();
        }


        List<String> stringArrayList = new ArrayList<String>();
        String[] strings = {"1abv", "1234.5", "10"};
        for (String string : strings) {
            stringArrayList.add(string);
        }
        Arrays.sort(stringArrayList.toArray());
        System.out.println(stringArrayList.toString());

        System.out.println("1abvcdc".compareTo("1234.5"));
    }
}
