package oi.github.antoniobrandao.clientes.rest.dto;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FuncionarioDTO {
    private Integer id;
    private String nome;
    private String cpf;
    private LocalDate dataAdmissao;
    private String cargo;
    private String habilidades;
    private String email;
    private String telefone;
    // Outros campos, se necessário
}