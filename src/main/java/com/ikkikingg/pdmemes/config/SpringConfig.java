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
            page = getObjectMapper().readValue(new File("/home/ikkikingg/PD_MemesBot/src/main/resources/page.json"), Page.class);
        } catch (IOException e) {
            log.error("Error while reading json page file: " + e.getMessage());
            e.printStackTrace();
        }
        return page;
    }
}