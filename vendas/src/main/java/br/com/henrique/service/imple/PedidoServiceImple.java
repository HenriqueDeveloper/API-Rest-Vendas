package br.com.henrique.service.imple;

import br.com.henrique.domain.entity.Cliente;
import br.com.henrique.domain.entity.ItemPedido;
import br.com.henrique.domain.entity.Pedido;
import br.com.henrique.domain.entity.Produto;
import br.com.henrique.domain.enums.StatusPedido;
import br.com.henrique.exception.PedidoNaoEncontradoException;
import br.com.henrique.exception.RegraNegocioException;
import br.com.henrique.domain.repository.ClientesRepository;
import br.com.henrique.domain.repository.ItemPedidos;
import br.com.henrique.domain.repository.PedidosRepository;
import br.com.henrique.domain.repository.ProdutosRepository;
import br.com.henrique.rest.dto.ItemPedidoDTO;
import br.com.henrique.rest.dto.PedidoDTO;
import br.com.henrique.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImple implements PedidoService {

    @Autowired
    private PedidosRepository pedidosRepository;
    @Autowired
    private ClientesRepository clientesRepository;
    @Autowired
    private ProdutosRepository produtosRepository;
    @Autowired
    private ItemPedidos itemPedidosRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));
        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);
       List<ItemPedido> itemPedidos = converterItens(pedido, pedidoDTO.getItens());
       pedidosRepository.save(pedido);
       pedido.setItens(itemPedidos);
       itemPedidosRepository.saveAll(itemPedidos);
        return pedido;

    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidosRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        this.pedidosRepository
                .findById(id)
                .map(pedido -> {
            pedido.setStatus(statusPedido);
           return pedidosRepository.save(pedido);
        }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens){
        if(itens.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens");
        }
        return itens.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto =produtosRepository.findById(idProduto).orElseThrow(() ->  new RuntimeException("Código de produto inválido: " + idProduto));
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());
    }
}
