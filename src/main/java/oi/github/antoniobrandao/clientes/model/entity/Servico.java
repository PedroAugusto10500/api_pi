package oi.github.antoniobrandao.clientes.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Entity
@Data
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 1000000) // Use um número grande para o comprimento máximo
    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;


    @Column(name = "data", nullable = false)
    @NotNull(message = "A data é obrigatória")
    private LocalDate data;

    @Column(name = "data_final", nullable = false)
    @NotNull(message = "A data final é obrigatória")
    private LocalDate dataFinal;

    @Column(nullable = false)
    @NotBlank(message = "O tipo de serviço é obrigatório")
    private String tipoServico;

    @Column(precision = 10, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor deve ser maior que zero")
    @NotNull(message = "O valor é obrigatório")
    private BigDecimal valor;

    @Column(nullable = false)
    @NotBlank(message = "A situação é obrigatória")
    private String situacao;

    @ManyToMany
    @JoinTable(
            name = "servico_funcionario",
            joinColumns = @JoinColumn(name = "servico_id"),
            inverseJoinColumns = @JoinColumn(name = "funcionario_id")
    )
    private List<Funcionario> funcionarios;

    // Getters e setters
}