package com.jongtix.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnnouncementUpdateRequestDto {

    private String title;
    private String content;

    @Builder
    public AnnouncementUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
