package major.adam;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ISaveable player = new Player(100, 10, 10, "Sword");
        System.out.println(player.toString());
        save(player);
        load(player);
    }

    public static boolean save(ISaveable toSave) {
        List<String> toSaveArrayList = toSave.save();
        for (String itemInList : toSaveArrayList) {
            System.out.println("Saving " + itemInList + " to storage device...");
        }
        return true;
    }

    public static void load(ISaveable toLoad) {
        List<String> simulatedValues = readValues();
        toLoad.load(simulatedValues);
    }

    public static ArrayList<String> readValues() {
        ArrayList<String> values = new ArrayList<String>();

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        int index = 0;
        System.out.println("Choose\n" +
                "1 to enter a string\n" +
                "0 to quit");

        while (!quit) {
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 0:
                    quit = true;
                    break;
                case 1:
                    System.out.print("Enter a string: ");
                    String stringInput = scanner.nextLine();
                    values.add(index, stringInput);
                    index++;
                    break;
            }
        }
        return values;
    }
}
