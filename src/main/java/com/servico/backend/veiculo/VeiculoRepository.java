package com.servico.backend.veiculo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, String> {

    Veiculo findByPlaca(String placa);

    @Query("SELECT v FROM Veiculo v WHERE v.marca.id = :marcaId")
    Page<Veiculo> findAllByMarcaId(@Param("marcaId") String marcaId, Pageable pageable);
}
