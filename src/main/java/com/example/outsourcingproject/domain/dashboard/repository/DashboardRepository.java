package com.example.outsourcingproject.domain.dashboard.repository;

import com.example.outsourcingproject.domain.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends JpaRepository<Task, Long>, QDashboardRepository {
}
