package com.jongtix.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDto {

    private int currentPage;

    private int pageSize;

    public PageRequestDto() {
        this.currentPage = 1;
        this.pageSize = 10;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(this.currentPage - 1, this.pageSize, sort);
    }

}
