package com.example.task25;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired; //Для сбора классов-бинсов
import org.springframework.stereotype.Service; //Для обнаружения зависимостей

@Service
public class ConferenceService {
    @Autowired
    private ConferenceRepository repo;

    public List<Conference> ListAll(String keyword) {
        if (keyword != null) {
            return repo.searchConference(keyword);
        }
        return repo.findAll();
    }

    public void save(Conference conf) {
        repo.save(conf);
    }

    public Conference get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
