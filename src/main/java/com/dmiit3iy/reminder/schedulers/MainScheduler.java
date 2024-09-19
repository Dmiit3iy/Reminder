package com.dmiit3iy.reminder.schedulers;

import com.dmiit3iy.reminder.model.Reminder;
import com.dmiit3iy.reminder.service.EmailService;
import com.dmiit3iy.reminder.service.ReminderService;
import com.dmiit3iy.reminder.service.UserService;
import com.dmiit3iy.reminder.telegram.service.TelegramService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

import static com.pengrad.telegrambot.model.request.ParseMode.HTML;

@AllArgsConstructor
@Component
public class MainScheduler {
    private final UserService userService;
    private final ReminderService reminderService;
    private final TelegramService telegramService;
    private final EmailService emailService;
    //private LinkedBlockingQueue<Reminder> linkedBlockingQueue = new LinkedBlockingQueue<>();


    @Scheduled(cron = "0 * * * * *", zone = "Europe/Moscow")
    public void sendCompliment() {
        System.out.println("Начинаем рассылку!");
        LinkedBlockingQueue<Reminder> linkedBlockingQueue = new LinkedBlockingQueue<>(reminderService.getToSend());
        while (linkedBlockingQueue.size() > 0) {
            try {
                Reminder x = linkedBlockingQueue.take();
                emailService.sendMessage(x.getUser().getEmail(), x.getTitle() + "\n" + x.getDescription());
                telegramService.sendMessage(x.getUser().getChatId(), x.getTitle() + "\n" + x.getDescription(), HTML);
                x.setSend(true);
                reminderService.update(x);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
