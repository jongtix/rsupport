package com.jongtix.dto.request;

import com.jongtix.domain.announcement.Announcement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementSaveRequestDto {

    private String title;
    private String content;
    private String writer;

    public Announcement toEntity() {
        return Announcement.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }

}
