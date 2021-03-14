package com.jongtix.dto.response;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDto<Dto, Entity> {

    private List<Dto> dtoList;

    private  int totalPages;        //총 페이지 번호

    private int pageNumber;        //현재 페이지 번호

    private int pageSize;         //목록 사이즈

    private int startPage;          //시작 페이지 번호

    private int endPage;            //끝 페이지 번호

    private boolean previous;   //이전

    private int previousPage;   //이전 페이지

    private boolean next;       //이후

    private int nextPage;

    private List<Integer> pageList;

    public PageResponseDto(Page<Entity> result, Function<Entity, Dto> function) {

        dtoList = result.stream().map(function).collect(Collectors.toList());

        totalPages = result.getTotalPages();

        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {

        this.pageNumber = pageable.getPageNumber() + 1;
        this.pageSize = pageable.getPageSize();

        int tempEndPage = ((int) Math.ceil(pageNumber / 10.0)) * 10;
        startPage = tempEndPage - 9;
        endPage = totalPages > tempEndPage ? tempEndPage : totalPages;
        previous = startPage > 1;
        if (previous) previousPage = startPage - 1;
        next = totalPages > tempEndPage;
        if (next) nextPage = tempEndPage + 1;

        pageList = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());

    }

}
