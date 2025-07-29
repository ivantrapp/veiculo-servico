package com.servico.backend.veiculo;

import com.servico.backend.marca.Marca;
import com.servico.backend.modelo.Modelo;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "id_modelo")
    private Modelo modelo;

    @Column(name = "preco_anunciado")
    private Float precoAnunciado;

    @Column(name = "preco_fipe")
    private Float precoFipe;

    @Column(unique = true)
    private String placa;

    @Column
    private int ano;

    @Column
    private String dataCadastro;
}
