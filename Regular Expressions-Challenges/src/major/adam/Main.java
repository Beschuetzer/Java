package major.adam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String challenge2String = "I want a bike.  I want a ball";
	    String challenge2Pattern = "I want a \\w+";
        Pattern pattern = Pattern.compile(challenge2Pattern);
        Matcher matcher = pattern.matcher(challenge2String);

        matcher.results()
                .forEach((result -> {

                    System.out.println("Result: " + result.start() + " to " + result.end());
                }));

        System.out.println(challenge2String.matches(challenge2Pattern));
        //using matcher.matches() to test for a match rather than string.matches
        System.out.println(matcher.matches());

        String challenge4 = "Replace all blanks with underscores";
        System.out.println(challenge4.replaceAll(" ", "_"));
        System.out.println(challenge4.replaceAll("\\s", "_"));

        String challenge5 = "aaabcccccccccdddefffg";
        String challenge5Pattern = "a+b+c+d+e+f+g+";
        System.out.println(challenge5.matches(challenge5Pattern));

        System.out.println("-".repeat(50));
        String challenge7TrueResult = "abcd.135";
        String challenge7TrueResult2 = "kjisl.22";
        String challenge7FalseResult = "f5.12a";
        String challenge7Pattern = "^[A-z]+\\.[0-9]+$";

        System.out.println(challenge7TrueResult.matches(challenge7Pattern));
        System.out.println(challenge7TrueResult2.matches(challenge7Pattern));
        System.out.println(challenge7FalseResult.matches(challenge7Pattern));


        String challenge8 = "abcd.135uvqz.7tzik.999";
        String challenge8Pattern = "[A-z]+\\.(\\d+)";
        Pattern pattern8 = Pattern.compile(challenge8Pattern);
        Matcher matcher8 = pattern8.matcher(challenge8);

        matcher8.results()
                .forEach(matchResult -> {
                    System.out.println("Match found: " + matchResult.group(1));
                });

        String challenge9 = "abcd.135\tuvqz.7\ttzik.999\n";
        String challenge9Pattern = "[A-z]+\\.([0-9]+)\\s";
        Pattern pattern9 = Pattern.compile(challenge9Pattern);
        Matcher matcher9 = pattern9.matcher(challenge9);

        matcher9.results()
                .forEach(matchResult -> {
                    System.out.println("Match found: " + matchResult.group(1));
                });



        //getting the start and end indexes of a group using .start(int group).
        matcher9.reset();
        matcher9.results()
                .forEach(matchResult -> {
                    System.out.println("Match start at " + matchResult.start(1) + " end at " + (matchResult.end(1) - 1));
                });


        String challenge11 = "{0, 2}, {0, 5}, {1, 3}, {2, 4}";
        String challenge11Pattern = "\\{(\\d),\\s*(\\d)\\}";
//        String challenge11Pattern = "\\{(.+?)\\}";
        Pattern pattern11 = Pattern.compile(challenge11Pattern);
        Matcher matcher11 = pattern11.matcher(challenge11);
        matcher11.results()
                .forEach(matchResult -> {
                    System.out.println("Match Found" + "-".repeat(50));
                    System.out.println("First Digit: " + matchResult.group(1));
                    System.out.println("Second Digit: " + matchResult.group(2));
                });


        String zipCodeTrue = "12345";
        String zipCodeTrue2 = "12345-4419";
        String zipCodeFalse1 = "12345-333";
        String zipCodeFalse2 = "12345-53333";

        String zipCodePattern = "^\\d{5}(\\-\\d{4})?$";

        System.out.println(zipCodeTrue.matches(zipCodePattern));
        System.out.println(zipCodeTrue2.matches(zipCodePattern));
        System.out.println(zipCodeFalse1.matches(zipCodePattern));
        System.out.println(zipCodeFalse2.matches(zipCodePattern));




    }
}
