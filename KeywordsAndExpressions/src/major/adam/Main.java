package major.adam;

public class Main {
    static double conversionFactor = 1.609;

    public static void main(String[] args) {
	    printConversion(5.0);
         
    }

    public static String displayHighScorePosition(String playerName, int position) {
        return String.format("%s managed to get into position %s", playerName, position);
    }

    public static long toMilesPerHour(double kilometersPerHour) {
        
        if (kilometersPerHour < 0) return -1;
        return (long) (kilometersPerHour * conversionFactor);
    }

    public static void printConversion(double kilometersPerHour) {

        if (kilometersPerHour < 0) {
            System.out.println("Invalid Value");
            return;
        }
        

        String format = "%s km/h = %s mi/h";

        long rounded = Math.round((double)(kilometersPerHour / Main.conversionFactor));

        System.out.println(String.format(format, kilometersPerHour, rounded));
    }
}