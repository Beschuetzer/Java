package major.adam;

public class Main {

    public static void main(String[] args) {
        
        System.out.println(getDurationString(3));
    }

    public static String getDurationString(int minutes, int seconds) {
        if (minutes < 0 || seconds < 0 || seconds >= 60) return "Invalid Value";

        String format = "%sh %sm %ss";

        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;


        return String.format(format, hours, remainingMinutes, seconds);
    }

    public static String getDurationString(int seconds) {
        if (seconds < 0) return "Invalid Value";

        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;

        return getDurationString(minutes, remainingSeconds);
    }
}
