package major.adam;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        Path directory = FileSystems.getDefault().getPath("FileTree", "Dir2");
        Path directoryWithSeparator = FileSystems.getDefault().getPath("FileTree" + File.separator + "Dir2");
        readDirectoryStream(directory);

        //Getting file separator
        System.out.println(File.separator);
        String separator = FileSystems.getDefault().getSeparator();
        System.out.println(separator);

        //temp files
        try {
            Path tempFile = Files.createTempFile("filePrefix", ".fileSuffix");
            System.out.println("Tempory file path = " + tempFile.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Get the stores
        Iterable<FileStore> stores = FileSystems.getDefault().getFileStores();
        for (FileStore store : stores) {
            System.out.println(store);
            System.out.println(store.getTotalSpace());
        }

        //Gets the root dirs (e.g. "c:\", "d:\" on Win OS)
        Iterable<Path> rootPaths = FileSystems.getDefault().getRootDirectories();
        for (Path path : rootPaths) {
            System.out.println(path);
        }


        walkDirectoryTree(directory);
    }

    private static void readDirectoryStream(Path path) {
        DirectoryStream.Filter<Path> filter = (p) -> {
            return Pattern.matches(".*\\.txt", p.toString());
        };

        try (
                DirectoryStream<Path> contents = Files.newDirectoryStream(path, "*.txt");
        ) {
            for(Path file : contents) {
                System.out.println(file.getFileName());
                if (Files.isDirectory(file)) readDirectoryStream(file);

                //https://docs.oracle.com/javase/8/docs/api/java/nio/file/FileSystem.html#getPathMatcher-java.lang.String-
                //glob is similar regExp geared towards working with paths
                //*.dat = all files ending in .dat
                //*.{dat,txt} = all files ending in .dat or .txt
                //??? = any path containing exactly three characters
                //myFile* = any path starting with 'myFile'
                //b?*.txt = match any file that is at least two chars long, starts with 'b' and ends in .txt
            }
        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }
    }

    private static void walkDirectoryTree(Path path) {
        //relevant methods to implement in FileVisitor interface
        //.preVisitDirectory() = called before entry visited; accepts ref to directory and BasicFileAttributes instance
        //.postVisitDirectory() = called after all entries in the dir and its descendants have been visited; accepts ref to dir and an exception object when necessary
        //.visitFile() = called when item being visited is a file; accepts ref to the file and a BasicFileAttributes instance (put code that operates on file here)
        //.visitFileFailed = called when a file can't be accessed;  the exception thrown is passed to the method;  you can decide what to do with it (throw it, print it, or anything else);  can be called for files and directories

        //JDK has an implementation called SimpleFileVisitor (can extend and override the methods that are required

        System.out.println("Walking tree for path " + path.toAbsolutePath());

        try {
            Files.walkFileTree(path, new ExtendedSimpleFileVisitor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
