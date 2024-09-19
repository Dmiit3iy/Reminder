package com.dmiit3iy.reminder.telegram.schedulers;

import com.dmiit3iy.reminder.service.ReminderService;
import com.dmiit3iy.reminder.service.UserService;
import com.dmiit3iy.reminder.telegram.service.TelegramService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.pengrad.telegrambot.model.request.ParseMode.HTML;

@AllArgsConstructor
@Component
public class MainScheduler {
    private final UserService userService;
    private final ReminderService reminderService;
    private final TelegramService telegramService;


    @Scheduled(cron = "0 * * * * *", zone = "Europe/Moscow")
    public void sendCompliment() {
        try {
            List<Person> list = personService.get();
            String message = "Привет, коллега! \uD83D\uDC4BНапоминаю, что практикум стартует уже 12.08.2024 в 13.00 по мск. Рекомендую заранее забронировать время в твоём расписании на плодотворную работу в рамках живого вебинара!\n" +
                    "\n" +
                    "Если у тебя не получилось попасть в наш чат практикума, пиши мне в личку @electro_ketty, помогу.";
            for (Person p : list) {
                telegramService.sendMessage(p.getChatId(), message, HTML);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
