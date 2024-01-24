package com.task.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "task")
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String description;

    @Enumerated(EnumType.ORDINAL)
    private Status status;
}
