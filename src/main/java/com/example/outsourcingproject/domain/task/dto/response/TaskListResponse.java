package com.example.outsourcingproject.domain.task.dto.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskListResponse {
    private List<TaskResponse> content;
    private PagingResponse paging;
}
