package com.project.restapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name="MAINS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    @NotBlank
    private String name;

    @Column
    @NotBlank
    private String description;

    @ManyToOne
    private AssociatedEntity associated;

}