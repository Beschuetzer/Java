package major.adam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String str = "I'm a string";

        //the entire string has to match when using String::matches
        //true
        System.out.println(str.matches("^I.*"));
        System.out.println(str.matches("I.*"));

        //false
        System.out.println(str.matches("m.*"));

        System.out.println("Harry".replaceAll("[hH]", "M"));


        String alpahNumeric = "a4bcD e1fGhi 3i dkL4P PIIIIj 929x z";
        System.out.println("alpahNumeric = " + alpahNumeric);

        //all but
        System.out.println(alpahNumeric.replaceAll("[^iPp]", "X"));
        System.out.println(alpahNumeric.replaceAll("[B-Fb-f0-5]", "X"));

        //specifying case in-sensitive (no separator after '(?i)') us special construct
        System.out.println(alpahNumeric.replaceAll("(?i)[b-f0-5]", "\u00c4"));

        //if input is unicode, add 'u' to the special construct
        System.out.println(alpahNumeric.replaceAll("(?iu)[b-f0-5]", "\u00c4"));


        //digits => \\d
        System.out.println(alpahNumeric.replaceAll("[0-9]", "X"));
        System.out.println(alpahNumeric.replaceAll("\\d", "X"));

        //Space characters => \\s
        System.out.println(alpahNumeric.replaceAll("\\s", "X"));

        //AlpahNumeric Chars ([0-9a-zA-Z]) => \\w
        System.out.println(alpahNumeric.replaceAll("\\w", "X"));


        //word boundary => \\b
        System.out.println(alpahNumeric.replaceAll("\\b", "<tag>"));


        //quantifiers
        System.out.println(alpahNumeric.replaceAll("e{3}", "X"));


        //Pattern Class
        //use .compile to convert a string to a regExp for use with a Matcher instance
        Pattern p = Pattern.compile(".*b.*", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher m = p.matcher("abbbbb");
        boolean b = m.matches();
        System.out.println(b);

        //when only using a pattern one time
        boolean matches = Pattern.matches("a*b.*", "abbbbb");
        System.out.println("matches = " + matches);


        //Matcher .find() and .group()
        String someHtml = "<div class=\"main-content\">\n" +
                "<footer class=\"udlite-footer ud-app-loader udlite-in-udheavy \" data-module-id=\"footer\" data-module-args=\"{&quot;is_udlite_footer&quot;:true,&quot;localeSelect&quot;:{&quot;currentLanguageId&quot;:&quot;en_US&quot;}}\" data-module-priority=\"-10\">\n" +
                "<h2 class=\"ud-component--footer--bai-banner\">Some Header</h2>\n" +
                "<h2 class=\"unstyled-list link-column \">ANother header</h2>" +
                "</footer>" +
                "</div>";

        String pattern = "<h2.*>";
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(someHtml);
        System.out.println("-".repeat(50));
        System.out.println(matcher.matches());

        int count = 0;
        matcher.reset();
        while (matcher.find()) {
            count++;
            System.out.println("occurrence " + count + " : " + matcher.start() + " to " + matcher.end());
        }

        //reluctant/lazy quantifier
        String h2GroupPattern = "(<h2.*?>)(.*?)(</h2>)";
        Pattern groupPattern = Pattern.compile(h2GroupPattern);
        Matcher groupMatcher = groupPattern.matcher(someHtml);
        System.out.println(groupMatcher.matches());
        groupMatcher.reset();

        while (groupMatcher.find()) {

            System.out.println("Entire Pattern: " + groupMatcher.group(0));
            System.out.println("Start Tag: " + groupMatcher.group(1));
            System.out.println("innerHtml: " + groupMatcher.group(2));
            System.out.println("Closing Tag: " + groupMatcher.group(3));
        }

        System.out.println("-".repeat(50));

        //and, or, and not
        //and is implicit
        //or '|'
        //not = [^...] but it consumes one character

        String tvTest = "tstvtkt";
        //the [^v] requires at least one character to be present, so the string-final 't' is not matched
        String tNotVRegExp = "t[^v]";
        Pattern pattern2 = Pattern.compile(tNotVRegExp);
        Matcher matcher2 = pattern2.matcher(tvTest);

        int count2 = 0;
        while (matcher2.find()) {
            count2++;
            System.out.println("occurrence " + count2 + " : " + matcher2.start() + " to " + matcher2.end());
        }

        System.out.println("-".repeat(50));
        System.out.println("Negative Lookahead");

        //negative lookahead expression doesn't consume characters (so it can match nothing
        //and the characters are not included in the matched text)
        //lookaheads always start with '(?...)'
        //the '!' is the negation
        String tNotVNegativeLookAheadRegExp = "t(?!v)";
        Pattern pattern3 = Pattern.compile(tNotVNegativeLookAheadRegExp);
        Matcher matcher3 = pattern3.matcher(tvTest);

        int count3 = 0;
        while (matcher3.find()) {
            count3++;
            System.out.println("occurrence " + count3 + " : " + matcher3.start() + " to " + matcher3.end());
        }

        //positive lookahead
        System.out.println("-".repeat(50));
        System.out.println("Positive Lookahead");
        String tFollowedByV = "t(?=v)";
        Pattern pattern4 = Pattern.compile(tFollowedByV);
        Matcher matcher4 = pattern4.matcher(tvTest);

        int count4 = 0;
        while (matcher4.find()) {
            count4++;
            System.out.println("occurrence " + count4 + " : " + matcher4.start() + " to " + matcher4.end());
        }

        String openParentheses = "[\\(]{1}";
        String closeParentheses = "[\\)]{1}";
        String threeDigits = "[0-9]{3}";
        String separator = "[ \\-]{1}";
        String fourDigits = "[0-9]{4}";
        String dash = "[\\-]{1}";
//        String phoneNumberRegExp = String.format("^(%s%s%s%s%s%s%s)$",
        String phoneNumberRegExp = String.format("^((%s%s%s|%s)%s%s%s%s)$",
                openParentheses,
                threeDigits,
                closeParentheses,
                threeDigits,
                separator,
                threeDigits,
                separator,
                fourDigits
        );

        System.out.println(phoneNumberRegExp);

        //true
        System.out.println("(123)-456-3888".matches(phoneNumberRegExp));
        System.out.println("(123) 456-3888".matches(phoneNumberRegExp));
        System.out.println("123-456-3888".matches(phoneNumberRegExp));
        System.out.println("123 456 3888".matches(phoneNumberRegExp));
        System.out.println("123 456-3888".matches(phoneNumberRegExp));

        //false
        System.out.println("(123)456-3888".matches(phoneNumberRegExp));
        System.out.println("(123)  456-3888".matches(phoneNumberRegExp));
        System.out.println("(123)  s456-3888".matches(phoneNumberRegExp));
        System.out.println("1234563888".matches(phoneNumberRegExp));
        System.out.println("123)-456-3888".matches(phoneNumberRegExp));
        System.out.println("(123-456-3888".matches(phoneNumberRegExp));

    }
}
