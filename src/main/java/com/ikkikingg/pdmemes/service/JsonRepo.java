package com.ikkikingg.pdmemes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikkikingg.pdmemes.model.Page;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;

@Slf4j
public class JsonRepo {

    public static void saveActualPage(ObjectMapper mapper,
                                       Page page) {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            File file = Paths.get(classloader.getResource("page.json").toURI()).toFile();
            PrintWriter writer = new PrintWriter(file);
            writer.print(mapper.writeValueAsString(page));
            writer.close();
            log.info("should be saved to json file");
        } catch (Exception ex) {
            log.error("Error while reading json page file: " + ex.getMessage());
        }
    }
}