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

/*    @Bean
    public Page getActualPage() {
        Page page = new Page();
        try (InputStream inputStream = getClass().getResourceAsStream("/page.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String contents = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            page = getObjectMapper().readValue(contents, Page.class);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Cannot read file from specified path");
        }
        return page;
    }*/

    @Bean
    public Page getActualPage() {
        Page page = new Page();
        try {
            page = getObjectMapper().readValue(new File("src/main/resources/page.json"), Page.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }
}