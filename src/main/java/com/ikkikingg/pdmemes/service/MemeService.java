package com.ikkikingg.pdmemes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikkikingg.pdmemes.config.SpringConfig;
import com.ikkikingg.pdmemes.model.Page;
import com.ikkikingg.pdmemes.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MemeService extends TelegramLongPollingBot {
    private Set<Post> postSet = new TreeSet<>();

    private final SpringConfig config;
    private final Page page;
    private final ObjectMapper mapper;
    private final ParserService parserService;

    @Autowired
    public MemeService(SpringConfig config, Page page, ObjectMapper mapper, ParserService parserService) {
        this.config = config;
        this.page = page;
        this.mapper = mapper;
        this.parserService = parserService;
    }

    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        String messageText;
        String chatId;

        if (update.getMessage() != null) {
            chatId = update.getMessage().getChatId().toString();
            messageText = update.getMessage().getText();
        } else {
            chatId = update.getChannelPost().getChatId().toString();
            messageText = update.getChannelPost().getText();
        }

        if (messageText.contains("/memes")) {
            log.info("Added new chatId = " + chatId);
            page.getChatIds().add(chatId);
            sendPosts(chatId);
        }

        if (messageText.contains("/go")) {
            parse();
        }
    }


    public void send(String chatId, String text) {
        SendMessage.SendMessageBuilder messageBuilder = SendMessage.builder();
        messageBuilder.chatId(chatId);
        messageBuilder.text(text);
        try {
            execute(messageBuilder.build());
        } catch (TelegramApiException e) {
            log.error("Error of sending message with link: " + text
                    + " to chatId: " + chatId);
        }
    }

    @Scheduled(fixedDelayString = "${parser.scheduler.frequency}", timeUnit = TimeUnit.MINUTES)
    public void parse() {
        try {
            postSet.clear();
            postSet = parserService.parsePage();
            page.getChatIds().forEach(chatId -> {
                sendPosts(chatId);
            });
            Runnable runnable = () -> JsonRepo.saveActualPage(mapper, page);
            new Thread(runnable).start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public String getBotUsername() {
        return config.getBotUserName();
    }

    public String getBotToken() {
        return config.getToken();
    }

    private void sendPosts(String chatId) {
        postSet.forEach(post -> {
            post.getLinks().forEach(link -> {
                send(chatId, "пост № " + post.getNumPost() + "\n" + link);
            });
        });
    }

}