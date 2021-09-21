package major.adam;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        MyLinkedList linkedList = new MyLinkedList(new Node(14));
        ListItem[] items = {
                new Node("1abvcdcd"),
                new Node(1234.5d),
                new Node(1234.8d),
                new Node("1234.5"),
                new Node("1234.7"),
        };

        for (ListItem item : items) {
            linkedList.addItem(item);
            linkedList.traverse(linkedList.getRoot());
            linkedList.traverseInReverse();
        }

        linkedList.removeItem(new Node("1234.7"));
        linkedList.removeItem(new Node("1234.5"));
        linkedList.traverse(linkedList.getRoot());
        linkedList.traverseInReverse();


//        List<String> stringArrayList = new ArrayList<String>();
//        String[] strings = {"1abv", "1234.5", "10"};
//        for (String string : strings) {
//            stringArrayList.add(string);
//        }
//        Arrays.sort(stringArrayList.toArray());
//        System.out.println(stringArrayList.toString());

        SearchTree searchTree = new SearchTree(new Node(10));
        ListItem[] nodesToAdd = {
                new Node(7),
                new Node(9),
                new Node(11),
                new Node(10),
                new Node(14),
                new Node(20),
                new Node(12),
                new Node(8)
        };

        for (ListItem node : nodesToAdd) {
            searchTree.addItem(node);
        }

        searchTree.removeItem(new Node(14));
        searchTree.removeItem(new Node(10));

        searchTree.traverse(searchTree.getRoot());
        System.out.println();
        searchTree.traversePreOrder(searchTree.getRoot());
        System.out.println();
        searchTree.traversePostOrder(searchTree.getRoot());
        System.out.println();
    }
}
