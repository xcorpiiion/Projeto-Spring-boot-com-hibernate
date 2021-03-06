package br.com.estudo.projetoweb.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import br.com.estudo.projetoweb.security.UserSpringSecurity;
import br.com.estudo.projetoweb.services.exception.AuthorizationException;
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
		emailService.sendOrderConfirmationHtmlEmail(pedido);
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

	/* O metodo abaixo serve para retornar uma consulta paginada */
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSpringSecurity userSpringSecurity = UserService.usuarioLogado();
		if (userSpringSecurity == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.findById(userSpringSecurity.getId());
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}

	public Pedido fromDTO(PedidoDTO pedidoDTO) {
		Cliente cliente = clienteService.findById(pedidoDTO.getIdCliente());
		Pagamento pagamento = pedidoDTO.getPagamento();
		List<Produto> produtos = pedidoDTO.getProdutos();
		Set<ItemPedido> itemPedidos = new HashSet<>();
		if (produtos == null) {
			itemPedidos = pedidoDTO.getItensPedido();
		} else {
			for (Produto produto : produtos) {
				itemPedidos.add(new ItemPedido(null, produto, null, null, pedidoDTO.getQuantidade()));
			}
		}
		return new Pedido(cliente, cliente.getEnderecos().get(0), pagamento, itemPedidos);
	}
}
