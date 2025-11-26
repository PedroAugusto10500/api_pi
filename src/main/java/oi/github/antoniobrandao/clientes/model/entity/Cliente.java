vpackage oi.github.antoniobrandao.clientes.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    @NotNull(message = "{campo.nome.obrigatorio}")
    @Size(min = 1, max = 150, message = "{campo.nome.tamanho}")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "{campo.nome.letras}")
    private String nome;

    @Column(nullable = false, length = 20)
    @NotNull(message = "{campo.cpf.obrigatorio}")
    private String cpf; // REMOVA @CPF POR ENQUANTO PARA EVITAR ERRO 500

    @Column(name = "data_cadastro", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro;

    @Column(nullable = false, unique = true)
    @NotNull(message = "{campo.email.obrigatorio}")
    @Email(message = "{campo.email.invalido}")
    private String email;

    @Column(nullable = false, length = 20)
    @NotNull(message = "{campo.telefone.obrigatorio}")
    @Pattern(regexp = "^[0-9() \\-]+$", message = "{campo.telefone.numeros}")
    private String telefone;

    @PrePersist
    public void prePersist() {
        setDataCadastro(LocalDate.now());
    }
}
