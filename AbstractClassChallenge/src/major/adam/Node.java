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
        if (item.getValue() == super.getValue()) return 0;

        boolean isThisValueString = super.getValue() instanceof String;
        boolean isThisValueDouble = super.getValue() instanceof Double;
        boolean isItemValueString = item.getValue() instanceof String;
        boolean isItemValueDouble = item.getValue() instanceof Double;
        boolean isItemInteger = item.getValue() instanceof Integer;
        boolean isThisInteger = super.getValue() instanceof Integer;

        String valueOne = "";
        String valueTwo = "";

        if (isItemInteger && isThisInteger) {
            if ((Integer) super.getValue() < (Integer) item.getValue()) return -1;
            else if (super.getValue() == item.getValue()) return 0;
            else return 1;
        }
        else if (isItemValueString && isThisValueString) {
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
        } else {
            try {
                valueOne = (String) super.getValue();
            } catch (Exception e) {
                try {
                    valueOne = Integer.toString((Integer) super.getValue());
                } catch (Exception e2) {
                    valueOne = Double.toString((Double) super.getValue());
                }
            }

            try {
                valueTwo = (String) item.getValue();
            } catch (Exception e) {
                try {
                    valueTwo = Integer.toString((Integer) item.getValue());
                } catch (Exception e2) {
                    valueTwo = Double.toString((Double) item.getValue());
                }
            }
        }

//        System.out.println("isThisValueString: " + isThisValueString);
//        System.out.println("isThisValueDouble: " + isThisValueDouble);
//        System.out.println("isItemValueDouble: " + isItemValueDouble);
//        System.out.println("isItemValueString: " + isItemValueString);

        if(item != null) {
            System.out.println("valueOne: " + valueOne);
            System.out.println("valueTwo: " + valueTwo);
            if (valueOne.trim().equals("") || valueTwo.trim().equals("")) return 1;
            System.out.println("item: " + item.getValue());
            System.out.println("super: " + super.getValue());
            System.out.println("result: " + valueOne.compareTo(valueTwo));
            return (valueOne.compareTo(valueTwo));
        } else {
            return -1;
        }
    }
}
