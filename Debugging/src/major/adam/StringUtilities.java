package major.adam;

import java.util.Locale;

public class StringUtilities {
    private int charsAdded = 0;
    private StringBuilder stringBuilder = new StringBuilder();

    public void addChar(StringBuilder stringBuilder, char c) {
        stringBuilder.append(c);
        charsAdded++;
    }

    public String upperAndPrefix(String str) {
        String upper = str.toUpperCase(Locale.ROOT);
        return "Prefix_" + upper;
    }

    public String addSuffix(String str) {
        return str + "_Suffix";
    }
}
