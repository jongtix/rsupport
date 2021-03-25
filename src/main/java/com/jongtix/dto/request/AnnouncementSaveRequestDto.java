package com.jongtix.dto.request;

import com.jongtix.domain.announcement.Announcement;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
