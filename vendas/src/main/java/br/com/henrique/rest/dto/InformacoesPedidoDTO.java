package br.com.henrique.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesPedidoDTO {

    private String status;
    private Integer codigo;
    private String cpf;
    private String dataPedido;
    private String nomeCliente;
    private BigDecimal total;
    private List<InformacaoItemPedidoDTO> itens;
}
