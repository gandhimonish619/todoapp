package com.husqvarna.todoapp.service;

import com.husqvarna.todoapp.generated.model.CreateTodoRequest;
import com.husqvarna.todoapp.generated.model.TodoListResponse;
import com.husqvarna.todoapp.generated.model.TodoResponse;

import java.util.List;

/**
 * Service contract for providing implementation to the application services
 */
public interface TodoService {
    /**
     * Method to create the resource
     *
     * @param todoCreateRequest request details for the item creation
     * @return item details as response along with id
     */
    TodoResponse createTodoResource(CreateTodoRequest todoCreateRequest);

    /**
     * Method to fetch the items
     *
     * @return list of all the items
     */
    List<TodoListResponse> getTodoItems();

    /**
     * To retrieve item details
     *
     * @param id with which the item details is to be searched or retrieved
     * @return item details as response
     */
    TodoResponse getTodoItem(Long id);

    /**
     * Will be used to update the item
     *
     * @param id which item has to be updated
     * @param patchRequest updated item details
     * @return item details as response
     */
    TodoResponse updateTodoItem(Long id, CreateTodoRequest patchRequest);

    /**
     * Method to remove the item
     *
     * @param id with which the item has to be removed
     */
    void removeItem(Long id);
}
