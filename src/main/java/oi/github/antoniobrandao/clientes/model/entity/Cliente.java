package oi.github.antoniobrandao.clientes.model.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import javax.validation.constraints.Size;
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
    @Pattern(regexp = "[a-zA-Z]+", message = "{campo.nome.letras}")
    private String nome;

    @Column(nullable = false, length = 20)
    @NotNull(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    private String cpf;


    @Column(name = "data_cadastro" , updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro;

    @Column(nullable = false, unique = true) // Supondo que o email seja único
    @Email(message = "{campo.email.invalido}")
    private String email;

    @Column(nullable = false, length = 15)
    @NotNull(message = "{campo.telefone.obrigatorio}")
    @Pattern(regexp = "^\\d+$", message = "{campo.telefone.numeros}")
    private String telefone;



    @PrePersist
    public void prePersist(){
        setDataCadastro(LocalDate.now());
    }

}
