package com.dmiit3iy.reminder.DTO;

import com.dmiit3iy.reminder.model.Reminder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageReminderDTO {
    private List<ReminderDTO> reminderList;
    private boolean isLastPage;

    public PageReminderDTO(Page<ReminderDTO> reminderPage) {
        this.reminderList = reminderPage.getContent();
        this.isLastPage = reminderPage.isLast();
    }
}
