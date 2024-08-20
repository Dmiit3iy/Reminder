package com.dmiit3iy.reminder.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private String email;
    private String telegram;
    @OneToMany(mappedBy = "user")
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    private List<Reminder> reminders = new ArrayList<>();

    public void addRemind(Reminder reminder) {
        reminders.add(reminder);
    }

    public List<Reminder> getReminds() {
        return reminders;
    }
}
