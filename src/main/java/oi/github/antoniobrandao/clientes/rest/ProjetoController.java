package oi.github.antoniobrandao.clientes.rest;
import java.util.List;
import java.util.stream.Collectors;

import oi.github.antoniobrandao.clientes.model.entity.Cliente;
import oi.github.antoniobrandao.clientes.model.entity.Projeto;
import oi.github.antoniobrandao.clientes.model.entity.Servico;
import oi.github.antoniobrandao.clientes.model.repository.ClienteRepository;
import oi.github.antoniobrandao.clientes.model.repository.ProjetoRepository;
import oi.github.antoniobrandao.clientes.model.repository.ServicoRepository;
import oi.github.antoniobrandao.clientes.rest.dto.ProjetoDTO;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/projeto")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8100","https://d194-186-215-67-187.ngrok-free.app", "https://apipi-production-9510.up.railway.app", "*"}, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ProjetoController {

    private final ProjetoRepository projetoRepository;
    private final ServicoRepository servicoRepository;
    private final ClienteRepository clienteRepository;

    public ProjetoController(ProjetoRepository projetoRepository, ServicoRepository servicoRepository, ClienteRepository clienteRepository) {
        this.projetoRepository = projetoRepository;
        this.servicoRepository = servicoRepository;
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjetoDTO salvar(@RequestBody ProjetoDTO dto) {
        LocalDate data;
        LocalDate dataFinal;
        try {
            data = LocalDate.parse(dto.getData());
            dataFinal = LocalDate.parse(dto.getDataFinal());
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de data inválido.");
        }

        List<Servico> servicos = dto.getIdServicos().stream()
                .map(servicoId -> servicoRepository.findById(servicoId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Serviço inexistente: " + servicoId)))
                .collect(Collectors.toList());

        List<Cliente> clientes = dto.getIdClientes().stream()
                .map(clienteId -> clienteRepository.findById(clienteId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente inexistente: " + clienteId)))
                .collect(Collectors.toList());

        Projeto projeto = new Projeto();
        projeto.setDescricao(dto.getDescricao());
        projeto.setData(data);
        projeto.setDataFinal(dataFinal);
        projeto.setValor(dto.getValor());
        projeto.setSituacao(dto.getSituacao());
        projeto.setServicos(servicos);
        projeto.setClientes(clientes);

        Projeto savedProjeto = projetoRepository.save(projeto);

        return toDto(savedProjeto);
    }

    @GetMapping
    public List<ProjetoDTO> listarTodos() {
        List<Projeto> projetos = projetoRepository.findAll();
        return projetos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProjetoDTO buscarPorId(@PathVariable Integer id) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado"));

        return toDto(projeto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        projetoRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ProjetoDTO atualizar(@PathVariable Integer id, @RequestBody ProjetoDTO dto) {
        // Busca o projeto pelo ID, se não encontrar, lança uma exceção
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado"));

        // Parse das datas
        LocalDate data;
        LocalDate dataFinal;
        try {
            data = LocalDate.parse(dto.getData());
            dataFinal = LocalDate.parse(dto.getDataFinal());
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de data inválido.");
        }

        // Mapeia os IDs dos serviços para entidades Servico
        List<Servico> servicos = dto.getIdServicos().stream()
                .map(servicoId -> servicoRepository.findById(servicoId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Serviço inexistente: " + servicoId)))
                .collect(Collectors.toList());

        // Mapeia os IDs dos clientes para entidades Cliente
        List<Cliente> clientes = dto.getIdClientes().stream()
                .map(clienteId -> clienteRepository.findById(clienteId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente inexistente: " + clienteId)))
                .collect(Collectors.toList());

        // Atualiza os atributos do projeto com base nos dados do DTO
        projeto.setDescricao(dto.getDescricao());
        projeto.setData(data);
        projeto.setDataFinal(dataFinal);
        projeto.setValor(dto.getValor());
        projeto.setSituacao(dto.getSituacao());
        projeto.setServicos(servicos);
        projeto.setClientes(clientes);

        // Salva o projeto atualizado no repositório
        Projeto updatedProjeto = projetoRepository.save(projeto);

        // Converte o projeto atualizado para DTO e retorna
        return toDto(updatedProjeto);
    }

    private ProjetoDTO toDto(Projeto projeto) {
        ProjetoDTO dto = new ProjetoDTO();
        dto.setId(projeto.getId());
        dto.setDescricao(projeto.getDescricao());
        dto.setData(projeto.getData().toString());
        dto.setDataFinal(projeto.getDataFinal().toString());
        dto.setIdServicos(projeto.getServicos().stream().map(Servico::getId).collect(Collectors.toList()));
        dto.setIdClientes(projeto.getClientes().stream().map(Cliente::getId).collect(Collectors.toList()));
        dto.setValor(projeto.getValor());
        dto.setSituacao(projeto.getSituacao().toString());
        return dto;
    }
}
