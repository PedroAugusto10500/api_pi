package oi.github.antoniobrandao.clientes.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class ServicoDTO {
    private Integer id; // Corrigido para Integer
    private String descricao;
    private String data;
    private String dataFinal;
    private String tipoServico;
    private String situacao;
    private List<Integer> idFuncionarios; // Corrigido para Integer
}