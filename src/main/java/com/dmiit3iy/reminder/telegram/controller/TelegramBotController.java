package com.dmiit3iy.reminder.telegram.controller;

import com.dmiit3iy.reminder.service.UserService;
import com.dmiit3iy.reminder.telegram.service.TelegramService;
import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;


@BotController
public class TelegramBotController implements TelegramMvcController {


    TelegramService telegramService;
    UserService userService;
    @Value("${bot.token}")
    private String token;
    private InlineKeyboardMarkup inlineKeyboardMarkup;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTelegramService(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostConstruct
    public void init() {
        this.inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("Хочу на практикум!");
        inlineKeyboardButton.callbackData("Epark");
        this.inlineKeyboardMarkup.addRow(inlineKeyboardButton);

    }

    @Override
    public String getToken() {
        return this.token;
    }

    private SendMessage sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        return sendMessage.parseMode(ParseMode.HTML);

    }


    private SendMessage sendMessageWithButtonsStart(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        //  sendMessage.replyMarkup(replyKeyboardRegistration);
        sendMessage.replyMarkup(inlineKeyboardMarkup);
        return sendMessage.parseMode(ParseMode.HTML);
    }


    /**
     * Callback for /start message
     *
     * @param user from bot
     * @param chat from bot
     * @return message to client
     */
    @BotRequest(value = "/start", type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
    public BaseRequest start(User user, Chat chat) throws InterruptedException {
        long chatId = chat.id();
        String userName = user.username();

        if (userService.get(userName).isEmpty()) {
            return sendMessage(chatId, "Вы не являетесь пользователем Reminder");
        }
        com.dmiit3iy.reminder.model.User user1 = userService.get(userName).get();
        user1.setChatId(chatId);
        userService.update(user1);
        return sendMessage(chatId, "Вы успешно добавлены для получения уведомлений из Reminder");
    }


}



