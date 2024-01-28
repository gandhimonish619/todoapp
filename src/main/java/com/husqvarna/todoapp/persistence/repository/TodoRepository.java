package com.husqvarna.todoapp.persistence.repository;

import com.husqvarna.todoapp.persistence.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class to interact with item details table
 */
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

}
