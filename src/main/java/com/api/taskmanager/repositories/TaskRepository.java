package com.api.taskmanager.repositories;

import com.api.taskmanager.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> {

    // Por ahora no hay validaciones a aplicar debido al test solicitar solamente dos campos
}
