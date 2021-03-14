package com.jongtix.controller;

import com.jongtix.dto.request.AnnouncementSaveRequestDto;
import com.jongtix.dto.request.AnnouncementUpdateRequestDto;
import com.jongtix.dto.request.PageRequestDto;
import com.jongtix.dto.response.AnnouncementResponseDto;
import com.jongtix.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Log4j2
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping("/api/announcement")
    public AnnouncementResponseDto save(@RequestBody AnnouncementSaveRequestDto announcementSaveRequestDto) {
        return announcementService.save(announcementSaveRequestDto);
    }

    @PutMapping("/api/announcement/{id}")
    public AnnouncementResponseDto update(@PathVariable Long id, @RequestBody AnnouncementUpdateRequestDto announcementUpdateRequestDto) {
        return announcementService.update(id, announcementUpdateRequestDto);
    }

    @DeleteMapping("/api/announcement/{id}")
    public Long delete(@PathVariable Long id) {
        return announcementService.delete(id);
    }

}
