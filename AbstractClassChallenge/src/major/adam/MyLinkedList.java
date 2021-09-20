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
        if (itemToAdd == null) return false;
        if (root.next() == null) {
            root.rightLink = itemToAdd;
            return true;
        }

        ListItem currentItem = root;
        ListItem nextItem = currentItem.next();
        while (nextItem != null) {
            if (currentItem.compareTo(itemToAdd) == 0 || nextItem.compareTo(itemToAdd) == 0) return false;

            int comparison = nextItem.next().compareTo(itemToAdd);
            if (comparison == -1) {
                currentItem.rightLink = itemToAdd;
                itemToAdd.rightLink = nextItem;
                return true;
            }

            currentItem = currentItem.next();
            nextItem = currentItem.next();
        }

        return true;
    }

    @Override
    public boolean removeItem(ListItem itemToRemove) {
        return true;
    }

    @Override
    public void traverse() {
        if (root == null) {
            System.out.println("The list is empty");
            return;
        }

        ListItem item = root;
        while (item != null) {
            System.out.println(item.getValue());
            item = item.next();
        }
    }
}
