package com.maryor;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.SimpleFileVisitor;
import java.util.Iterator;

public class Main {
    public static void main(String[] args )  {

//        DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
//            @Override
//            public boolean accept(Path path) throws IOException {
//                return (Files.isRegularFile(path));
//            }
//        };

        DirectoryStream.Filter<Path> filter = p -> Files.isRegularFile(p);
        Path directory = FileSystems.getDefault().getPath("FileTree" + File.separator + "Dir2" + File.separator + "Dir3");
        try (DirectoryStream<Path> contents = Files.newDirectoryStream(directory, filter)) {
            for (Path file: contents) {
                System.out.println(file.getFileName());
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.out.println(e.getMessage());
        }

        try {
            Path tempFile = Files.createTempFile("myapp", ".appx");
            System.out.println("Temp file path = " + tempFile.toAbsolutePath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Iterable<FileStore> stores = FileSystems.getDefault().getFileStores();
        for (FileStore store : stores) {
            System.out.println(store);
        }

        Iterable<Path> rootPaths = FileSystems.getDefault().getRootDirectories();
        for (Path path : rootPaths) {
            System.out.println(path);
        }

        System.out.println("-----Walking tree for Dir2");
        Path dir2Path = FileSystems.getDefault().getPath("FileTree\\Dir2");
        try {
            Files.walkFileTree(dir2Path, new PrintNames());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("------Copy Dir2 to Dir4/Dir2Copy----");
        Path copyPath = FileSystems.getDefault().getPath("FileTree\\Dir4\\Dir2Copy");
        try {
            Files.walkFileTree(dir2Path, new CopyFiles(dir2Path, copyPath));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        File file = new File("C:\\Examples\\file.txt");
        Path convertedPath = file.toPath();
        System.out.println("convertedPath = " + convertedPath);

        File parent = new File("C:\\Examples");
        File resolvedFile = new File(parent, "dir\\file.txt");
        System.out.println(resolvedFile.toPath());

        resolvedFile = new File("C:\\Examples", "dir\\file.txt");
        System.out.println(resolvedFile.toPath());

        Path parentPath = Paths.get("C:\\Examples");
        Path childRelativePath = Paths.get("dir\\file.txt");
        System.out.println(parentPath.resolve(childRelativePath));

        File workingDirectory = new File("").getAbsoluteFile();
        System.out.println("Working directory = " + workingDirectory.getAbsolutePath());

        System.out.println("-----Print Dir2 contents using list()-----");
        File dir2File = new File(workingDirectory, "\\FileTree\\Dir2");
        String[] dir2Contents = dir2File.list();
        for (int i=0; i<dir2Contents.length; i++) {
            System.out.println("i= " + i + ": " + dir2Contents[i]);
        }

        System.out.println("----Print Dir2 contents using listFile()------");
        File[] dir2Files = dir2File.listFiles();
        for (int i=0; i<dir2Files.length; i++) {
            System.out.println("i= " + i + ": " + dir2Files[i].getName());
        }

    }
}