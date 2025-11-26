package oi.github.antoniobrandao.clientes.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.*
import jakarta.validation.constraints.*
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 150, message = "A descrição deve ter no máximo 150 caracteres")
    private String descricao;

    @Column(name = "data", nullable = false)
    @NotNull(message = "A data é obrigatória")

    private LocalDate data;

    @Column(name = "data_final", nullable = false)
    @NotNull(message = "A data final é obrigatória")
    private LocalDate dataFinal;

    @Column(precision = 10, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor deve ser maior que zero")
    @NotNull(message = "O valor é obrigatório")
    private BigDecimal valor;


    @Column(nullable = false)
    @NotNull(message = "A situação é obrigatória")
    private String situacao;

    @ManyToMany
    @JoinTable(
            name = "projeto_servico",
            joinColumns = @JoinColumn(name = "projeto_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private List<Servico> servicos;

    @ManyToMany
    @JoinTable(
            name = "projeto_cliente",
            joinColumns = @JoinColumn(name = "projeto_id"),
            inverseJoinColumns = @JoinColumn(name = "cliente_id")
    )
    private List<Cliente> clientes;

}
