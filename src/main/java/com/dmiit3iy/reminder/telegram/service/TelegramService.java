package com.dmiit3iy.reminder.telegram.service;


import com.pengrad.telegrambot.model.request.ParseMode;
import org.springframework.web.multipart.MultipartFile;

public interface TelegramService {
    void sendMessage(long chatId, String message, ParseMode parseMode);

}
