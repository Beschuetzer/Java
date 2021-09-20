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
        if (root.next() == null) {
            System.out.println("Exiting early");
            root.rightLink = itemToAdd;
            return true;
        }


        ListItem currentItem = root;
        ListItem nextItem = currentItem.next();
        while (nextItem != null) {
            if (currentItem.compareTo(itemToAdd) == 0 || nextItem.compareTo(itemToAdd) == 0) {
                System.out.println("Skipping as is already present");
                System.out.println("nextItem: " + nextItem.getValue());
                System.out.println("itemToAdd: " + itemToAdd.getValue());
                System.out.println("root: " + root.getValue());
                System.out.println(currentItem.compareTo(itemToAdd));
                System.out.println(nextItem.compareTo(itemToAdd));
                return false;
            }

            if (nextItem.next() == null) break;
            int comparison = nextItem.next().compareTo(itemToAdd);
            if (comparison == -1) {
                System.out.println("+++++++++++re routing");
                currentItem.rightLink = itemToAdd;
                itemToAdd.rightLink = nextItem;
                return true;
            }

            currentItem = currentItem.next();
            nextItem = currentItem.next();
        }

        System.out.println("Current: " + currentItem.value);
        currentItem.next().setNext(itemToAdd);
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
