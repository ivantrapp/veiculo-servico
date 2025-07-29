package com.servico.backend.modelo;

import com.servico.backend.marca.Marca;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(name = "fipe_id")
    private Integer fipeId;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;
}
