package com.dmiit3iy.reminder.DTO;

import com.dmiit3iy.reminder.model.Reminder;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MapperPageReminderDTO {
   PageReminderDTO mapToDTO(Page<Reminder> reminderPage);
}
