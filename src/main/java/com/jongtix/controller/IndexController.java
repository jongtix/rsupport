package com.jongtix.controller;

import com.jongtix.dto.request.PageRequestDto;
import com.jongtix.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
@Log4j2
public class IndexController {

    private final AnnouncementService announcementService;

    @GetMapping("/")
    public String index() {
        return "redirect:/announcement/list";
    }

    @GetMapping("/announcement/list")
    public String list(@ModelAttribute("pageRequestDto") PageRequestDto pageRequestDto, Model model) {

        model.addAttribute("announcements", announcementService.getList(pageRequestDto));

        return "announcement-list";
    }

    @GetMapping("/announcement/register")
    public String register(@ModelAttribute("pageRequestDto") PageRequestDto pageRequestDto) {
        return "announcement-register";
    }

    @GetMapping("/announcement/view/{id}")
    public String view(@PathVariable Long id, @ModelAttribute("pageRequestDto") PageRequestDto pageRequestDto, Model model) {
        model.addAttribute("announcement", announcementService.findById(id));
        return "announcement-view";
    }

    @GetMapping("/announcement/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("pageRequestDto") PageRequestDto pageRequestDto, Model model) {
        model.addAttribute("announcement", announcementService.findById(id));
        return "announcement-update";
    }

}
