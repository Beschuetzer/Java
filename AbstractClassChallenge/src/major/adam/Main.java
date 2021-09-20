package major.adam;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("1234.5".compareTo(Double.toString(1234.5d)));
        MyLinkedList linkedList = new MyLinkedList(new Node(10));
        ListItem[] items = {
                new Node("1abvcdcd"),
                new Node(1234.5d),
                new Node(1234.6d),
                new Node("1234.5"),

        };

        for(ListItem item : items) {
            linkedList.addItem(item);
        }
        linkedList.traverse();

    }
}
