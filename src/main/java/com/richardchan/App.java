package com.richardchan;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

import com.richardchan.csv.CSVReader;
import com.richardchan.model.InstrumentTradeQuoteData;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	
    	String targetPath = "target/";
    	String zipPath = targetPath + "scandi.csv.zip";
    	downloadFile("file:src/main/resources/scandi.csv.zip", zipPath);
    	unzipFolder(Paths.get(zipPath),Paths.get(targetPath));
    	
    	Map<String, InstrumentTradeQuoteData> analysis = new HashMap<>(100);
    	
    	CSVReader reader = new CSVReader(new File("target/scandi.csv"));
        while (reader.isIterable()) {
        	String[] columns = reader.next();
        	String id = columns[0];
        	InstrumentTradeQuoteData d = analysis.get(id);
        	if (d == null) {
        		d = new InstrumentTradeQuoteData(id);
        		d.processValues(columns);
        		analysis.put(id, d);
        	} else {
        		d.processValues(columns);
        	}
        }

        for (Map.Entry<String, InstrumentTradeQuoteData> entry: analysis.entrySet()) {
        	System.out.println(entry.getValue().summarize());
        }
        
    }
    
    private static void downloadFile(String url, String targetPath) throws MalformedURLException, IOException {
    	System.out.println("downloading file from: " + url + " ... ");
    	FileUtils.copyURLToFile(new URL(url), new File(targetPath));
    	System.out.println("download complete");
    }
    
    // protect zip slip attack
    private static Path zipSlipProtect(ZipEntry zipEntry, Path targetDir)
        throws IOException {

        // test zip slip vulnerability
        // Path targetDirResolved = targetDir.resolve("../../" + zipEntry.getName());

        Path targetDirResolved = targetDir.resolve(zipEntry.getName());

        // make sure normalized file still has targetDir as its prefix
        // else throws exception
        Path normalizePath = targetDirResolved.normalize();
        if (!normalizePath.startsWith(targetDir)) {
            throw new IOException("Bad zip entry: " + zipEntry.getName());
        }

        return normalizePath;
    }
    
    private static void unzipFolder(Path source, Path target) throws IOException {

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source.toFile()))) {

            // list files in zip
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {

                boolean isDirectory = false;
                // example 1.1
                // some zip stored files and folders separately
                // e.g data/
                //     data/folder/
                //     data/folder/file.txt
                if (zipEntry.getName().endsWith(File.separator)) {
                    isDirectory = true;
                }

                Path newPath = zipSlipProtect(zipEntry, target);

                if (isDirectory) {
                    Files.createDirectories(newPath);
                } else {

                    // example 1.2
                    // some zip stored file path only, need create parent directories
                    // e.g data/folder/file.txt
                    if (newPath.getParent() != null) {
                        if (Files.notExists(newPath.getParent())) {
                            Files.createDirectories(newPath.getParent());
                        }
                    }

                    // copy files, nio
                    Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);

                    // copy files, classic
                    /*try (FileOutputStream fos = new FileOutputStream(newPath.toFile())) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }*/
                }

                zipEntry = zis.getNextEntry();

            }
            zis.closeEntry();

        }

    }

}
