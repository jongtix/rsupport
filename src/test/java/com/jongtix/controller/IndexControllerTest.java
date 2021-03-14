package com.jongtix.controller;

import com.jongtix.domain.announcement.Announcement;
import com.jongtix.domain.announcement.AnnouncementRepository;
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

import static com.jongtix.utils.ApiDocumentUtils.getDocumentRequest;
import static com.jongtix.utils.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IndexControllerTest {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup(RestDocumentationContextProvider restDocumentation) {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("인덱스_페이지_로딩")
    @Test
    void announcement_index() throws Exception {
        //given
        String url = "/";

        //when
        ResultActions resultActions = mvc.perform(
                get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        //then
        resultActions.andDo(print())
                .andDo(document("{class-name}/{method-name}"
                        , getDocumentRequest()
                        , getDocumentResponse()
                ))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/announcement/list"));
    }

    @DisplayName("공지사항_리스트")
    @Test
    void announcement_list() throws Exception {
        //given
        String url = "/announcement/list";

        //when
        ResultActions resultActions = mvc.perform(
                get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        //then
        resultActions.andDo(print())
                .andDo(document("{class-name}/{method-name}"
                        , getDocumentRequest()
                        , getDocumentResponse()
                ))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pageRequestDto"))
                .andExpect(model().attributeExists("announcements"))
                .andExpect(view().name("announcement-list"));
    }

    @DisplayName("공지사항_등록")
    @Test
    void announcement_register() throws Exception {
        //given
        String url = "/announcement/register";

        //when
        ResultActions resultActions = mvc.perform(
                get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        //then
        resultActions.andDo(print())
                .andDo(document("{class-name}/{method-name}"
                        , getDocumentRequest()
                        , getDocumentResponse()
                ))
                .andExpect(status().isOk())
                .andExpect(view().name("announcement-register"));
    }

    @DisplayName("공지사항_보기")
    @Test
    void announcement_view() throws Exception {
        //given
        Long id = announcementRepository.save(
                Announcement.builder()
                        .title("title")
                        .content("content")
                        .writer("writer")
                        .build()
        ).getId();

        String url = "/announcement/view/{id}";

        //when
        ResultActions resultActions = mvc.perform(
                get(url, id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        //then
        resultActions.andDo(print())
                .andDo(document("{class-name}/{method-name}"
                        , getDocumentRequest()
                        , getDocumentResponse()
                ))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pageRequestDto"))
                .andExpect(model().attributeExists("announcement"))
                .andExpect(view().name("announcement-view"));
    }

    @DisplayName("공지사항_수정_보기")
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

        String url = "/announcement/update/{id}";

        //when
        ResultActions resultActions = mvc.perform(
                get(url, id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        //then
        resultActions.andDo(print())
                .andDo(document("{class-name}/{method-name}"
                        , getDocumentRequest()
                        , getDocumentResponse()
                ))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pageRequestDto"))
                .andExpect(model().attributeExists("announcement"))
                .andExpect(view().name("announcement-update"));
    }

}