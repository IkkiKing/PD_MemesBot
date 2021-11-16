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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        try {
            page = getObjectMapper().readValue(readFileFromResources("page.json"), Page.class);
        } catch (Exception e) {
            log.error("Error while reading json page file: " + e.getMessage());
        }
        return page;
    }

    public static String readFileFromResources(String filename) throws URISyntaxException, IOException {
        URL resource = ClassLoader.getSystemResource(filename);
        byte[] bytes = Files.readAllBytes(Paths.get(resource.toURI()));
        return new String(bytes);
    }
}