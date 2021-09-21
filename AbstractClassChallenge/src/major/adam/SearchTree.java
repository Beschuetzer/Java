package major.adam;

public class SearchTree implements NodeList{
    private ListItem root;
    private int size;

    public SearchTree(ListItem root) {
        this.root = root;
        size = 1;
    }

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

        ListItem currentNode = root;
        while (currentNode != null) {
            int itemComparison = itemToAdd.compareTo(currentNode);

            if (itemComparison == 0) return false;
            else if (itemComparison > 0) {
                if (currentNode.next() == null) {
                    currentNode.setNext(itemToAdd);
                    return true;
                }
                currentNode = currentNode.next();
            } else if (itemComparison < 0) {
                if (currentNode.previous() == null) {
                    currentNode.setPrevious(itemToAdd);
                    return true;
                }
                currentNode = currentNode.previous();
            }
        }

        return false;
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
        int count = 0;
        while (current != null) {
            if (current.compareTo(itemToRemove) == 0) {
                if (size == 1) {
                    root = null;
                } else {
//                    boolean previousIsSameAsCurrent = previous.compareTo(current) == 0;

                    if (count == 0) {
                        root = next;
                        root.setPrevious(null);
                    } else {
                        previous.setNext(next);
                        next.setPrevious(previous);
                    }
//                    if (!previousIsSameAsCurrent) previous.setNext(next);
                }

                size = size <= 1 ? size = 0 : size--;
                return true;
            }

            previous = current;
            current = current.next();
            next = current != null ? current.next() : null;
            count++;
        }

        return false;
    }

    @Override
    public void traverse(ListItem root) {
        if (root == null) return;
        traverse(root.previous());
        System.out.print(String.format("%s, ", root.getValue()));
        traverse(root.next());

    }

    public void traversePreOrder(ListItem root) {
        if (root == null) return;
        System.out.print(String.format("%s, ", root.getValue()));
        traverse(root.previous());
        traverse(root.next());

    }

    public void traversePostOrder(ListItem root) {
        if (root == null) return;
        traverse(root.previous());
        traverse(root.next());
        System.out.print(String.format("%s, ", root.getValue()));

    }

    @Override
    public void traverseInReverse() {
        traverse(this.root);
    }
}
