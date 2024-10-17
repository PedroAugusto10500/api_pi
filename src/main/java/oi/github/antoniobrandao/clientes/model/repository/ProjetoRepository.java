package oi.github.antoniobrandao.clientes.model.repository;
import oi.github.antoniobrandao.clientes.model.entity.Projeto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

    List<Projeto> findByDescricaoContaining(String descricao);


}
