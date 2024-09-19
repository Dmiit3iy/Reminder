package com.dmiit3iy.reminder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
//@AllArgsConstructor
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
    @NonNull
    @Column(unique = true)
    private String email;

    private String telegram;

    @Column(unique = true, name = "chat_id")
    private long chatId;

    @NonNull
    private String password;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reminder> reminders = new ArrayList<>();

    public void addRemind(Reminder reminder) {
        reminders.add(reminder);
        reminder.setUser(this);
    }

    @JsonIgnore
    public List<Reminder> getReminds() {
        return reminders;
    }
}
