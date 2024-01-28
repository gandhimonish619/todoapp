package com.husqvarna.todoapp.rest;


import com.husqvarna.todoapp.generated.api.TodoRequestsApi;
import com.husqvarna.todoapp.generated.model.CreateTodoRequest;
import com.husqvarna.todoapp.generated.model.TodoListResponse;
import com.husqvarna.todoapp.generated.model.TodoResponse;
import com.husqvarna.todoapp.service.TodoService;
import jakarta.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TodoController implements TodoRequestsApi {

    private final TodoService todoService;

    @Inject
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    public ResponseEntity<TodoResponse> todoCreate(
            CreateTodoRequest createTodoRequest
    ) {
        return new ResponseEntity<>(todoService.createTodoResource(createTodoRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<TodoListResponse>> todoGetAll() {
        return new ResponseEntity<>(todoService.getTodoItems(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TodoResponse> todoGetOne(
            Long id
    ) {
        return new ResponseEntity<>(todoService.getTodoItem(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TodoResponse> todoUpdate(
            Long id,
            CreateTodoRequest patchRequest
    ) {
        return new ResponseEntity<>(todoService.updateTodoItem(id, patchRequest), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> todoDeleteOne(
            Long id
    ) {
        todoService.removeItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
