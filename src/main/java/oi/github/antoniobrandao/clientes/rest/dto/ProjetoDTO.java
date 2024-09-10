package oi.github.antoniobrandao.clientes.rest.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Collections; // Importe a classe Collections

@Data
public class ProjetoDTO {
        private Integer id;
        private String descricao;
        private String data;
        private String dataFinal;
        private BigDecimal valor;
        private String situacao;
        private List<Integer> idServicos;
        private List<Integer> idClientes;

        // Método para garantir que idServicos nunca seja nulo
        public List<Integer> getIdServicos() {
                if (this.idServicos == null) {
                        return Collections.emptyList(); // Retorna uma lista vazia se idServicos for nulo
                }
                return this.idServicos;
        }

        // Método para garantir que idClientes nunca seja nulo
        public List<Integer> getIdClientes() {
                if (this.idClientes == null) {
                        return Collections.emptyList(); // Retorna uma lista vazia se idClientes for nulo
                }
                return this.idClientes;
        }
}