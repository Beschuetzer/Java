package com.timbuchalka;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by timbuchalka on 2/04/2016.
 */
public class Locations implements Map<Integer, Location> {
    private static final Map<Integer, Location> locations = new HashMap<Integer, Location>();

    static {
        System.out.println("Running static in locations");
        Scanner scanner = null;

        //reading exits
        Map<Integer, Map<String,Integer>> tempExitsMap = new HashMap<>();
        try {
            scanner = new Scanner(new BufferedReader(new FileReader("directions.txt")));
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
//                int loc = scanner.nextInt();
//                scanner.skip(scanner.delimiter());
//                String exits = scanner.nextLine();
                String nextLine = scanner.nextLine();
                String[] split = nextLine.split(",", 2);
                tempExitsMap.put(Integer.parseInt(split[0]), createMapFromExits(split[1]));
            }
        }catch (IOException e ) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }

        try {
            scanner = new Scanner(new FileReader("locations.txt"));
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                int location = scanner.nextInt();
                scanner.skip(scanner.delimiter());
                String description = scanner.nextLine();

                locations.put(location, new Location(location, description, tempExitsMap.get(location)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) scanner.close();
        }
//
//
//        Map<String, Integer> tempExit = new HashMap<String, Integer>();
//        locations.put(0, new Location(0, "You are sitting in front of a computer learning Java", null));
//
//        tempExit = new HashMap<String, Integer>();
//        tempExit.put("W", 2);
//        tempExit.put("E", 3);
//        tempExit.put("S", 4);
//        tempExit.put("N", 5);
//        locations.put(1, new Location(1, "You are standing at the end of a road before a small brick building", tempExit));
//
//        tempExit = new HashMap<String, Integer>();
//        tempExit.put("N", 5);
//        locations.put(2, new Location(2, "You are at the top of a hill", tempExit));
//
//        tempExit = new HashMap<String, Integer>();
//        tempExit.put("W", 1);
//        locations.put(3, new Location(3, "You are inside a building, a well house for a small spring", tempExit));
//
//        tempExit = new HashMap<String, Integer>();
//        tempExit.put("N", 1);
//        tempExit.put("W", 2);
//        locations.put(4, new Location(4, "You are in a valley beside a stream", tempExit));
//
//        tempExit = new HashMap<String, Integer>();
//        tempExit.put("S", 1);
//        tempExit.put("W", 2);
//        locations.put(5, new Location(5, "You are in the forest", tempExit));

    }

    private static Map<String, Integer> createMapFromExits(String mapAsString) {
        Map<String, Integer> toReturn = new HashMap<>();
        String leftRemoved = mapAsString.replace('{', ' ');
        String bothRemoved = leftRemoved.replace('}', ' ');
        String[] split = bothRemoved.trim().split(",");

        for (String keyValuePair : split) {
            String[] splitKeyValuePair = keyValuePair.split("=");
            toReturn.put(splitKeyValuePair[0].trim(), Integer.parseInt(splitKeyValuePair[1].trim()));
        }

        return toReturn;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Running main in locations");

        try (
                FileWriter locFile = new FileWriter("locations.txt");
                FileWriter dirFile = new FileWriter("directions.txt")
        ) {
                for(Location location : locations.values()) {
                    locFile.write(location.getLocationID() + ", " + location.getDescription() + "\n");
                    dirFile.write(location.getLocationID() + ", " + location.getExits() + "\n");
                }
        }
    }

    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        return locations.get(key);
    }

    @Override
    public Location put(Integer key, Location value) {
        return locations.put(key, value);
    }

    @Override
    public Location remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> m) {

    }

    @Override
    public void clear() {
        locations.clear();

    }

    @Override
    public Set<Integer> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {
        return locations.entrySet();
    }
}
