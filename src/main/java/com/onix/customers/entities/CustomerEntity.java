package com.onix.customers.entities;

import com.onix.customers.enumerations.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(length = 200)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private Gender gender;
}
