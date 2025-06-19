package com.example.outsourcingproject.domain.task.repository;

import com.example.jooq.generated.enums.TaskStatus;
import com.example.jooq.generated.tables.User;
import com.example.outsourcingproject.domain.dashboard.dto.TaskOutline;
import com.example.outsourcingproject.domain.task.dto.request.TaskReadRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.entity.Priority;
import com.example.outsourcingproject.domain.task.entity.QTask;
import com.example.outsourcingproject.domain.task.entity.Status;
import com.example.outsourcingproject.domain.user.dto.AssigneeResponse;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;
import static com.example.jooq.generated.Tables.TASK;
import static com.example.jooq.generated.Tables.USER;

@Repository
@RequiredArgsConstructor
public class QTaskRepositoryImpl implements QTaskRepository {
    private final JPAQueryFactory queryFactory;
    private final DSLContext dsl;

    @Override
    public TaskResponse findTaskById(Long id) {
        // 별칭 설정
        User ASSIGNEE = USER.as("assignee");
        User GENERATOR = USER.as("generator");

        return dsl.select(
                TASK.ID,
                TASK.TITLE,
                TASK.DESCRIPTION,
                TASK.DUE_DATE,
                TASK.PRIORITY,
                TASK.STATUS,
                TASK.ASSIGNEE_ID,
                        ASSIGNEE.ID.as("assignee_id"),
                        ASSIGNEE.USERNAME,
                        ASSIGNEE.NAME,
                        ASSIGNEE.EMAIL,
                TASK.CREATED_AT,
                TASK.UPDATED_AT
                )
                .from(TASK)
                .leftJoin(ASSIGNEE).on(ASSIGNEE.ID.eq(TASK.ASSIGNEE_ID))
                .leftJoin(GENERATOR).on(GENERATOR.ID.eq(TASK.GENERATOR_ID))
                .where(TASK.ID.eq(id),
                        TASK.IS_DELETED.eq(false))
                .fetchOne(record -> {
                    if (record == null) return null;

                    TaskResponse response = new TaskResponse();
                    response.setId(record.get(TASK.ID));
                    response.setTitle(record.get(TASK.TITLE));
                    response.setDescription(record.get(TASK.DESCRIPTION));
                    response.setDueDate(record.get(TASK.DUE_DATE));
                    response.setPriority(record.get(TASK.PRIORITY, Priority.class));
                    response.setStatus(record.get(TASK.STATUS, Status.class));
                    response.setAssigneeId(record.get(TASK.ASSIGNEE_ID, Long.class));
                    response.setCreatedAt(record.get(TASK.CREATED_AT));
                    response.setUpdatedAt(record.get(TASK.UPDATED_AT));

                    AssigneeResponse assignee = new AssigneeResponse();
                    assignee.setId(record.get(ASSIGNEE.ID, Long.class));
                    assignee.setUsername(record.get(ASSIGNEE.USERNAME));
                    assignee.setName(record.get(ASSIGNEE.NAME));
                    assignee.setEmail(record.get(ASSIGNEE.EMAIL));
                    response.setAssignee(assignee);

                    return response;
                });
    }

    @Override
    public Page<TaskResponse> findTasks(TaskReadRequest request){
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        // 별칭 지정
        User ASSIGNEE = USER.as("assignee");
        User GENERATOR = USER.as("generator");

        List<TaskResponse> content = dsl.select(
                        TASK.ID,
                        TASK.TITLE,
                        TASK.DESCRIPTION,
                        TASK.DUE_DATE,
                        TASK.PRIORITY,
                        TASK.STATUS,
                        TASK.ASSIGNEE_ID,
                        ASSIGNEE.ID.as("assignee_id"),
                        ASSIGNEE.USERNAME,
                        ASSIGNEE.NAME,
                        ASSIGNEE.EMAIL,
                        TASK.CREATED_AT,
                        TASK.UPDATED_AT
                )
                .from(TASK)
                .leftJoin(ASSIGNEE).on(ASSIGNEE.ID.eq(TASK.ASSIGNEE_ID))
                .where( /* 조건 */ )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .map(record -> {
                    TaskResponse response = new TaskResponse();
                    response.setId(record.get(TASK.ID));
                    response.setTitle(record.get(TASK.TITLE));
                    response.setDescription(record.get(TASK.DESCRIPTION));
                    response.setDueDate(record.get(TASK.DUE_DATE));
                    response.setPriority(record.get(TASK.PRIORITY, Priority.class));
                    response.setStatus(record.get(TASK.STATUS, Status.class));
                    response.setAssigneeId(record.get(TASK.ASSIGNEE_ID, Long.class));
                    response.setCreatedAt(record.get(TASK.CREATED_AT));
                    response.setUpdatedAt(record.get(TASK.UPDATED_AT));

                    AssigneeResponse assignee = new AssigneeResponse();
                    assignee.setId(record.get(ASSIGNEE.ID, Long.class));
                    assignee.setUsername(record.get(ASSIGNEE.USERNAME));
                    assignee.setName(record.get(ASSIGNEE.NAME));
                    assignee.setEmail(record.get(ASSIGNEE.EMAIL));
                    response.setAssignee(assignee);

                    return response;
                });


        // 전체 개수 쿼리
        int total = dsl.selectCount()
                .from(TASK)
                .where(
                        isTitleContains(request.getTitle()),
                        isStatusEquals(request.getStatus()),
                        isAssigneeEquals(request.getAssigneeId()),
                        isDescriptionContains(request.getDescription()),
                        TASK.IS_DELETED.eq(false)
                )
                .fetchOne(request.getPage(), int.class);

        return new PageImpl<>(content, pageable, total);
    }

    private Condition isTitleContains(String title) {
        return StringUtils.hasText(title) ? TASK.TITLE.containsIgnoreCase(title) : DSL.noCondition();
    }

    private Condition isDescriptionContains(String description) {
        return StringUtils.hasText(description) ? TASK.DESCRIPTION.containsIgnoreCase(description) : DSL.noCondition();
    }

    private Condition isStatusEquals(Status status) {
        return status != null ? TASK.STATUS.eq(TaskStatus.valueOf(status.name())) : DSL.noCondition();
    }

    private Condition isAssigneeEquals(Long managerId) {
        return managerId != null ? TASK.ASSIGNEE_ID.eq(managerId.intValue()) : DSL.noCondition();
    }

    @Override
    public List<TaskResponse> findTasksByUserId(Long userId) {
        // 별칭 설정
        User ASSIGNEE = USER.as("assignee");
        User GENERATOR = USER.as("generator");

        return dsl.select(
                        TASK.ID,
                        TASK.TITLE,
                        TASK.DESCRIPTION,
                        TASK.DUE_DATE,
                        TASK.PRIORITY,
                        TASK.STATUS,
                        TASK.ASSIGNEE_ID,
                        ASSIGNEE.ID.as("assignee_id"),
                        ASSIGNEE.USERNAME.as("assignee_username"),
                        ASSIGNEE.NAME.as("assignee_name"),
                        ASSIGNEE.EMAIL.as("assignee_email"),
                        TASK.CREATED_AT,
                        TASK.UPDATED_AT
                )
                .from(TASK)
                .leftJoin(ASSIGNEE).on(ASSIGNEE.ID.eq(TASK.ASSIGNEE_ID))
                .leftJoin(GENERATOR).on(GENERATOR.ID.eq(TASK.GENERATOR_ID))
                .where(
                        TASK.ASSIGNEE_ID.eq(Math.toIntExact(userId)),
                        TASK.IS_DELETED.eq(false)
                )
                .fetch()
                .map(record -> {
                    TaskResponse response = new TaskResponse();
                    response.setId(record.get(TASK.ID));
                    response.setTitle(record.get(TASK.TITLE));
                    response.setDescription(record.get(TASK.DESCRIPTION));
                    response.setDueDate(record.get(TASK.DUE_DATE));
                    response.setPriority(record.get(TASK.PRIORITY, Priority.class));
                    response.setStatus(record.get(TASK.STATUS, Status.class));
                    response.setAssigneeId(record.get(TASK.ASSIGNEE_ID, Long.class));
                    response.setCreatedAt(record.get(TASK.CREATED_AT));
                    response.setUpdatedAt(record.get(TASK.UPDATED_AT));

                    AssigneeResponse assignee = new AssigneeResponse();
                    assignee.setId(record.get(ASSIGNEE.ID, Long.class));
                    assignee.setUsername(record.get(ASSIGNEE.USERNAME));
                    assignee.setName(record.get(ASSIGNEE.NAME));
                    assignee.setEmail(record.get(ASSIGNEE.EMAIL));
                    response.setAssignee(assignee);

                    return response;
                });

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
                        task.dueDate.before(now)
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
