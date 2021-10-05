package com.timbuchalka;


import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by timbuchalka on 2/04/2016.
 */
public class Locations implements Map<Integer, Location>{
    private static final LocationsToUse locationToUse = LocationsToUse.LOCATIONS_SERIALIZED;
    private static final Map<Integer, Location> locations = new HashMap<Integer, Location>();
    private static final Map<Integer, Location> locationsBinary = new HashMap<Integer, Location>();
    private static final Map<Integer, Location> locationsSerialized = new HashMap<Integer, Location>();
    private static final Map<Integer, IndexRecord> index = new LinkedHashMap<>();
    private static RandomAccessFile randomAccessFile;

    public enum LocationsToUse {
        LOCATIONS,
        LOCATIONS_BINARY,
        LOCATIONS_SERIALIZED,
    }

    static {
        System.out.println("Running static in locations");

        //region Byte Stream Reading
        try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream("locations_binary.dat")))) {
            boolean eof = false;
            while (!eof) {
                try {
                    Map<String, Integer> exits = new LinkedHashMap<>();
                    int locId = dataInputStream.readInt();
                    String description = dataInputStream.readUTF();
                    int numExits = dataInputStream.readInt();
                    System.out.println("Read location " + locId + ": " + description);
                    System.out.println("Found " + numExits + " exits");
//                exits.put("Q", 0);
                    for (int i = 0; i < numExits; i++) {
                        String direction = dataInputStream.readUTF();
                        int destination = dataInputStream.readInt();
                        exits.put(direction, destination);
                        System.out.println("\t\t" + direction + "," + destination);
                    }

                    locationsBinary.put(locId, new Location(locId, description, exits));
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //endregion

        //region Character Stream Reading
        Map<Integer, Map<String,Integer>> tempExitsMap = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("directions.txt"))) {
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                String[] split = nextLine.split(",", 2);
                tempExitsMap.put(Integer.parseInt(split[0]), createMapFromExits(split[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("locations.txt"))) {
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null)  {
                String[] splitLine = nextLine.split(",");
                Integer location = Integer.parseInt(splitLine[0]);
                String description = splitLine[1];
                locations.put(location, new Location(location, description, tempExitsMap.get(location)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // endregion

        //region De-serializing Data
        try (ObjectInputStream locFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream("locations-serialized.dat")))) {
            boolean eof = false;
            while (!eof) {
                try {
                    Location location = (Location) locFile.readObject();
                    locationsSerialized.put(location.getLocationID(), location);
                } catch (EOFException e) {
                    eof = true;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //endregion

        //region Random File Access Index generation based on file layout in main
        //this approach assumes that the locations are not all just loaded into memory and accessed upon player input
        try {
            randomAccessFile = new RandomAccessFile("locations_rand.dat", "rwd");
            int numOfLocations = randomAccessFile.readInt();
            int offsetOfFirstLocation = randomAccessFile.readInt();

            while (randomAccessFile.getFilePointer() < offsetOfFirstLocation) {
                int locationId = randomAccessFile.readInt();
                int locationStart = randomAccessFile.readInt();
                int locationLength = randomAccessFile.readInt();

                IndexRecord indexRecord = new IndexRecord(locationStart, locationLength);
                index.put(locationId, indexRecord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //endregion
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

    public static void main(String[] args) throws IOException {
        System.out.println("Running main in locations");

        //region Write Binary Data
        try (
                DataOutputStream locFile = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("locations_binary.dat")))
                ) {
            for (Location location : locations.values()) {
                locFile.writeInt(location.getLocationID());
                locFile.writeUTF(location.getDescription());
                locFile.writeInt(location.getExits().size() - 1);
                for (String direction : location.getExits().keySet()) {
                    if (direction.equalsIgnoreCase("Q")) continue;
                    System.out.println("\t\t" + direction + "," + location.getExits().get(direction));
                    locFile.writeUTF(direction);
                    locFile.writeInt(location.getExits().get(direction));
                }
            }
        }
        //endregion

        //region Write Non-binary data
//        try (
//                BufferedWriter locFile = new BufferedWriter(new FileWriter("locations.txt"));
//                BufferedWriter dirFile = new BufferedWriter(new FileWriter("directions.txt"))
//        ) {
//            for (Location location : locations.values()) {
//                locFile.write(location.getLocationID() + ", " + location.getDescription() + "\n");
//                dirFile.write(location.getLocationID() + ", " + location.getExits() + "\n");
//            }
//        }
        //endregion

        //region Write Serializable Object
        try (ObjectOutputStream locFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("locations-serialized.dat")))) {
            for (Location location : locationsSerialized.values()) {
                locFile.writeObject(location);
            }
        }

        //endregion

        //region Create random Access File
        //Random Access File Layout
        //bytes 0-3 = number of locations
        //bytes 4-7 = start offset of the first location record (used in index buidling while loop)
        //bytes 8-1699 = index (which bytes correspond to which locations' start offset)
        //bytes 1700+ = location records

        //"rwd" is mode that has RandomAccessFile handle file synchronization (good practice)
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("locations_rand.dat", "rwd")) {

            //writing number of locations as int (4 bytes)
            randomAccessFile.writeInt(locations.size());

            //each index record contains three ints corresponding to
            //location ID
            //location offset
            //size of location record
            int indexSize = locations.size() * 3 * Integer.BYTES;
            int locationStart = (int) (indexSize + randomAccessFile.getFilePointer() + Integer.BYTES);
            randomAccessFile.writeInt(locationStart);

            long indexStart = randomAccessFile.getFilePointer();

            //build index in memory to minimize read/write operations
            randomAccessFile.seek(locationStart);

            for (Location location : locations.values()) {
                randomAccessFile.writeInt(location.getLocationID());
                randomAccessFile.writeUTF(location.getDescription());
                StringBuilder builder = new StringBuilder();

                for (String direction : location.getExits().keySet()) {
                    if (!direction.equalsIgnoreCase("Q")) {
                        builder.append(direction);
                        builder.append(",");
                        builder.append(location.getExits().get(direction));
                        builder.append(",");
                    }
                }
                randomAccessFile.writeUTF(builder.toString());

                int currentPointLocation = (int) randomAccessFile.getFilePointer();
                IndexRecord record = new IndexRecord(locationStart, currentPointLocation - locationStart);
                index.put(location.getLocationID(), record);

                locationStart = (int) randomAccessFile.getFilePointer();
            }

            //writing the index all in one go
            randomAccessFile.seek(indexStart);
            for (Integer locationID : index.keySet()) {
                randomAccessFile.writeInt(locationID);
                randomAccessFile.writeInt(index.get(locationID).getStartByte());
                randomAccessFile.writeInt(index.get(locationID).getLength());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //endregion
    }

    public Location getLocation(int idToGet) throws IOException {
        if (idToGet < 0) throw new IOException("Invalid idToGet in getLocation");

        IndexRecord record = index.get(idToGet);
        randomAccessFile.seek(record.getStartByte());

        //is same as idToGet (could have seeked one extra byte)
        int locationId = randomAccessFile.readInt();
        String description = randomAccessFile.readUTF();
        String exitsString = randomAccessFile.readUTF();
        String[] exitParts = new String(exitsString).split(",");
        Map<String, Integer> exits = new LinkedHashMap<>();

        String exitKey = null;
        int exitValue = -1;

        assert exitParts.length % 2 == 0;
        for (int i = 0; i < exitParts.length; i++) {
            String exitPart = exitParts[i];
            if (i % 2 == 0) {
                exitKey = exitPart;
            } else {
                exitValue = Integer.parseInt(exitPart);
            }

            if (exitKey != null && exitValue != -1) {
                exits.put(exitKey, exitValue);
                exitKey = null;
                exitValue = -1;
            }
        }
        return new Location(locationId, description, exits);
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
        if (locationToUse.equals(LocationsToUse.LOCATIONS_BINARY)) return locationsBinary.get(key);
        else if (locationToUse.equals(LocationsToUse.LOCATIONS_SERIALIZED)) return locationsSerialized.get(key);
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

    @Override
    public String toString() {
        for (Location location : locations.values()) {
            System.out.println(location.toString());
        }
        return "";
    }

    public void close() throws IOException {
        randomAccessFile.close();
    }

    private static Map<String, Integer> createMapFromExits(@NotNull String mapAsString) {
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

}
