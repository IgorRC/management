package com.gniuscode.sie.management.rrhh.entity;

import com.gniuscode.sie.management.security.entity.User;
import jakarta.persistence.*;

import java.time.LocalDate;

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Datos Personales ---
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    private String phone;

    private String address;

    // --- Datos Corporativos ---

    @Column(unique = true)
    private String corporateEmail;

    private LocalDate hireDate;

    private Double salary;

    private boolean active = true;

    // --- Relaciones ---

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
