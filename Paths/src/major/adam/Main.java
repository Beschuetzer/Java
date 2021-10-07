package major.adam;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {
    private static final String wDirFileName = "workingDirectoryFile.txt";
    private static final String subDirFileName = "subDirectoryFile.txt";
    private static final String outThereFileName = "outThere.txt";

    public static void main(String[] args) {
        //Use absolute paths when the user creates the files otherwise stick to FileSystems.(...) relative paths
        Path wDirFile = FileSystems.getDefault().getPath(wDirFileName);
        Path subDirFile = FileSystems.getDefault().getPath("files/" + subDirFileName);
        Path subDirFile2 = FileSystems.getDefault().getPath("files", subDirFileName);
        Path outThereFile3 = FileSystems.getDefault().getPath("../", "outThere.txt");
        Path wDirFile2 = Paths.get("./" + wDirFile);
        Path outThereFile = Paths.get("/home/adam/github/WebDevelopmentStuff/Java/" + outThereFileName);
        Path outThereFile2 = Paths.get("../outThere.txt");

        Path workingDir = Paths.get(".");

        printFile(wDirFile);
        printFile(wDirFile2);

        printFile(subDirFile);
        printFile(outThereFile);
        printFile(subDirFile2);
        printFile(outThereFile2);
        printFile(outThereFile3);

        System.out.println(workingDir.toAbsolutePath());

        Path pathNormalized = FileSystems.getDefault().getPath(".", "files", "..", "files", subDirFileName);
        //normalize removes ".." and "."
        //NORMALIZE THE PATH BEFORE USING
        System.out.println("No normalization: " + pathNormalized.toAbsolutePath());
        System.out.println("Normalized: " + pathNormalized.normalize().toAbsolutePath());

        Path nonExistantPath = FileSystems.getDefault().getPath("doesnotexist.txt");
        System.out.println(nonExistantPath.toAbsolutePath());

        Path checkFilePath = FileSystems.getDefault().getPath("files");
        if (Files.exists(checkFilePath)) {
            System.out.println("Exists: " + checkFilePath.toAbsolutePath());
        }

        if (Files.isExecutable(checkFilePath)) {
            System.out.println("Is Executable: " + checkFilePath.toAbsolutePath());
        }

        if (Files.isWritable(checkFilePath)) {
            System.out.println("Is writable: " + checkFilePath.toAbsolutePath());
        }

        if (Files.isReadable(checkFilePath)) {
            System.out.println("Is Readable: " + checkFilePath.toAbsolutePath());
        }

        boolean isReadable = Files.isReadable(checkFilePath);
        boolean isWritable = Files.isWritable(checkFilePath);
        boolean isExecutable = Files.isExecutable(checkFilePath);

        copyDemo();

    }

    private static void printFile(Path path) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            System.out.println();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyDemo() {
        //copying a file
        try {
            Path sourceFile = FileSystems.getDefault().getPath("Examples", "file1.txt");
            Path destFile = FileSystems.getDefault().getPath("Examples", "file1-copy.txt");
            Files.copy(sourceFile, destFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Path fileToMove = FileSystems.getDefault().getPath("Examples", "file1-copy.txt");
            Path dest = FileSystems.getDefault().getPath("Examples", "Dir1", "file1-copy.txt");
            Files.move(fileToMove, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Path toDelete = FileSystems.getDefault().getPath("Examples/Dir1", "file1-copy.txt");
            Files.deleteIfExists(toDelete);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Path dirToCreate = FileSystems.getDefault().getPath("Examples", "Dir2", "Dir3", "Dir4", "Dir5", "Dir6");
            Files.createDirectories(dirToCreate);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Path fileToGetSizeFor = FileSystems.getDefault().getPath("Examples", "Dir1", "file1.txt");
            System.out.println(Files.size(fileToGetSizeFor));
            System.out.println(Files.getLastModifiedTime(fileToGetSizeFor));
            System.out.println(Files.getPosixFilePermissions(fileToGetSizeFor));
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Getting all attributes in one shot
        try {
            Path fileToGetAllAttributesFor = FileSystems.getDefault().getPath("Examples", "Dir1", "file2.txt");
            BasicFileAttributes attr = Files.readAttributes(fileToGetAllAttributesFor, BasicFileAttributes.class);
            System.out.println("size = " + attr.size());
            System.out.println("lastModifiedTime() = " + attr.lastModifiedTime());
            System.out.println("creationTime() = " + attr.creationTime());
            System.out.println("isDirectory() = " + attr.isDirectory());
            System.out.println("isRegularFile()() = " + attr.isRegularFile());
            System.out.println("isSymbolicLink()() = " + attr.isSymbolicLink());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
