package major.adam;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

            //if you have a byte array, you can use the .wrap() convenience method
            //to set the buffer length, put the byte array into buffer and reset the buffer position to 0
            //USING .WRAP() MEANS THAT THE BYTE[] YOU PUT IN IS BACKING THE BUFFER (ANY CHANGES TO BYTE[])
            //ARE REFLECTED IN THE BUFFER
            ByteBuffer byteBufferWrapped = ByteBuffer.wrap(stringToWriteInBytes);

            //the alternative to using .wrap()
            ByteBuffer byteBufferNotWrapped = ByteBuffer.allocate(stringToWriteInBytes.length);
            byteBufferNotWrapped.put(stringToWriteInBytes);
            byteBufferNotWrapped.flip();


            //writing buffer to channel (remember to .flip() before)
            fileChannel.write(byteBufferWrapped);

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


            //writing all in one go
            ByteBuffer intBufferOneGo = ByteBuffer.allocate(Integer.BYTES * 3);
            for (int intToWrite : intsToWrite) {
                intBufferOneGo.putInt(intToWrite);
                intBufferOneGo.flip();
            }
            fileChannel.write(intBufferOneGo);


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

            //Relative Reading using java.nio
            RandomAccessFile randomAccessFile = new RandomAccessFile("data_binary.dat", "rwd");
            FileChannel channel = randomAccessFile.getChannel();

            stringToWriteInBytes[0] = 'a';
            stringToWriteInBytes[1] = 'b';
            byteBufferWrapped.flip();

            long numBytesRead = channel.read(byteBufferWrapped);
            if (byteBufferWrapped.hasArray()) {
                System.out.println("byte buffer: " + new String(byteBufferWrapped.array()));
            }

            //Absolute read using .getInt(index) (reads at index)
            //absolute vs relative applies to all types as well as
            //the put equivalent method (e.g. .putLong(0))
            //absolute reads don't change buffer's position unlike relative reads
            //basically stick with one approach and be consistent
            intBuffer.flip();
            numBytesRead = channel.read(intBuffer);
//            System.out.println(intBuffer.getInt(0));


            try (
                    RandomAccessFile copyFile = new RandomAccessFile("data-copy.dat", "rw");
                    FileChannel copyChannel = copyFile.getChannel()
            ) {
                long numTransferredTo = channel.transferTo(0, channel.size(), copyChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }
            channel.close();

            pipeDemo();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void pipeDemo() {
        try {
            Pipe pipe = Pipe.open();

            //Writer thread
            Runnable writer = () -> {
                try {
                    Pipe.SinkChannel sinkChannel = pipe.sink();
                    ByteBuffer buffer = ByteBuffer.allocate(56);

                    for (int i = 0; i < 10; i++) {
                        String currentTime = "The time is " + System.currentTimeMillis();
                        buffer.put(currentTime.getBytes(StandardCharsets.UTF_8));
                        buffer.flip();

                        while (buffer.hasRemaining()) {
                            sinkChannel.write(buffer);
                        }

                        buffer.flip();
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            Runnable reader = () -> {
              try {
                  Pipe.SourceChannel sourceChannel = pipe.source();
                  ByteBuffer buffer = ByteBuffer.allocate(56);

                  for (int i = 0; i < 10; i++) {
                      int bytesRad = sourceChannel.read(buffer);
                      byte[] timeString = new byte[bytesRad];
                      buffer.flip();
                      buffer.get(timeString);
                      System.out.println("Reader thread: " + new String(timeString));
                      buffer.flip();
                      Thread.sleep(100);
                  }
              } catch (Exception e) {
                  e.printStackTrace();
              }
            };

            new Thread(writer).start();
            new Thread(reader).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //relative
        //returns a working directory set to the current user's directory or when run in an IDE, the project directory (as that is the IDE's working directory)
        Path relativePath = FileSystems.getDefault().getPath("file.txt");

        //absolute
        Path absolutePath = Paths.get("c:\\somePath\\to\\file.txt");
    }
}
