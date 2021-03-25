package com.jongtix.service;

import com.jongtix.domain.announcement.Announcement;
import com.jongtix.domain.announcement.AnnouncementRepository;
import com.jongtix.dto.request.AnnouncementSaveRequestDto;
import com.jongtix.dto.request.AnnouncementUpdateRequestDto;
import com.jongtix.dto.request.PageRequestDto;
import com.jongtix.dto.response.AnnouncementResponseDto;
import com.jongtix.dto.response.PageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
@Log4j2
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public PageResponseDto<AnnouncementResponseDto, Announcement> getList(PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.getPageable(Sort.by("id").descending());
        Page<Announcement> result = announcementRepository.findAll(pageable);

        Function<Announcement, AnnouncementResponseDto> function = (AnnouncementResponseDto::new);

        return new PageResponseDto<>(result, function);
    }

    @Transactional
    public AnnouncementResponseDto save(AnnouncementSaveRequestDto announcementSaveRequestDto) {
        return new AnnouncementResponseDto(announcementRepository.save(announcementSaveRequestDto.toEntity()));
    }

    @Transactional
    public AnnouncementResponseDto update(Long id, AnnouncementUpdateRequestDto announcementUpdateRequestDto) {
        Announcement announcement = announcementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 공지가 없습니다. id: " + id));
        announcement.update(announcementUpdateRequestDto.getTitle(), announcementUpdateRequestDto.getContent());

        return new AnnouncementResponseDto(announcement);
    }

    public Announcement findById(Long id) {
        return announcementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 공지가 없습니다. id: " + id));
    }

    @Transactional
    public Long delete(Long id) {
        announcementRepository.deleteById(id);
        return id;
    }

}
