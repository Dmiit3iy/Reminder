package com.dmiit3iy.reminder.schedulers;

import com.dmiit3iy.reminder.model.Reminder;
import com.dmiit3iy.reminder.service.EmailService;
import com.dmiit3iy.reminder.service.ReminderService;
import com.dmiit3iy.reminder.telegram.service.TelegramService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static com.pengrad.telegrambot.model.request.ParseMode.HTML;

@AllArgsConstructor
@Component
public class MainScheduler {

    private final ReminderService reminderService;
    private final TelegramService telegramService;
    private final EmailService emailService;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);


    @Scheduled(cron = "0 * * * * *", zone = "Europe/Moscow")
    public void sendCompliment() {
        System.out.println("Начинаем рассылку!");
        LinkedBlockingQueue<Reminder> linkedBlockingQueue = new LinkedBlockingQueue<>(reminderService.getToSend());
        while (!linkedBlockingQueue.isEmpty()) {
            try {
                Reminder reminder = linkedBlockingQueue.take();
                threadPool.submit(() -> {
                    try {
                        emailService.sendMessage(reminder.getUser().getEmail(), reminder.getTitle() + "\n" + reminder.getDescription());
                        telegramService.sendMessage(reminder.getUser().getChatId(), reminder.getTitle() + "\n" + reminder.getDescription(), HTML);
                        reminder.setSend(true);
                        reminderService.update(reminder);
                    } catch (Exception e) {
                        System.err.println("Ошибка при отправке напоминания: " + reminder.getTitle() + " id:" + reminder.getId());
                        e.printStackTrace();
                    }
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Ошибка при получении напоминания из очереди");
                throw new RuntimeException(e);
            }
        }
    }

    public void shutdownThreadPool() {
        threadPool.shutdown();
    }

}
