package com.dmiit3iy.reminder.DTO;

import com.dmiit3iy.reminder.model.Reminder;
import org.springframework.data.domain.Page;

public class MapperPageReminderDTOImp implements MapperPageReminderDTO {
    @Override
    public PageReminderDTO mapToDTO(Page<Reminder> reminderPage) {
        Page<ReminderDTO> reminderDTOPage = reminderPage.map(ReminderDTO::new);
        return new PageReminderDTO(reminderDTOPage);
    }
}
