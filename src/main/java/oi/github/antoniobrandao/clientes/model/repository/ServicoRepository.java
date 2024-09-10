package oi.github.antoniobrandao.clientes.model.repository;

import oi.github.antoniobrandao.clientes.model.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {


    @Query("SELECT COUNT(s) FROM Servico s")
    long countAllServicos();
    @Query("select s from Servico s")
    List<Servico> findAllServicos();


    @Query("SELECT s.tipoServico, COUNT(s) FROM Servico s GROUP BY s.tipoServico")
    List<Object[]> countServicosByTipoServico();
}

