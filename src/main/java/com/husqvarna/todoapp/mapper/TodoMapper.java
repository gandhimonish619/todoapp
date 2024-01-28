package com.husqvarna.todoapp.mapper;

import com.husqvarna.todoapp.generated.model.CreateTodoRequest;
import com.husqvarna.todoapp.generated.model.TodoListResponse;
import com.husqvarna.todoapp.generated.model.TodoResponse;
import com.husqvarna.todoapp.persistence.entity.TodoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(implementationPackage = "com.husqvarna.todoapp.mapper.impl", unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TodoMapper
{

    TodoEntity mapToEntity(CreateTodoRequest createTodoRequest);

    TodoResponse mapFromEntity(TodoEntity todoEntity);

    List<TodoListResponse> mapItemsFromEntities(List<TodoEntity> todoEntities);


}
