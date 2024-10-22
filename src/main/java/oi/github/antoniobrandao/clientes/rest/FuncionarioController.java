package oi.github.antoniobrandao.clientes.rest;
import oi.github.antoniobrandao.clientes.model.entity.Funcionario;
import oi.github.antoniobrandao.clientes.model.entity.Funcionario;
import oi.github.antoniobrandao.clientes.model.repository.FuncionarioRepository;
import oi.github.antoniobrandao.clientes.util.BigDecimalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8100", "https://apipi-production-9510.up.railway.app","https://d194-186-215-67-187.ngrok-free.app", "*"}, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class FuncionarioController {



    private final FuncionarioRepository repository;

    @Autowired
    public FuncionarioController(FuncionarioRepository repository) {
        this.repository = repository;

    }

    @GetMapping
    public List<Funcionario> obterTodos() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Funcionario salvar(@RequestBody @Valid Funcionario funcionario) {
        if (repository.existsByCpf(funcionario.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já cadastrado");
        }
        // Conversão do valor para BigDecimal


        return repository.save(funcionario);
    }

    @GetMapping("/verificar-cpf/{cpf}")
    public boolean verificarCPFCadastrado(@PathVariable String cpf) {
        return repository.existsByCpf(cpf);
    }

    @GetMapping("{id}")
    public Funcionario acharPorId(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        repository.findById(id)
                .map(funcionario -> {
                    repository.delete(funcionario);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id, @RequestBody @Valid Funcionario funcionarioAtualizado) {
        repository.findById(id)
                .map(funcionario -> {
                    funcionario.setNome(funcionarioAtualizado.getNome());
                    funcionario.setCargo(funcionarioAtualizado.getCargo());
                    // Conversão do valor para BigDecimal

                    funcionario.setHabilidades(funcionarioAtualizado.getHabilidades());
                    funcionario.setEmail(funcionarioAtualizado.getEmail());
                    funcionario.setTelefone(funcionarioAtualizado.getTelefone());
                    return repository.save(funcionario);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
