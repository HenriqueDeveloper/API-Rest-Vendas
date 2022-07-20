package br.com.henrique.service;

import br.com.henrique.domain.entity.Pedido;
import br.com.henrique.domain.enums.StatusPedido;
import br.com.henrique.rest.dto.PedidoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO pedido);
    @Query("select p from Pedido p left join fetch p.itens where p.id = :id")
    Optional<Pedido> obterPedidoCompleto(@Param("id") Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
