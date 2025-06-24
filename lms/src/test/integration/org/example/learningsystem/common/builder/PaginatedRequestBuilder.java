package org.example.learningsystem.common.builder;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.data.domain.Sort.Order;

public class PaginatedRequestBuilder {

    private static final String ORDER_FORMAT = "%s,%s";
    private static final String PAGE_NUMBER_PARAM = "page";
    private static final String PAGE_SIZE_PARAM = "size";
    private static final String SORT_PARAM = "sort";

    public static RequestBuilder build(MockHttpServletRequestBuilder builder, PageRequest pageRequest) {
        var paginatedBuilder = applyPagination(builder, pageRequest);
        var sort = pageRequest.getSort();
        return applySort(paginatedBuilder, sort);
    }

    private static MockHttpServletRequestBuilder applyPagination(MockHttpServletRequestBuilder builder, PageRequest pageRequest) {
        int page = pageRequest.getPageNumber();
        int size = pageRequest.getPageSize();
        return builder
                .queryParam(PAGE_NUMBER_PARAM, String.valueOf(page))
                .queryParam(PAGE_SIZE_PARAM, String.valueOf(size));
    }

    private static RequestBuilder applySort(MockHttpServletRequestBuilder builder, Sort sort) {
        return sort.isSorted()
                ? builder.queryParam(SORT_PARAM, getOrders(sort))
                : builder;
    }

    private static String[] getOrders(Sort sort) {
        return sort.stream()
                .map(PaginatedRequestBuilder::formatOrder)
                .toArray(String[]::new);
    }

    private static String formatOrder(Order order) {
        var property = order.getProperty();
        var direction = order.getDirection();
        return ORDER_FORMAT.formatted(property, direction);
    }
}
