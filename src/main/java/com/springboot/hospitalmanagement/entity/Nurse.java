package com.springboot.hospitalmanagement.entity;

import lombok.*;

import javax.persistence.*;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name ="nurses")
public class Nurse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nurse_id")
    private Long nurseId;

    @Column(name = "nurse_name")
    private String nurseName;

    private String email;

    @Column(name = "is_allocated")
    private boolean isAllocated = true;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}
