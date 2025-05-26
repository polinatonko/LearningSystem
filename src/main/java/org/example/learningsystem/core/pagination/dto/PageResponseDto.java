package org.example.learningsystem.core.pagination.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public class PageResponseDto<T> {

    private List<T> content;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;

    public static <T, R> PageResponseDto<R> of(Page<T> page, Function<T, R> mapper) {
        var mappedContent = page.map(mapper).getContent();
        return new PageResponseDto<>(
                mappedContent,
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

}
