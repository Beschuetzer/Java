package major.adam;

public class MyLinkedList implements NodeList {
    private ListItem root = null;

    public MyLinkedList (ListItem root) {
        this.root = root;
    }

    @Override
    public ListItem getRoot() {
        return root;
    }

    @Override
    public boolean addItem(ListItem itemToAdd) {
        System.out.println("addItem------------------ \n\tItem:" + itemToAdd.getValue());
        if (itemToAdd == null) {
            System.out.println("ItemToAdd is null");
            return false;
        }

        System.out.println("itemToAdd.getValue(): " + itemToAdd.getValue());
        System.out.println("root.getValue(): " + root.getValue());
        if (root.compareTo(itemToAdd) < 0) {
            System.out.println("Adding as first item");
            ListItem temp = root;
            root = itemToAdd;
            itemToAdd.setNext(temp);
            return true;
        }
        if (root.next() == null) {
            System.out.println("Exiting early");
            root.rightLink = itemToAdd;
            return true;
        }


        ListItem currentItem = root;
        ListItem nextItem = currentItem.next();
        int nextItemComparison = -1;
        int currentItemComparison = -1;

        while (nextItem != null) {
            System.out.println("1--------------");
            nextItemComparison = nextItem.compareTo(itemToAdd);
            currentItemComparison = currentItem.compareTo(itemToAdd);

            if (currentItemComparison == 0 || nextItemComparison == 0) {
                System.out.println("Skipping as is already present");
                System.out.println("nextItem: " + nextItem.getValue());
                System.out.println("itemToAdd: " + itemToAdd.getValue());
                System.out.println("root: " + root.getValue());
                System.out.println(currentItem.compareTo(itemToAdd));
                System.out.println(nextItem.compareTo(itemToAdd));
                return false;
            }
            System.out.println("2---------------");

            if (nextItem.next() == null) break;
            System.out.println("3-----------------");

            int comparison = itemToAdd.compareTo(nextItem.next());

            System.out.println("Comparison: " + comparison);
            if (comparison < 0) {
                System.out.println("+++++++++++re routing");
                currentItem.rightLink = itemToAdd;
                itemToAdd.rightLink = nextItem;
                return true;
            }

            currentItem = currentItem.next();
            nextItem = currentItem.next();
        }

        System.out.println("Current: " + currentItem.value);

        if (nextItemComparison > 0) {
            ListItem temp = currentItem.next();
            currentItem.setNext(itemToAdd);
            itemToAdd.setNext(temp);
        } else {
            currentItem.next().setNext(itemToAdd);
        }

        System.out.println("----------------------------");
        return true;
    }

    @Override
    public boolean removeItem(ListItem itemToRemove) {
        if (itemToRemove == null || root == null) return false;
        if (root.getValue() == itemToRemove.getValue() ) {
            root = null;
            return true;
        }

        ListItem current = root;
        ListItem previous = root;
        ListItem next = current.next();

        while (next != null) {
            if (next.compareTo(itemToRemove) == 0) {
                previous.setNext(next);
            }

            previous = current;
            current = current.next();
        }

        return false;
    }

    @Override
    public void traverse() {
        System.out.println("Traversing-------------------");
        if (root == null) {
            System.out.println("The list is empty");
            return;
        }

        ListItem item = root;
        while (item != null) {
            System.out.println(item.getValue());
            item = item.next();
        }
        System.out.println("-------------------");

    }
}
