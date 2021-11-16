package com.ikkikingg.pdmemes.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikkikingg.pdmemes.model.Page;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Configuration
@Data
@PropertySource("classpath:application.properties")
@Slf4j
@EnableScheduling
public class SpringConfig {

    @Value("${bot.username}")
    String botUserName;

    @Value("${bot.token}")
    String token;

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Page getActualPage() {
        Page page = new Page();
        try (InputStream inputStream = getClass().getResourceAsStream("/page.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String contents = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            page = getObjectMapper().readValue(contents, Page.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

//    public String readFileFromResources(String filename) throws Exception {
//        Class clazz = SpringConfig.class;
//        InputStream inputStream = clazz.getResourceAsStream(filename);
//        return readFromInputStream(inputStream);
//    }

//    private String readFromInputStream(InputStream inputStream) throws Exception {
//        ByteArrayOutputStream result = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        for (int length; (length = inputStream.read(buffer)) != -1; ) {
//            result.write(buffer, 0, length);
//        }
//        // StandardCharsets.UTF_8.name() > JDK 7
//        return result.toString("UTF-8");
//    }
}