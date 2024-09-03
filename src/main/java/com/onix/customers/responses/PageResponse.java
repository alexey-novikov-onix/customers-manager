package com.onix.customers.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse<T> {
    private int page;
    private int itemsCount;
    private int totalPages;
    private long totalItems;
    private List<T> data;
}
