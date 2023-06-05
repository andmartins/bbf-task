package com.api.taskmanager.controllers;

import com.api.taskmanager.dtos.TaskDto;
import com.api.taskmanager.models.TaskModel;
import com.api.taskmanager.services.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/task")
public class TaskController {

    final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Object> saveTask(@RequestBody @Valid TaskDto taskDto){

        var taskModel = new TaskModel();
        BeanUtils.copyProperties(taskDto, taskModel);
        taskModel.setFechaCreacion(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskModel));
    }

    @GetMapping
    public ResponseEntity<Page<TaskModel>> getAllTasks(@PageableDefault(page = 0, size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTask(@PathVariable(value = "id") UUID id) {
        Optional<TaskModel> taskModelOptional = taskService.findById(id);
        if(!taskModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable(value = "id") UUID id){
        Optional<TaskModel> taskModelOptional = taskService.findById(id);
        if(!taskModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
        taskService.delete(taskModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted sucessfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable(value = "id") UUID id,
                                             @RequestBody @Valid TaskDto taskDto) {
        Optional<TaskModel> taskModelOptional = taskService.findById(id);
        if(!taskModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
        var taskModel = new TaskModel();
        BeanUtils.copyProperties(taskDto, taskModel);
        taskModel.setId(taskModelOptional.get().getId());
        taskModel.setFechaCreacion(taskModelOptional.get().getFechaCreacion());

        return ResponseEntity.status(HttpStatus.OK).body(taskService.save(taskModel));
    }
}
