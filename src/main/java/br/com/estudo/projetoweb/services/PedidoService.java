package br.com.estudo.projetoweb.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estudo.projetoweb.domain.ItemPedido;
import br.com.estudo.projetoweb.domain.PagamentoBoleto;
import br.com.estudo.projetoweb.domain.Pedido;
import br.com.estudo.projetoweb.domain.Produto;
import br.com.estudo.projetoweb.domain.enums.EnumEstadoPagamento;
import br.com.estudo.projetoweb.repositories.ItemPedidoRepository;
import br.com.estudo.projetoweb.repositories.PagamentoRepository;
import br.com.estudo.projetoweb.repositories.PedidoRepository;
import br.com.estudo.projetoweb.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepositpry;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

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
		itemPedidoRepository.saveAll(pedido.getItemPedidos());
		return pedido;
	}

	private void colocaValoresNoItemPedido(Pedido pedido) {
		for (ItemPedido itemPedido : pedido.getItemPedidos()) {
			itemPedido.setDesconto(BigDecimal.ZERO);
			Produto produto = produtoService.find(itemPedido.getProduto().getId());
			itemPedido.setPreco(produto.getPreco());
			itemPedido.setPedido(pedido);
		}
	}
}
