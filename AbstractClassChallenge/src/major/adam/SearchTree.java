package major.adam;

public class SearchTree implements NodeList {
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
        ListItem rightNode = root.next();
        ListItem leftNode = root;

        while (currentNode != null) {
            int itemComparison = itemToAdd.compareTo(currentNode);
            if (itemComparison == 0) return false;
            else if (itemComparison > 0) {
                if (rightNode == null) {
                    currentNode.setNext(itemToAdd);
                    return true;
                }

                currentNode = rightNode;
            } else if (itemComparison < 0) {
                if (leftNode == null) {
                    currentNode.setPrevious(itemToAdd);
                    return true;
                }
                currentNode = leftNode;
            }

            rightNode = currentNode.next();
            leftNode = currentNode.previous();
        }

        return false;
    }

    @Override
    public boolean removeItem(ListItem itemToRemove) {
        if (itemToRemove == null || root == null) return false;
        if (root.getValue() == itemToRemove.getValue()) {
            root = null;
            size = size <= 1 ? size = 0 : size--;
            return true;
        }

        ListItem current = root;
        ListItem previous = null;

        int iterations = 1;
        while (current != null) {
            System.out.println("current.getValue(): " + current.getValue());
            System.out.println("itemToRemove.getValue(): " + itemToRemove.getValue());
            if (current.getValue() == itemToRemove.getValue()) {
                if (iterations == 1) {
                    if (size == 1) {
                        root = null;
                        size = 0;
                        return  true;
                    }

                    //NEED TO FIGURE OUT HOW TO HANDLE ROOT REMOVAL
                    boolean isLeftNull = root.previous() == null;
                    boolean isRightNull = root.next() == null;
                    if (!isLeftNull) {
                        root = root.previous();

                        if (!isRightNull)
                    } else if (!isRightNull) {
                        root = root.next();
                    }

                    size = size <= 1 ? 0 : size--;
                } else {
                    int comparisonWithPrevious = itemToRemove.compareTo(previous);
                    boolean isLeftNull = current.previous() == null;
                    boolean isRightNull = current.next() == null;

                    if (comparisonWithPrevious > 0) {
                        if (!isLeftNull) {
                            previous.setNext(current.previous());
                            if (current.next() != null) addItem(current.next(), current.previous(), true);
                        } else {
                            previous.setNext(current.next());
                        }
                    } else if (comparisonWithPrevious < 0) {
                        if (!isRightNull) {
                            previous.setPrevious(current.next());
                            if (current.previous() != null) addItem(current.previous(), current.next(), false);

                        } else {
                            previous.setPrevious(current.previous());
                        }
                    }
                    size = size <= 1 ? 0 : size--;
                }
                return true;
            }

            previous = current;
            current = itemToRemove.compareTo(current) > 0 ? current.next() : current.previous();
            iterations++;
        }

        return false;
    }

    private void addItem(ListItem itemToAdd, ListItem root, boolean addToRight) {
        if (root == null || itemToAdd == null) return;

        if (addToRight) {
            ListItem furthestRight = root;
            while (furthestRight.next() != null) {
                furthestRight = furthestRight.next();
            }
            furthestRight.setNext(itemToAdd);
        } else {
            ListItem furthestLeft = root;
            while (furthestLeft.previous() != null) {
                furthestLeft = furthestLeft.previous();
            }
            furthestLeft.setPrevious(itemToAdd);
        }

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
