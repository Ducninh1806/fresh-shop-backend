package com.ducninh.freshfood.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private ERole name;
}
