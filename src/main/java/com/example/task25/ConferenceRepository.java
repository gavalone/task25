package com.example.task25;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    @Query("SELECT p FROM Conference p WHERE CONCAT(p.conferenceName, '', p.meetingDate, '', p.moderator, '', p.speaker) LIKE %?1%")
    List<Conference> searchConference(String keyword);
}
