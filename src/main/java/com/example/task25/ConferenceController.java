package com.example.task25;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;
import java.util.Collections;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param; //привязка пераметров
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Comparator;

@Controller
public class ConferenceController {

    @Autowired
    private ConferenceService service;

    @RequestMapping("/")
    public String index(Model model, @Param("keyword") String keyword) {
        List<Conference> confList = service.ListAll(keyword);

        Map<Date, Integer> dateMap = new HashMap<>();

        for (Conference conf : confList) {
            Date dateMeet = conf.getMeetingDate();
            dateMap.put(dateMeet, dateMap.getOrDefault(dateMeet, 0) + 1);
        }

        List<List<Object>> dateCountMap = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Map.Entry<Date, Integer> entry : dateMap.entrySet()) {
            List<Object> subList = new ArrayList<>();
            subList.add(sdf.format(entry.getKey()));
            subList.add(entry.getValue());
            dateCountMap.add(subList);
        }

        Collections.sort(dateCountMap, new Comparator<List<Object>>() {
            public int compare(List<Object> o1, List<Object> o2) {
                try {
                    Date date1 = sdf.parse((String) o1.get(0));
                    Date date2 = sdf.parse((String) o2.get(0));
                    return date1.compareTo(date2);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        model.addAttribute("chartData", dateCountMap);
        model.addAttribute("confList", confList);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    @RequestMapping("/new")
    public String newConference(Model model) {
        Conference conf = new Conference();
        model.addAttribute("conf", conf);
        return "new";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveConference(@ModelAttribute("conf") Conference conf) {
        service.save(conf);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editConference(@PathVariable(name="id") Long id) {
        ModelAndView mav = new ModelAndView("edit");
        Conference conf = service.get(id);
        mav.addObject("conf", conf);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteConference(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }

    @RequestMapping("sort")
    public String sortHomePage(Model model, @Param("keyword") String keyword) {
        List<Conference> confList = service.ListAll(keyword);
        confList.sort(Comparator.comparing(Conference::getMeetingDate));

        Map<Date, Integer> dateMap = new HashMap<>();

        for (Conference conf : confList) {
            Date dateMeet = conf.getMeetingDate();
            dateMap.put(dateMeet, dateMap.getOrDefault(dateMeet, 0) + 1);
        }

        List<List<Object>> dateCountMap = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Map.Entry<Date, Integer> entry : dateMap.entrySet()) {
            List<Object> subList = new ArrayList<>();
            subList.add(sdf.format(entry.getKey()));
            subList.add(entry.getValue());
            dateCountMap.add(subList);
        }

        Collections.sort(dateCountMap, new Comparator<List<Object>>() {
            public int compare(List<Object> o1, List<Object> o2) {
                try {
                    Date date1 = sdf.parse((String) o1.get(0));
                    Date date2 = sdf.parse((String) o2.get(0));
                    return date1.compareTo(date2);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        model.addAttribute("chartData", dateCountMap);
        model.addAttribute("confList", confList);
        model.addAttribute("keyword", keyword);
        return "index";
    }
}
