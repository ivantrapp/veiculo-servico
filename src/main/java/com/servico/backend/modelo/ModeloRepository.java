package com.servico.backend.modelo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, String> {

    Modelo findByFipeId(Integer fipeId);

    @Query("SELECT COUNT(m) FROM Modelo m WHERE m.marca.id = :idMarca")
    long countByMarcaId(@Param("idMarca") String idMarca);
}
