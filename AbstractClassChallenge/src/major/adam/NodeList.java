package major.adam;

public interface NodeList {
    abstract ListItem getRoot();
    abstract boolean addItem(ListItem itemToAdd);
    abstract boolean removeItem(ListItem itemToRemove);
    abstract void traverse(ListItem root);
    abstract void traverseInReverse();
}
