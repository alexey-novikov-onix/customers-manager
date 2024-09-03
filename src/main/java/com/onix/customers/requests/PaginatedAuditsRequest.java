package com.onix.customers.requests;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedAuditsRequest {

    @Min(1)
    private int itemsCount = 100;
    @Min(1)
    private int page = 1;
    private String orderBy = "id";
    private Sort.Direction orderDirection = Sort.Direction.DESC;
}
