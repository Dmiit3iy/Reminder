package com.dmiit3iy.reminder.repository;

import com.dmiit3iy.reminder.model.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    Optional<Reminder> findTopByUserIdOrderByIdAsc(long userID);

    List<Reminder> findByTitleAndUserId(String title, long userID);

    List<Reminder> findByDescriptionAndUserId(String description, long userID);


   // @Query("SELECT * FROM reminder r WHERE user_id = :userId ORDER BY EXTRACT('TIME' from remind)", nativeQuery = true)
   @Query(value = "SELECT * FROM reminder r WHERE r.user_id = :userId  ORDER BY EXTRACT(HOUR FROM remind), " +
           "EXTRACT(MINUTE FROM remind), EXTRACT(SECOND FROM remind) ", nativeQuery = true)
    Page<Reminder> findAllSortedByTime(@Param("userId") long userId, Pageable pageable);

    @Query("SELECT r FROM Reminder r where r.user.id = :userId ORDER BY FUNCTION('DATE', r.remind)")
    Page<Reminder> findAllSortedByDate(@Param("userId") long userId, Pageable pageable);

    @Query("SELECT r FROM Reminder r where r.user.id = :userId ORDER BY r.title")
    Page<Reminder> findAllSortedByTitle(@Param("userId") long userId, Pageable pageable);

    @Query("SELECT r FROM Reminder r WHERE FUNCTION('TIME', r.remind) = :localTime")
    List<Reminder> findByLocalTime(@Param("localTime") LocalTime localTime);

    @Query("SELECT r FROM Reminder r WHERE FUNCTION('DAY', r.remind) = :localDate")
    List<Reminder> findByLocalDate(@Param("localDate") LocalDate localDate);

    List<Reminder> findByRemind(LocalDate remind);


}
