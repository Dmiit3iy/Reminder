package com.dmiit3iy.reminder.DTO;

import com.dmiit3iy.reminder.model.Reminder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReminderDTO {
    private String title;

    private String description;

    public ReminderDTO(Reminder reminder) {
        this.title = reminder.getTitle();
        this.description = reminder.getDescription();
    }
}
