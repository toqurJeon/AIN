package com.ssafy.ain.idealperson.entity;

import com.ssafy.ain.global.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "first_name")
public class FirstName extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private String gender;
}