package oi.github.antoniobrandao.clientes.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServicoCountDTO {
    private String tipoServico;
    private Long count;
}