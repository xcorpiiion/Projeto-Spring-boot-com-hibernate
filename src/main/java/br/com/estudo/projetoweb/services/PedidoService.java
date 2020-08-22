package br.com.estudo.projetoweb.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estudo.projetoweb.domain.Cliente;
import br.com.estudo.projetoweb.domain.ItemPedido;
import br.com.estudo.projetoweb.domain.Pagamento;
import br.com.estudo.projetoweb.domain.PagamentoBoleto;
import br.com.estudo.projetoweb.domain.Pedido;
import br.com.estudo.projetoweb.domain.Produto;
import br.com.estudo.projetoweb.domain.enums.EnumEstadoPagamento;
import br.com.estudo.projetoweb.dto.PedidoDTO;
import br.com.estudo.projetoweb.repositories.ItemPedidoRepository;
import br.com.estudo.projetoweb.repositories.PagamentoRepository;
import br.com.estudo.projetoweb.repositories.PedidoRepository;
import br.com.estudo.projetoweb.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private PagamentoRepository pagamentoRepositpry;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido save(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
	
	public Pedido buscar(Long id) {
		Optional<Pedido> optionalPedido = pedidoRepository.findById(id);

		return optionalPedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato! ID: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstant(new Date());
		pedido.getPagamento().setEnumEstadoPagamento(EnumEstadoPagamento.PEDENTE.getCodigo());
		pedido.getPagamento().setPedido(pedido);
		if (pedido.getPagamento() instanceof PagamentoBoleto) {
			PagamentoBoleto boleto = (PagamentoBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoBoleto(boleto, pedido.getInstant());
		}
		pedido = pedidoRepository.save(pedido);
		pagamentoRepositpry.save(pedido.getPagamento());
		colocaValoresNoItemPedido(pedido);
		itemPedidoRepository.saveAll(pedido.getItensPedido());
		emailService.sendOrderConfirmationEmail(pedido);
		return pedido;
	}

	private void colocaValoresNoItemPedido(Pedido pedido) {
		for (ItemPedido itemPedido : pedido.getItensPedido()) {
			itemPedido.setDesconto(BigDecimal.ZERO);
			itemPedido.setProduto(produtoService.find(itemPedido.getProduto().getId()));
			itemPedido.setPreco(itemPedido.getProduto().getPreco());
			itemPedido.setPedido(pedido);
		}
	}
	
	public Pedido fromDTO(PedidoDTO pedidoDTO) {
		Cliente cliente = clienteService.findById(pedidoDTO.getIdCliente());
		Pagamento pagamento = pedidoDTO.getPagamento();
		Produto produto = pedidoDTO.getProduto();
		Set<ItemPedido> itemPedidos = new HashSet<>();
		itemPedidos.add(new ItemPedido(null, produto, null, null, pedidoDTO.getQuantidade()));
		return new Pedido(cliente, cliente.getEnderecos().get(0), pagamento, itemPedidos);
	}
}
