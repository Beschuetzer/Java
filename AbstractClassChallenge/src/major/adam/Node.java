package major.adam;

public class Node extends ListItem {
    public Node(Object value) {
        super(value);
    }

    @Override
    ListItem next() {
        return rightLink;
    }

    @Override
    ListItem previous() {
        return leftLink;
    }

    @Override
    ListItem setNext(ListItem item) {
        rightLink = item;
        return rightLink;
    }

    @Override
    ListItem setPrevious(ListItem item) {
        leftLink = item;
        return leftLink;
    }

    @Override
    int compareTo(ListItem item) {
        System.out.println("compareTo-------------------------");
        System.out.println(item.getValue());

        boolean isThisValueString = super.getValue() instanceof String;
        boolean isThisValueDouble = super.getValue() instanceof Double;
        boolean isItemValueString = item.getValue() instanceof String;
        boolean isItemValueDouble = item.getValue() instanceof Double;

        String valueOne = "";
        String valueTwo = "";

        if (isItemValueString && isThisValueString) {
            valueOne = (String) super.getValue();
            valueTwo = (String) item.getValue();
        } else if (isItemValueDouble && isThisValueDouble) {
            valueOne = Double.toString((Double) super.getValue());
            valueTwo = Double.toString((Double) item.getValue());
        } else if (isItemValueDouble && isThisValueString) {
            valueOne = (String) super.getValue();
            valueTwo = Double.toString((Double) item.getValue());
        } else if (isThisValueDouble && isItemValueString) {
            valueOne = Double.toString((Double) super.getValue());
            valueTwo = (String) item.getValue();
        }

        if(item != null) {
            if (valueOne.trim().equals("") || valueTwo.trim().equals("")) return 1;
            System.out.println("valueOne: " + valueOne);
            System.out.println("valueTwo: " + valueTwo);
            System.out.println("result: " + valueOne.compareTo(valueTwo));
            return (valueOne.compareTo(valueTwo));
        } else {
            return -1;
        }
    }
}
