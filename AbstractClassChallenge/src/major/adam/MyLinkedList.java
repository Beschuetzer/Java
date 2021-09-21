package major.adam;

import java.util.List;

public class MyLinkedList implements NodeList {
    private ListItem root = null;
    private ListItem end = null;
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
            end = itemToAdd;
            size = 1;
            return true;
        }

        ListItem currentItem = root;
        ListItem nextItem = currentItem.next() != null ? currentItem.next() : null;
        ListItem previousItem = root;

        int count = 0;
        while (currentItem != null) {
                int itemToAddComparison = itemToAdd.compareTo(currentItem);

                if (itemToAddComparison < 0) {
                    //append before current
                    System.out.println("before current");
                    System.out.println("Size: " + size);
                    if (size == 1) {
                        ListItem temp = new Node(root.getValue());
                        temp.setPrevious(root);
                        temp.setNext(null);
                        end = temp;

                        root = itemToAdd;
                        root.setNext(temp);
                        root.setPrevious(null);

                        System.out.println(root.getValue());
                        System.out.println(temp.getValue());
                    } else {
                        boolean isPreviousSameAsCurrent = previousItem.compareTo(currentItem) == 0;
                        itemToAdd.setPrevious(!isPreviousSameAsCurrent ? previousItem : null);
                        itemToAdd.setNext(currentItem);
                        currentItem.setPrevious(itemToAdd);

                        if (count == 0) root = itemToAdd;
                        if (!isPreviousSameAsCurrent) previousItem.setNext(itemToAdd);
                        if (nextItem == null) end = currentItem;
                    }

                    size++;
                    count++;
                    return true;
                } else if (itemToAddComparison == 0) {
                    System.out.println("Skipping as is already present");
                    return false;
                } else {
                    if (nextItem == null) {
                        //when larger than current and at end
                        currentItem.setNext(itemToAdd);
                        itemToAdd.setPrevious(currentItem);
                        itemToAdd.setNext(null);
                        end = itemToAdd;
                        size++;
                        return true;
                    } else {
                        int nextItemComparison = itemToAdd.compareTo(nextItem);
                        if (nextItemComparison < 0) {
                            //when larger than current and less than next
                            currentItem.setNext(itemToAdd);
                            itemToAdd.setNext(nextItem);
                            itemToAdd.setPrevious(currentItem);
                            nextItem.setPrevious(itemToAdd);

                            if (nextItem.next() == null) end = nextItem;

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

    @Override
    public void traverseInReverse() {
        System.out.println("Traversing in reverse-------------------");
        if (end == null) {
            System.out.println("The list is empty");
            return;
        }

        ListItem item = end;
        while (item != null) {
            System.out.println(item.getValue());
            item = item.previous();
        }
        System.out.println("-------------------");

    }
}
