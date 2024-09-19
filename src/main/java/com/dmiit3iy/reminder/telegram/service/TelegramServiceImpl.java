package com.dmiit3iy.reminder.telegram.service;

import com.dmiit3iy.reminder.service.Sender;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class TelegramServiceImpl implements TelegramService, Sender {
    @Value("${bot.token}")
    private String token;

    @Override
    public void sendMessage(long chatId, String message, ParseMode parseMode) {
        TelegramBot bot = new TelegramBot(token);
        bot.execute(new SendMessage(chatId, message).parseMode(parseMode),
                new Callback<SendMessage, SendResponse>() {
                    @Override
                    public void onResponse(SendMessage sendMessage,
                                           SendResponse sendResponse) {
                        int messageId = sendResponse.message().messageId();
                        System.out.println(messageId);
                    }

                    @Override
                    public void onFailure(SendMessage sendMessage, IOException e) {
                        System.out.println(sendMessage);
                        e.printStackTrace();
                    }
                });
        }

    @Override
    public void sendMessage(String to, String text) {
        long chatId = Long.parseLong(to); // Преобразуем строку в chatId
        TelegramBot bot = new TelegramBot(token);
        bot.execute(new SendMessage(chatId, text),
                new Callback<SendMessage, SendResponse>() {
                    @Override
                    public void onResponse(SendMessage sendMessage,
                                           SendResponse sendResponse) {
                        int messageId = sendResponse.message().messageId();
                        System.out.println("Telegram сообщение отправлено: " + messageId);
                    }

                    @Override
                    public void onFailure(SendMessage sendMessage, IOException e) {
                        System.out.println("Ошибка отправки сообщения: " + sendMessage);
                        e.printStackTrace();
                    }
                });
    }
}
