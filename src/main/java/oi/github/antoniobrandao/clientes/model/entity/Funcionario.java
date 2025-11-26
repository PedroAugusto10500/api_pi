package oi.github.antoniobrandao.clientes.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.*
import jakarta.validation.constraints.*
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    @NotNull(message = "{campo.nome.obrigatorio}")
    @Size(min = 1, max = 150, message = "{campo.nome.tamanho}")
    @Pattern(regexp = "[a-zA-Z ]+", message = "{campo.nome.letras}")
    private String nome;

    @Column(nullable = false, length = 20)
    @NotNull(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    private String cpf;

    @Column(name = "data_admissao", updatable = false, columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAdmissao;

    @Column(nullable = false, length = 100)
    @NotNull(message = "{campo.cargo.obrigatorio}")
    @Size(min = 1, max = 100, message = "{campo.cargo.tamanho}")
    private String cargo;

    @Column(length = 255)
    private String habilidades;

    @Column(nullable = false, unique = true)
    @Email(message = "{campo.email.invalido}")
    private String email;

    @Column()
    @NotNull(message = "{campo.telefone.obrigatorio}")
    @Pattern(regexp = "^\\d+$", message = "{campo.telefone.numeros}")
    private String telefone;

    @PrePersist
    public void prePersist() {
        setDataAdmissao(LocalDate.now());
    }

    // Método para obter o ID do funcionário
    public Integer getId() {
        return id;
    }

}

