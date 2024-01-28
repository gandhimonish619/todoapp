package com.husqvarna.todoapp.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity object for item details
 * Have quoted the columns as we have columns with H2 keywords as well.
 * id - acts as a primary column having unique id per item
 */
@Getter
@Setter
@Entity
@Table(name = "TODO_DETAILS")
public class TodoEntity {

    @Column(name = "`ID`")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "`TITLE`")
    private String title;
    @Column(name = "`COMPLETED`")
    private String completed;
    @Column(name = "`URL`")
    private String url;
    @Column(name = "`ORDER`")
    private int order;


}
