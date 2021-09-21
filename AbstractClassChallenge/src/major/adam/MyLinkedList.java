package major.adam;

import java.util.List;

public class MyLinkedList implements NodeList {
    private ListItem root = null;
    private int size;

    public MyLinkedList (ListItem root) {
        this.root = root;
        this.size = 1;
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

        if (root == null) {
            root = itemToAdd;
            size = 1;
            return true;
        }

        ListItem currentItem = root;
        ListItem nextItem = currentItem.next() != null ? currentItem.next() : null;
        ListItem previousItem = root;

        while (currentItem != null) {
                int itemToAddComparison = itemToAdd.compareTo(currentItem);

                if (itemToAddComparison < 0) {
                    //append before current
                    if (size == 1) {
                        ListItem temp = root;
                        root = itemToAdd;
                        itemToAdd.setNext(temp);
                    } else {
                        previousItem.setNext(itemToAdd);
                        itemToAdd.setNext(currentItem);
                    }

                    size++;
                    return true;
                } else if (itemToAddComparison == 0) {
                    System.out.println("Skipping as is already present");
                    return false;
                } else {
                    if (nextItem == null) {
                        //when larger than current and at end
                        currentItem.setNext(itemToAdd);
                        size++;
                        return true;
                    } else {
                        int nextItemComparison = itemToAdd.compareTo(nextItem);
                        if (nextItemComparison < 0) {
                            //when larger than current and less than next
                            currentItem.setNext(itemToAdd);
                            itemToAdd.setNext(nextItem);
                            size++;
                            return true;
                        }
                    }
                }

            previousItem = currentItem;
            currentItem = currentItem.next();
            nextItem = currentItem != null ? currentItem.next() : null;
        }

        return true;
    }

    @Override
    public boolean removeItem(ListItem itemToRemove) {
        if (itemToRemove == null || root == null) return false;
        if (root.getValue() == itemToRemove.getValue() ) {
            root = null;
            size = size <= 1 ? size = 0 : size--;
            return true;
        }

        ListItem current = root;
        ListItem previous = root;
        ListItem next = current.next() != null ? current.next() : null;

        while (current != null) {
            if (current.compareTo(itemToRemove) == 0) {
                if (size == 1) {
                    root = null;
                }

                previous.setNext(next);
                size = size <= 1 ? size = 0 : size--;
                return true;
            }

            previous = current;
            current = current.next();
            next = current != null ? current.next() : null;
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
