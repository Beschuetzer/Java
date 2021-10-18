package major.adam;

public class Utilties {
    public char[] everyNthChar(char[] sourceArray, int n) {
        //return char array containing every nth char
        if (n <= 0) throw new IllegalArgumentException("n must be > 0");
        if (sourceArray == null || sourceArray.length == 0)
            throw new IllegalArgumentException("sourceArray must be at least toReturnLength 1");
        if (sourceArray.length < n) throw new IllegalArgumentException("sourceArray doesn't have n number of items");

        double toReturnLength = Math.floor((double) sourceArray.length / (double) n);
        char[] toReturn = new char[(int) toReturnLength];
        int toReturnIndex = 0;

        for (int i = 0; i < sourceArray.length; i++) {
            if (toReturnIndex >= toReturn.length) continue;
            if ((i + 1) % n == 0) {
                toReturn[toReturnIndex] = sourceArray[i];
                toReturnIndex++;
            }
        }

        for (char c : toReturn) {
            System.out.println(c);
        }

        return toReturn;
    }

    public String removePairs(String source) {
        //remove repeating characters (e.g. 'eeaaaafbcda' => 'eafbcda')
        if (source == null) return "";
        if (source.length() < 2) return source;

        StringBuilder sb = new StringBuilder();
        char[] sourceChars = source.toCharArray();
        for (int i = 0; i < sourceChars.length - 1; i++) {
            char currentChar = sourceChars[i];
            char nextChar = sourceChars[i + 1];
            if (currentChar == nextChar) {
                if (i == sourceChars.length - 2) sb.append(nextChar);
                continue;
            }

            sb.append(currentChar);
            if (i == sourceChars.length - 2) sb.append(nextChar);
        }
        return sb.toString();
    }

    public int converter(int a, int b) {
        return (a / b) + (a * 30) - 2;
    }

    public String nullIfOddLength(String source) {
        if (source.length() % 2 == 0) return source;
        return null;
    }
}
