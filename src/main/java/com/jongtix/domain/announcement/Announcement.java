package com.jongtix.domain.announcement;

import com.jongtix.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //기본 생성자가 없으면 JPA에서 Entity 클래스를 만들지 못해 에러가 발생함
                                                    //하지만 기본 생성자를 아무 곳에서나 가져다 쓸 수 있기 때문에 AccessLevel.PROTECTED 설정을 추가해야 함
@Entity
public class Announcement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String writer;

    @Builder
    public Announcement(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
