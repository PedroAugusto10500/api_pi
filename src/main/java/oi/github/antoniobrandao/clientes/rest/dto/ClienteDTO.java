package oi.github.antoniobrandao.clientes.rest.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClienteDTO {
    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
}