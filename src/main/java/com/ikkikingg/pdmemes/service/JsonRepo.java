package com.ikkikingg.pdmemes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikkikingg.pdmemes.model.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.PrintWriter;


@Slf4j
@Repository
public class JsonRepo {

    public void saveActualPage(ObjectMapper mapper,
                                      Page page) {
        try {

            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            File file = new File(getClass().getClassLoader().getResource("page.json").toURI());
            PrintWriter writer = new PrintWriter(file);
            writer.print(mapper.writeValueAsString(page));
            writer.close();
            log.info("should be saved to json file");
        } catch (Exception e) {
            log.error("Error while saving json page file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
