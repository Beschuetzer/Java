package major.adam;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
//            FileInputStream fileInputStream = new FileInputStream("data.txt");
//            FileChannel channel = fileInputStream.getChannel();
            Path dataPath = FileSystems.getDefault().getPath("data.txt");
//            Files.write(dataPath, "\nLine 4".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            List<String> lines = Files.readAllLines(dataPath, StandardCharsets.US_ASCII);
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream("data_binary.dat")) {
            //need to have a channel and buffer
            FileChannel fileChannel = fileOutputStream.getChannel();
            byte[] stringToWriteInBytes = "This is string to output.".getBytes(StandardCharsets.UTF_8);

            //if you have a byte array, you can use the .wrap convenience method
            //to set the buffer length and reset the position to 0
            ByteBuffer byteBuffer = ByteBuffer.wrap(stringToWriteInBytes);
            fileChannel.write(byteBuffer);

            //when writing numeric value though, you have to use .putInt() and .allocate()
            //.putInt() doesn't reset the head like .wrap(), so you have to reset the head
            //using .flip() before writing
            int[] intsToWrite = {42, -44, 540};
            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
            for (int intToWrite : intsToWrite) {
                intBuffer.putInt(intToWrite);
                intBuffer.flip();
                fileChannel.write(intBuffer);
                intBuffer.flip();
            }

//            //Reading using java.io's RandomAccessFile
//            RandomAccessFile randomAccessFile = new RandomAccessFile("data_binary.dat", "rwd");
//
//            //have to know the contents of the file when dealing with strings
//            //in order to only read the correct number of stringToWriteInBytes for each string
//            byte[] bytesRead = new byte[stringToWriteInBytes.length];
//            randomAccessFile.read(bytesRead);
//            System.out.println("\nRead Start--------\n" + new String(bytesRead));
//
//            long int1 = randomAccessFile.readInt();
//            long int2 = randomAccessFile.readInt();
//            System.out.println(int1);
//            System.out.println(int2);

            //Reading using java.nio
            RandomAccessFile randomAccessFile = new RandomAccessFile("data_binary.dat", "rwd");
            FileChannel channel = randomAccessFile.getChannel();

            stringToWriteInBytes[0] = 'a';
            stringToWriteInBytes[1] = 'b';
            byteBuffer.flip();

            long numBytesRead = channel.read(byteBuffer);
            if (byteBuffer.hasArray()) {
                System.out.println("byte buffer: " + new String(byteBuffer.array()));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
