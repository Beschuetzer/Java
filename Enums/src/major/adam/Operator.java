package major.adam;

enum Operator {
    ADD {
        @Override int execute(final int num1, final int num2) {
            return num1 + num2;
        }
    },
    SUBTRACT {
        @Override int execute(final int num1, final int num2) {
            return num1 - num2;
        }
    },
    MULTIPLY {
        @Override int execute(final int num1, final int num2) {
            return num1 * num2;
        }
    },
    DIVIDE {
        @Override int execute(final int num1, final int num2) {
            if (num2 != 0) {
                return num1 / num2;
            } else {
                System.out.println("Can't divide by zero.");
            }
            return 0;
        }
    };

    abstract int execute(int num1, int num2);

}
