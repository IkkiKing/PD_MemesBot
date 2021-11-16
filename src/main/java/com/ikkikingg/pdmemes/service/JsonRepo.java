package com.ikkikingg.pdmemes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikkikingg.pdmemes.model.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Slf4j
@Repository
public class JsonRepo {
    private static final String sp = File.separator;

    public static void saveActualPage(ObjectMapper mapper,
                                      Page page) {
        try {

            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            File file = new File("src/main/resources/page.json");
            PrintWriter writer = new PrintWriter(file);
            writer.print(mapper.writeValueAsString(page));
            writer.close();
            log.info("should be saved to json file");
        } catch (Exception ex) {
            log.error("Error while saving json page file: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

/*    public void saveActualPage(ObjectMapper mapper,
                                Page page) {
        String jarPath = "";
        System.out.println("Writing data...");
        try {
            jarPath = URLDecoder.decode(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
            System.out.println(jarPath);

        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String completePath = jarPath.substring(0, jarPath.lastIndexOf("/"))
                + sp + "page.json";

        File f = new File(completePath);
        try {
            if (!f.exists() && !f.createNewFile()) {
                System.out.println("File doesn't exist, and creating file with path: " + completePath + " failed. ");

            } else {
                System.out.println("Input data exists, or file with path " + completePath + " created successfully. ");
                System.out.println("Absolute Path: " + f.getAbsolutePath());
                System.out.println("Path: " + f.getPath());
                PrintWriter writer = new PrintWriter(f);
                writer.print(mapper.writeValueAsString(page));
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
