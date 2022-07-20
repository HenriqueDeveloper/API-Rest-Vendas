package br.com.henrique.rest.controller;

import br.com.henrique.rest.dto.InformacoesPedidoDTO;
import br.com.henrique.rest.dto.PedidoDTO;
import br.com.henrique.domain.entity.ItemPedido;
import br.com.henrique.domain.entity.Pedido;
import br.com.henrique.domain.enums.StatusPedido;
import br.com.henrique.rest.dto.AtualizacaoStatusPedidoDTO;
import br.com.henrique.rest.dto.InformacaoItemPedidoDTO;
import br.com.henrique.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = pedidoService.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("/{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return pedidoService.obterPedidoCompleto(id)
                .map(pedido -> converter(pedido))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

    }

    private InformacoesPedidoDTO converter(Pedido pedido){
       return InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens(converter(pedido.getItens()))
                .build();

    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
        if (CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map(
                item ->InformacaoItemPedidoDTO
                        .builder().descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()

        ).collect(Collectors.toList());
    }
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getStatus();
        this.pedidoService.atualizaStatus(id, StatusPedido.valueOf(novoStatus));

    }

}
