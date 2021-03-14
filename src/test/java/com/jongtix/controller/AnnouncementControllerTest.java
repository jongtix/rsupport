package com.jongtix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jongtix.domain.announcement.Announcement;
import com.jongtix.domain.announcement.AnnouncementRepository;
import com.jongtix.dto.request.AnnouncementSaveRequestDto;
import com.jongtix.dto.request.AnnouncementUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.jongtix.utils.ApiDocumentUtils.*;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnnouncementControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AnnouncementRepository announcementRepository;

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("공지사항_저장")
    @Test
    void announcement_save() throws Exception {
        //given
        String title = "title";
        String content = "content";
        String writer = "writer";

        AnnouncementSaveRequestDto announcementSaveRequestDto = AnnouncementSaveRequestDto.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();

        String url = "http://localhost:" + port + "/api/announcement";

        //when
        ResultActions resultActions = mvc.perform(
                post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsString(announcementSaveRequestDto))
        );

        //then
        resultActions.andDo(print())
                .andDo(document("{class-name}/{method-name}"
                        , getDocumentRequest()
                        , getDocumentResponse()
                        , responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("writer").type(JsonFieldType.STRING).description("작성자"),
                                fieldWithPath("createdDate").type(JsonFieldType.STRING).description("최초 작성일"),
                                fieldWithPath("modifiedDate").type(JsonFieldType.STRING).description("최종 수정일")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.writer").value(writer))
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.modifiedDate").exists());
    }

    @DisplayName("공지사항_수정")
    @Test
    void announcement_update() throws Exception {
        //given
        String title = "title";
        String content = "content";
        String writer = "writer";

        Long id = announcementRepository.save(
                Announcement.builder()
                        .title(title)
                        .content(content)
                        .writer(writer)
                        .build()
        ).getId();

        String expectedTitle = "expectedTitle";
        String expectedContent = "expectedContent";

        AnnouncementUpdateRequestDto announcementUpdateRequestDto = AnnouncementUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/announcement/{id}";

        //when
        ResultActions resultActions = mvc.perform(
                put(url, id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsString(announcementUpdateRequestDto))
        );

        //then
        resultActions.andDo(print())
                .andDo(document("{class-name}/{method-name}"
                        , getDocumentRequest()
                        , getDocumentResponse()
                        , pathParameters(
                                parameterWithName("id").description("아이디")
                        )
                        , responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("writer").type(JsonFieldType.STRING).description("작성자"),
                                fieldWithPath("createdDate").type(JsonFieldType.STRING).description("최초 작성일"),
                                fieldWithPath("modifiedDate").type(JsonFieldType.STRING).description("최종 수정일")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(expectedTitle))
                .andExpect(jsonPath("$.content").value(expectedContent))
                .andExpect(jsonPath("$.writer").value(writer))
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.modifiedDate").exists());
    }

    @DisplayName("공지사항_삭제")
    @Test
    void announcement_delete() throws Exception {
        //given
        String title = "title";
        String content = "content";
        String writer = "writer";

        Long id = announcementRepository.save(
                Announcement.builder()
                        .title(title)
                        .content(content)
                        .writer(writer)
                        .build()
        ).getId();

        String url = "http://localhost:" + port + "/api/announcement/{id}";

        //when
        ResultActions resultActions = mvc.perform(
                delete(url, id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        //then
        resultActions.andDo(print())
                .andDo(document("{class-name}/{method-name}"
                        , getDocumentRequest()
                        , getDocumentResponse()
                        , pathParameters(
                                parameterWithName("id").description("아이디")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(id));
    }


}