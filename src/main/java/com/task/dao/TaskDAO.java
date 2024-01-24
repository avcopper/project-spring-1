package com.task.dao;

import com.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDAO extends JpaRepository<Task, Integer> {
}
