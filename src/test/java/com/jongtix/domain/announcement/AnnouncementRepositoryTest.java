package com.jongtix.domain.announcement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AnnouncementRepositoryTest {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @AfterEach
    void tearDown() {
        announcementRepository.deleteAll();
    }


    @DisplayName("공지사항_ID_조회_테스트")
    @Test
    void announcement_find_by_id() {
        //given
        String title = "title";
        String content = "content";
        String writer = "writer";

        //when
        Long id = announcementRepository.save(
                Announcement.builder()
                        .title(title)
                        .content(content)
                        .writer(writer)
                        .build()
        ).getId();

        //then
        Announcement announcement = announcementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 공지가 없습니다. id: " + id));
        assertThat(announcement.getTitle()).isEqualTo(title);
        assertThat(announcement.getContent()).isEqualTo(content);
        assertThat(announcement.getWriter()).isEqualTo(writer);
        assertThat(announcement.getCreatedDate()).isBefore(LocalDateTime.now());
        assertThat(announcement.getModifiedDate()).isBefore(LocalDateTime.now());

    }

    @DisplayName("공지사항_저장_테스트")
    @Test
    void announcement_save() {
        //given
        String title = "title";
        String content = "content";
        String writer = "writer";

        //when
        Long id = announcementRepository.save(
                Announcement.builder()
                        .title(title)
                        .content(content)
                        .writer(writer)
                        .build()
        ).getId();

        //then
        Announcement announcement = announcementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 공지가 없습니다. id: " + id));
        assertThat(announcement.getTitle()).isEqualTo(title);
        assertThat(announcement.getContent()).isEqualTo(content);
        assertThat(announcement.getWriter()).isEqualTo(writer);
        assertThat(announcement.getCreatedDate()).isBefore(LocalDateTime.now());
        assertThat(announcement.getModifiedDate()).isBefore(LocalDateTime.now());
    }

}