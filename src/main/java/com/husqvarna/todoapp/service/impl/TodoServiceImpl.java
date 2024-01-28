package com.husqvarna.todoapp.service.impl;

import com.husqvarna.todoapp.constant.TodoApiConstants;
import com.husqvarna.todoapp.exception.TodoAPIException;
import com.husqvarna.todoapp.generated.model.CreateTodoRequest;
import com.husqvarna.todoapp.generated.model.TodoListResponse;
import com.husqvarna.todoapp.generated.model.TodoResponse;
import com.husqvarna.todoapp.mapper.TodoMapper;
import com.husqvarna.todoapp.persistence.entity.TodoEntity;
import com.husqvarna.todoapp.persistence.repository.TodoRepository;
import com.husqvarna.todoapp.service.TodoService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer implementation to provide CRUD support to the application
 */
@Slf4j
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;


    /**
     * @param todoRepository used for DB interactions
     * @param todoMapper     to map object to and from entity
     */
    @Inject
    public TodoServiceImpl(TodoRepository todoRepository, TodoMapper todoMapper) {
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
    }

    @Override
    public TodoResponse createTodoResource(CreateTodoRequest createTodoRequest) {
        TodoEntity todoEntity = todoRepository.save(todoMapper.mapToEntity(createTodoRequest));
        return todoMapper.mapFromEntity(todoEntity);
    }

    @Override
    public List<TodoListResponse> getTodoItems() {
        List<TodoEntity> todoEntities = todoRepository.findAll();
        return todoMapper.mapItemsFromEntities(todoEntities);
    }

    @Override
    public TodoResponse getTodoItem(Long id) {
        return todoRepository.findById(id).map(todoMapper::mapFromEntity).orElseThrow(() -> new TodoAPIException(TodoApiConstants.INVALID_ITEM_ID_ERROR_CODE, TodoApiConstants.INVALID_ITEM_ID_ERROR_MESSAGE, id.toString())
        );
    }

    @Override
    public TodoResponse updateTodoItem(Long id, CreateTodoRequest patchRequest) {
        TodoEntity todoEntity = todoMapper.mapToEntity(patchRequest);
        if (todoRepository.findById(id).isPresent()) {
            todoEntity.setId(id);
            todoRepository.save(todoEntity);
        } else {
            log.info("Unable to find the item with provided id :: {}", id);
            throw new TodoAPIException(TodoApiConstants.ITEM_DOES_NOT_EXISTS_ERROR_CODE, TodoApiConstants.ITEM_DOES_NOT_EXISTS_ERROR_MESSAGE, id.toString());

        }
        return todoMapper.mapFromEntity(todoEntity);
    }

    @Override
    public void removeItem(Long id) {
        Optional<TodoEntity> itemToDelete = todoRepository.findById(id);
        itemToDelete.ifPresentOrElse(todoRepository::delete,
                () -> {
                    log.info("Unable to find the item with provided id :: {}", id);
                    throw new TodoAPIException(TodoApiConstants.INVALID_ITEM_ID_ERROR_CODE, TodoApiConstants.INVALID_ITEM_ID_ERROR_MESSAGE, id.toString());
                }
        );
    }
}
