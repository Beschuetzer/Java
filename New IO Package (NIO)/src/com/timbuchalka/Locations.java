package com.timbuchalka;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by timbuchalka on 2/04/2016.
 */
public class Locations implements Map<Integer, Location> {
    private static final Map<Integer, Location> locations = new LinkedHashMap<Integer, Location>();

    static {
          //region Reading using java.nio streams
//        Path locPath = FileSystems.getDefault().getPath("locations_big.txt");
//        Path dirPath = FileSystems.getDefault().getPath("directions_big.txt");
//
//        try (Scanner scanner = new Scanner(Files.newBufferedReader(locPath))) {
//            scanner.useDelimiter(",");
//            while (scanner.hasNextLine()) {
//                int loc = scanner.nextInt();
//                scanner.skip(scanner.delimiter());
//                String description = scanner.nextLine();
//                System.out.println("Imported loc: " + loc + ": " + description) ;
//                locations.put(loc, new Location(loc, description, null));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try(BufferedReader dirFile = Files.newBufferedReader(dirPath)) {
//            String input;
//            while ((input = dirFile.readLine()) != null) {
//                String[] data = input.split(",");
//                int loc = Integer.parseInt(data[0]);
//                String direction = data[1];
//                int destination = Integer.parseInt(data[2]);
//                Location location = locations.get(loc);
//                location.addExit(direction, destination);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //endregion

        //region De-serializing with java.nio
        Path locationsPath = FileSystems.getDefault().getPath("locations.dat");
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(locationsPath)))) {
            boolean eof = false;
            while (!eof) {
                try {
                    Location location = (Location) objectInputStream.readObject();
                    locations.put(location.getLocationID(), location);
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (InvalidClassException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //endregion
    }

    // 1. This first four bytes will contain the number of locations (bytes 0-3)
    // 2. The next four bytes will contain the start offset of the locations section (bytes 4-7)
    // 3. The next section of the file will contain the index (the index is 1692 bytes long.  It will start at byte 8 and end at byte 1699
    // 4. The final section of the file will contain the location records (the data). It will start at byte 1700

    public static void main(String[] args) throws IOException {
        //region Writing using ObjectOutputStream (serialization)
        Path locationsPath = FileSystems.getDefault().getPath("locations.dat");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(locationsPath)))) {
            for (Location location : locations.values()) {
                objectOutputStream.writeObject(location);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //endregion

        //region Writing using java.nio buffered streams
//        Path locPath = FileSystems.getDefault().getPath("locations_big.txt");
//        Path dirPath = FileSystems.getDefault().getPath("directions_big.txt");
//        try (
//                BufferedWriter locFile = Files.newBufferedWriter(locPath);
//                BufferedWriter dirFile = Files.newBufferedWriter(dirPath)
//        ) {
//            for (Location location : locations.values()) {
//                locFile.write(location.getLocationID() + "," + location.getLocationID() + "\n");
//                for (String direction : location.getExits().keySet()) {
//                    if (!direction.equalsIgnoreCase("Q")) {
//                        dirFile.write(location.getLocationID() + "," + direction + "," + location.getExits().get(direction) + "\n");
//
//                    }
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("IOException: " + e.getMessage());
//        }
        //endregion
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
