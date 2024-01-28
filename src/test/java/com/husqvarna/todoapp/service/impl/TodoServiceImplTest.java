package com.husqvarna.todoapp.service.impl;

import com.husqvarna.todoapp.constant.TodoApiConstants;
import com.husqvarna.todoapp.exception.TodoAPIException;
import com.husqvarna.todoapp.generated.model.CreateTodoRequest;
import com.husqvarna.todoapp.generated.model.TodoListResponse;
import com.husqvarna.todoapp.generated.model.TodoResponse;
import com.husqvarna.todoapp.service.TodoService;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to validate application service layer implementation logic
 */
@SpringBootTest()
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class TodoServiceImplTest {

    @Autowired
    TodoService todoService;


    /**
     * To test creation happy flow
     */
    @Test
    @Order(1)
    void createTodoItemTest() {
        CreateTodoRequest createTodoRequest = new CreateTodoRequest("MyTestTitle", 1);
        createTodoRequest.setCompleted(false);
        createTodoRequest.setUrl("MyTodoURL");
        TodoResponse createdRequest = todoService.createTodoResource(createTodoRequest);
        assertAll(
                () -> assertNotNull(createdRequest, "To check whether item is created or not"),
                () -> assertFalse(createdRequest.getCompleted()),
                () -> assertEquals("MyTodoURL", createdRequest.getUrl()),
                () -> assertEquals("MyTestTitle", createdRequest.getTitle())
        );
    }

    /**
     * To test update happy flow
     */
    @Test
    @Order(2)
    void updateTodoItemTest() {
        CreateTodoRequest patchRequest = new CreateTodoRequest("MyPatchRequest", 2);
        patchRequest.setCompleted(false);
        patchRequest.setUrl("MyTodoURL");
        TodoResponse updatedTodoItem = todoService.updateTodoItem(1L, patchRequest);

        assertAll(
                () -> assertNotNull(updatedTodoItem),
                () -> assertEquals("MyPatchRequest", updatedTodoItem.getTitle()),
                () -> assertEquals(1L, updatedTodoItem.getId())
        );
    }

    /**
     * To fetch list of items
     */
    @Test
    @Order(3)
    void getTodoItemsListTest() {
        CreateTodoRequest createTodoRequest2 = new CreateTodoRequest("MyTodoItemTitle2", 2);
        todoService.createTodoResource(createTodoRequest2);

        TodoListResponse todoListResponse1 = new TodoListResponse();
        todoListResponse1.setId(1L);
        todoListResponse1.setTitle("MyPatchRequest");

        TodoListResponse todoListResponse2 = new TodoListResponse();
        todoListResponse2.setId(2L);
        todoListResponse2.setTitle("MyTodoItemTitle2");

        var expectedListItems = new ArrayList<TodoListResponse>();
        expectedListItems.add(todoListResponse1);
        expectedListItems.add(todoListResponse2);

        List<TodoListResponse> todoResponseList = todoService.getTodoItems();

        assertAll(
                () -> assertNotNull(todoResponseList),
                () -> assertEquals(expectedListItems.size(), todoResponseList.size()),
                () -> assertIterableEquals(expectedListItems, todoResponseList)
        );


    }


    /**
     * To remove an item happy flow
     */
    @Test
    @Order(4)
    void deleteTodoItemTest() {
        assertDoesNotThrow(() -> todoService.removeItem(2L));
    }

    /**
     * To test update flow with the item which is already deleted
     */
    @Test
    @Order(5)
    void updateTodoItemWithDeletedItemTest() {
        CreateTodoRequest patchRequest = new CreateTodoRequest("MyInvalidPatchRequest", 2);
        patchRequest.setCompleted(false);
        patchRequest.setUrl("MyTodoURL");
        TodoAPIException todoAPIException = assertThrows(TodoAPIException.class, () -> todoService.updateTodoItem(2L, patchRequest), "Trying to update an item which does not exists");
        assertAll(
                () -> assertEquals(TodoApiConstants.ITEM_DOES_NOT_EXISTS_ERROR_CODE, todoAPIException.getErrorCode()),
                () -> assertEquals(TodoApiConstants.ITEM_DOES_NOT_EXISTS_ERROR_MESSAGE, todoAPIException.getErrorMessage())
        );
    }

    /**
     * To get the item which is already deleted - should return custom error code
     */
    @Test
    @Order(6)
    void getTodoItemWithDeletedItemTest() {
        TodoAPIException todoAPIException = assertThrows(TodoAPIException.class, () -> todoService.getTodoItem(2L), "Trying to get an item which does not exists");
        assertAll(
                () -> assertEquals(TodoApiConstants.INVALID_ITEM_ID_ERROR_CODE, todoAPIException.getErrorCode()),
                () -> assertEquals(TodoApiConstants.INVALID_ITEM_ID_ERROR_MESSAGE, todoAPIException.getErrorMessage())
        );
    }

    /**
     * To get item details post update call - Ensures that the item is updated correctly
     */
    @Test
    @Order(7)
    void getTodoItemWithPatchDetailsTest() {

        TodoResponse todoResponse = todoService.getTodoItem(1L);

        assertAll(
                () -> assertNotNull(todoResponse),
                () -> assertEquals("MyPatchRequest", todoResponse.getTitle()),
                () -> assertEquals(1L, todoResponse.getId()),
                () -> assertEquals(2, todoResponse.getOrder()),
                () -> assertEquals("MyTodoURL", todoResponse.getUrl()),
                () -> assertFalse(todoResponse.getCompleted())
        );
    }

    /**
     * To delete the item which is already deleted - should return custom error code
     */
    @Test
    @Order(8)
    void removeInvalidItemTest() {
        TodoAPIException todoAPIException = assertThrows(TodoAPIException.class, () -> todoService.removeItem(20L), "Trying to remove an item which does not exists");
        assertAll(
                () -> assertEquals(TodoApiConstants.INVALID_ITEM_ID_ERROR_CODE, todoAPIException.getErrorCode()),
                () -> assertEquals(TodoApiConstants.INVALID_ITEM_ID_ERROR_MESSAGE, todoAPIException.getErrorMessage())
        );
    }


}
