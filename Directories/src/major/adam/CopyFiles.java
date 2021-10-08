package major.adam;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


//Video 261 Walk File Tree  (about halfway)
public class CopyFiles extends SimpleFileVisitor<Path> {
    private final Path sourceRoot;
    private final Path targetRoot;

    public CopyFiles(Path sourceRoot, Path targetRoot) {
        this.sourceRoot = sourceRoot;
        this.targetRoot = targetRoot;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println(file.toAbsolutePath());

        //when you want to skip siblings of this file
        //return FileVisitResult.SKIP_SIBLINGS;

        //when you've found what you're looking for
        //return FileVisitResult.TERMINATE;

        try {
            Path resolvedPath = sourceRoot.relativize(file);
            Path targetPath = targetRoot.resolve(resolvedPath);
            Files.copy(file, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        //HANDLE COPYING A DIRECTORY HERE AS THE COPIED DIRECTORY HAS TO BE CREATED SO THAT ITS ENTRIES CAN BE COPIED INTO IT

        //this skips the directory in question and prevents postVisitDirectory() from being called
        //return FileVisitResult.SKIP_SIBLINGS;

        //only place to use this as it is equivalent to CONTINUE when used in other methods
        //return FileVisitResult.SKIP_SUBTREE;

        System.out.println("Pre visit path = " + dir.toAbsolutePath());
        Path relativizedPath = sourceRoot.relativize(dir);
        System.out.println("relativizedPath = " + relativizedPath);
        Path resolvedPath = targetRoot.resolve(relativizedPath);
        System.out.println("resolvedPath = " + resolvedPath);

        try {
            if (!Files.exists(resolvedPath)) Files.copy(dir, resolvedPath);
        } catch (IOException e) {
            e.printStackTrace();
            return FileVisitResult.SKIP_SUBTREE;
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("Error accessing file: " + file.toAbsolutePath() + " " + exc.getMessage());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        //HANDLE DELETING A DIRECTORY HERE AS THE DIRECTORY HAS TO BE EMPTY BEFORE DELETING IT

        System.out.println("Post visit path = " + dir.toAbsolutePath());
        return FileVisitResult.CONTINUE;
    }
}
