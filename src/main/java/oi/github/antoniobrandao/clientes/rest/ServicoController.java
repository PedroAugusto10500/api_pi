package oi.github.antoniobrandao.clientes.rest;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import oi.github.antoniobrandao.clientes.model.entity.Funcionario;
import oi.github.antoniobrandao.clientes.model.entity.Servico;
import oi.github.antoniobrandao.clientes.model.repository.FuncionarioRepository;
import oi.github.antoniobrandao.clientes.model.repository.ServicoRepository;
import oi.github.antoniobrandao.clientes.rest.dto.FuncionarioDTO;
import oi.github.antoniobrandao.clientes.rest.dto.ServicoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;


@RestController
@RequestMapping("/api/servico")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8100","https://d194-186-215-67-187.ngrok-free.app", "https://apipi-production-9510.up.railway.app", "*"}, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ServicoController {

    private final FuncionarioRepository funcionarioRepository;
    private final ServicoRepository repository;

    @Autowired
    public ServicoController(ServicoRepository repository, FuncionarioRepository funcionarioRepository) {
        this.repository = repository;
        this.funcionarioRepository = funcionarioRepository;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoDTO salvar(@RequestBody ServicoDTO dto) {
        System.out.println("PEDROPEDROPEDRO --------------------------------------------------- ");
        System.out.println(dto);
        LocalDate data;
        LocalDate dataFinal;
        try {
            data = LocalDate.parse(dto.getData());
            dataFinal = LocalDate.parse(dto.getDataFinal());
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de data inválido.");
        }

        List<Funcionario> funcionarios = dto.getIdFuncionarios().stream()
                .map(id -> funcionarioRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Funcionario inexistente: " + id)))
                .collect(Collectors.toList());

        // Verifica se há funcionários selecionados
        if (funcionarios.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pelo menos um funcionário deve ser selecionado.");
        }


        // Imprime os dados antes de salvar o serviço
        System.out.println("Dados do serviço a serem salvos:");
        System.out.println("ID: " + dto.getId());
        System.out.println("Descrição: " + dto.getDescricao());
        System.out.println("Data: " + dto.getData());
        System.out.println("Data Final: " + dto.getDataFinal());
        System.out.println("Tipo de Serviço: " + dto.getTipoServico());
        System.out.println("Situação: " + dto.getSituacao());
        System.out.println("Funcionários:");
        dto.getIdFuncionarios().forEach(id -> System.out.println("- ID do Funcionário: " + id));
        // Cria um novo objeto Servico e define seus atributos
        Servico servico = new Servico();
        servico.setId(dto.getId());
        servico.setDescricao(dto.getDescricao());
        servico.setData(data);
        servico.setDataFinal(dataFinal);
        servico.setTipoServico(dto.getTipoServico());
        servico.setSituacao(dto.getSituacao());
        servico.setFuncionarios(funcionarios); // Define os funcionários associados ao serviço

        // Salva o serviço no banco de dados
        Servico savedServico = repository.save(servico);

        // Converte o serviço salvo de volta para o formato DTO e retorna
        ServicoDTO servicoDTO = new ServicoDTO();
        servicoDTO.setId(savedServico.getId());
        servicoDTO.setDescricao(savedServico.getDescricao());
        servicoDTO.setData(savedServico.getData().toString());
        servicoDTO.setDataFinal(savedServico.getDataFinal().toString());
        servicoDTO.setIdFuncionarios(savedServico.getFuncionarios().stream().map(Funcionario::getId).collect(Collectors.toList()));
        servicoDTO.setTipoServico(savedServico.getTipoServico());
        servicoDTO.setSituacao(savedServico.getSituacao().toString());

        return servicoDTO;
    }

    @GetMapping
    public List<ServicoDTO> pesquisar(
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "mes", required = false) Integer mes
    ) {
        List<Servico> servicos = repository.findAll();

        return servicos.stream().map(servico -> {
            ServicoDTO dto = new ServicoDTO();
            dto.setId(servico.getId());
            dto.setDescricao(servico.getDescricao());
            dto.setData(servico.getData().toString());
            dto.setDataFinal(servico.getDataFinal().toString());
            dto.setIdFuncionarios(servico.getFuncionarios().stream().map(Funcionario::getId).collect(Collectors.toList()));
            dto.setTipoServico(servico.getTipoServico());
            dto.setSituacao(servico.getSituacao().toString());

            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ServicoDTO acharPorId(@PathVariable Integer id) {
        Servico servico = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ServicoDTO servicoDTO = new ServicoDTO();
        servicoDTO.setId(servico.getId());
        servicoDTO.setDescricao(servico.getDescricao());
        servicoDTO.setData(servico.getData().toString());
        servicoDTO.setDataFinal(servico.getDataFinal().toString());
        servicoDTO.setIdFuncionarios(servico.getFuncionarios().stream().map(Funcionario::getId).collect(Collectors.toList()));
        servicoDTO.setTipoServico(servico.getTipoServico());
        servicoDTO.setSituacao(servico.getSituacao().toString());

        return servicoDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoDTO atualizar(@PathVariable Integer id, @RequestBody ServicoDTO dto) {
        LocalDate data;
        LocalDate dataFinal;
        try {
            data = LocalDate.parse(dto.getData());
            dataFinal = LocalDate.parse(dto.getDataFinal());
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de data inválido.");
        }

        Servico servico = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado."));

        List<Funcionario> funcionarios = dto.getIdFuncionarios().stream()
                .map(funcId -> funcionarioRepository.findById(funcId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Funcionario inexistente: " + funcId)))
                .collect(Collectors.toList());

        servico.setDescricao(dto.getDescricao());
        servico.setData(data);
        servico.setDataFinal(dataFinal);
        servico.setTipoServico(dto.getTipoServico());
        servico.setSituacao(dto.getSituacao());
        servico.setFuncionarios(funcionarios);

        Servico savedServico = repository.save(servico);

        ServicoDTO servicoDTO = new ServicoDTO();
        servicoDTO.setId(savedServico.getId());
        servicoDTO.setDescricao(savedServico.getDescricao());
        servicoDTO.setData(savedServico.getData().toString());
        servicoDTO.setDataFinal(savedServico.getDataFinal().toString());
        servicoDTO.setIdFuncionarios(savedServico.getFuncionarios().stream().map(Funcionario::getId).collect(Collectors.toList()));
        servicoDTO.setTipoServico(savedServico.getTipoServico());
        servicoDTO.setSituacao(savedServico.getSituacao().toString());

        return servicoDTO;
    }
    @GetMapping("/{id}/funcionarios")
    public List<FuncionarioDTO> buscarFuncionariosPorServico(@PathVariable Integer id) {
        Servico servico = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado."));

        return servico.getFuncionarios().stream()
                .map(funcionario -> {
                    FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
                    funcionarioDTO.setId(funcionario.getId());
                    funcionarioDTO.setNome(funcionario.getNome());
                    funcionarioDTO.setCpf(funcionario.getCpf());
                    funcionarioDTO.setDataAdmissao(funcionario.getDataAdmissao());
                    funcionarioDTO.setCargo(funcionario.getCargo());
                    funcionarioDTO.setHabilidades(funcionario.getHabilidades());
                    funcionarioDTO.setEmail(funcionario.getEmail());
                    funcionarioDTO.setTelefone(funcionario.getTelefone());
                    // Adicione outros atributos do FuncionarioDTO, se necessário
                    return funcionarioDTO;
                })
                .collect(Collectors.toList());
    }


    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        repository.findById(id)
                .map(servico -> {
                    repository.delete(servico);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/count")
    public long contarServicos() {
        return repository.countAllServicos();
    }

    @GetMapping("/countByTipo")
    public List<ServicoCountDTO> contarServicosPorTipo() {
        List<Object[]> counts = repository.countServicosByTipoServico();
        return counts.stream()
                .map(count -> new ServicoCountDTO((String) count[0], (Long) count[1]))
                .collect(Collectors.toList());
    }
}

