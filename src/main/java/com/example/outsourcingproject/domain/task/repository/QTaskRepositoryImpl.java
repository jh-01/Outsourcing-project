package com.example.outsourcingproject.domain.task.repository;

import com.example.outsourcingproject.domain.task.dto.request.TaskReadRequest;
import com.example.outsourcingproject.domain.task.dto.response.QTaskResponse;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.entity.QTask;
import com.example.outsourcingproject.domain.task.entity.Status;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.outsourcingproject.domain.task.entity.QTask.task;

@Repository
@RequiredArgsConstructor
public abstract class QTaskRepositoryImpl implements QTaskRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public TaskResponse searchTask(Long id){
        return queryFactory.select(
                new QTaskResponse(
                        task.id,
                        task.manager.name,
                        task.generator.name,
                        task.title,
                        task.description,
                        task.priority,
                        task.deadline,
                        task.status,
                        task.startAt,
                        task.createdAt,
                        task.ModifiedAt
                )
        ).from(task)
                .where(task.id.eq(id))
                .fetchOne();
    }
    @Override
    public List<TaskResponse> searchTasks(TaskReadRequest request){
        QTask task = QTask.task;
        BooleanBuilder builder = new BooleanBuilder();

        if(request.getTitle() != null){
            builder.and(task.title.containsIgnoreCase(request.getTitle()));
        }
        if(request.getDescription() != null){
            builder.and(task.description.containsIgnoreCase(request.getDescription()));
        }
        if(request.getStatus() != null){
            builder.and(task.status.eq(request.getStatus()));
        }

        Pageable pageable = (Pageable) PageRequest.of(request.getPage(), request.getSize());

        return queryFactory.select(
            new QTaskResponse(
                    task.id,
                    task.manager.name,
                    task.generator.name,
                    task.title,
                    task.description,
                    task.priority,
                    task.deadline,
                    task.status,
                    task.startAt,
                    task.createdAt,
                    task.ModifiedAt
            )).from(task)
                .where(
                        isTitleContains(request.getTitle()),
                        isDescriptionContains(request.getDescription()),
                        isStatusContains(request.getStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression isTitleContains(String title){
        return title.isEmpty() ? null : task.title.containsIgnoreCase(title);
    }

    private BooleanExpression isDescriptionContains(String description){
        return description.isEmpty() ? null : task.description.containsIgnoreCase(description);
    }

    private BooleanExpression isStatusContains(Status status){
        return status == null ? null : task.status.stringValue().containsIgnoreCase(status.toString());
    }

}
