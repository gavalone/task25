package com.example.task25;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Entity
public class Conference {
    private Long ID;
    @Getter
    private String ConferenceName;
    @Getter
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date MeetingDate;
    @Getter
    private String Moderator;
    @Getter
    private String Speaker;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getID() {
        return ID;
    }
}


