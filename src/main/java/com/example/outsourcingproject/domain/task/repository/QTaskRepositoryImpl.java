package com.example.outsourcingproject.domain.task.repository;

import com.example.outsourcingproject.domain.dashboard.dto.TaskOutline;
import com.example.outsourcingproject.domain.task.dto.request.TaskReadRequest;
import com.example.outsourcingproject.domain.task.dto.response.QTaskResponse;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.entity.QTask;
import com.example.outsourcingproject.domain.task.entity.Status;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.outsourcingproject.domain.task.entity.QTask.task;

@Repository
@RequiredArgsConstructor
public class QTaskRepositoryImpl implements QTaskRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public TaskResponse findTaskById(Long id){

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
                        task.CreatedAt,
                        task.ModifiedAt
                )
        ).from(task)
                .leftJoin(task.manager)
                .leftJoin(task.generator)
                .where(task.id.eq(id), task.isDeleted.isFalse())
                .fetchOne();
    }

    @Override
    public Page<TaskResponse> findTasks(TaskReadRequest request){
        QTask task = QTask.task;
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        List<TaskResponse> taskResponseList = queryFactory.select(
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
                    task.CreatedAt,
                    task.ModifiedAt
            )).from(task)
                .leftJoin(task.manager)
                .leftJoin(task.generator)
                .where(
                        isTitleContains(request.getTitle()),
                        isDescriptionContains(request.getDescription()),
                        isStatusEquals(request.getStatus()),
                        isManagerContains(request.getManagerId()),
                        task.isDeleted.isFalse()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(task.count())
                .from(task)
                .where(
                        isTitleContains(request.getTitle()),
                        isDescriptionContains(request.getDescription()),
                        isStatusEquals(request.getStatus()),
                        isManagerContains(request.getManagerId()),
                        task.isDeleted.isFalse()
                )
                .fetchOne();

        return new PageImpl<>(taskResponseList, pageable, total);

    }

    private BooleanExpression isTitleContains(String title){
        return StringUtils.hasText(title) ? task.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression isDescriptionContains(String description){
        return StringUtils.hasText(description) ? task.description.containsIgnoreCase(description) : null;
    }

    private BooleanExpression isStatusEquals(Status status){
        return status != null ? task.status.eq(status) : null;
    }

    private BooleanExpression isManagerContains(Long managerId){
        return managerId != null ? task.manager.id.eq(managerId.intValue()) : null;
    }

    @Override
    public List<TaskResponse> findTasksByUserId(Long userId){
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
                                task.CreatedAt,
                                task.ModifiedAt
                        )).from(task)
                .leftJoin(task.manager)
                .leftJoin(task.generator)
                .where(
                        task.manager.id.eq(Math.toIntExact(userId)),
                        task.isDeleted.isFalse()
                )
                .fetch();
    }


    @Override
    public TaskOutline findDashboard() {
        QTask task = QTask.task;
        int todoCount = 0;
        int inProgressCount = 0;
        int doneCount = 0;

        // 전체 태스크 수
        long totalCount = queryFactory
                .select(task.count())
                .from(task)
                .where(task.isDeleted.isFalse())
                .fetchOne();

        // 상태별 태스크 수
        List<Tuple> statusCounts = queryFactory
                .select(task.status, task.count())
                .from(task)
                .where(task.isDeleted.isFalse())
                .groupBy(task.status)
                .fetch();

        for (Tuple tuple : statusCounts) {
            Status status = tuple.get(task.status);
            Long count = tuple.get(task.count());

            if (status == Status.TODO) {
                todoCount = count.intValue();
            } else if (status == Status.IN_PROGRESS) {
                inProgressCount = count.intValue();
            } else if (status == Status.DONE) {
                doneCount = count.intValue();
            }
        }

        // 완료율
        double completionRate = totalCount == 0 ? 0.0 : (double) doneCount / totalCount;

        // 기한 초과 태스크수
        LocalDateTime now = LocalDateTime.now();

        long overDueDateCount = queryFactory
                .select(task.count())
                .from(task)
                .where(
                        task.isDeleted.isFalse(),
                        task.status.eq(Status.IN_PROGRESS).or(task.status.eq(Status.TODO)),
                        task.deadline.before(now)
                )
                .fetchOne();

        return new TaskOutline(
                totalCount,
                (long) todoCount,
                (long) inProgressCount,
                (long) doneCount,
                completionRate,
                overDueDateCount
        );
    }

}
