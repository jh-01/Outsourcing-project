package com.example.outsourcingproject.domain.task.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingResponse{
    private Long totalElements;
    private int totalPages;
    private int size;
    private int number;
}
