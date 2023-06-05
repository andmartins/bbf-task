package com.api.taskmanager.services;

import com.api.taskmanager.models.TaskModel;
import com.api.taskmanager.repositories.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskModel save(TaskModel taskModel) {
        return taskRepository.save(taskModel);
    }

    // Por ahora no necesita crear metodos de validacion ya que el desafio no presenta muchos campos

    public Page<TaskModel> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Optional<TaskModel> findById(UUID id) {
        return taskRepository.findById(id);
    }

    @Transactional
    public void delete(TaskModel parkingSpotModel) {
        taskRepository.delete(parkingSpotModel);
    }
}
