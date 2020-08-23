package br.com.estudo.projetoweb.dto;

import java.io.Serializable;
import java.util.Set;

import br.com.estudo.projetoweb.domain.ItemPedido;
import br.com.estudo.projetoweb.domain.Pagamento;
import br.com.estudo.projetoweb.domain.Pedido;
import br.com.estudo.projetoweb.domain.Produto;

public class PedidoDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idCliente;
	
	private Pagamento pagamento;
	
	private Produto produto;
	
	private int quantidade;
	
	private Set<ItemPedido> itensPedido;
	
	public PedidoDTO() {
	}
	
	public PedidoDTO(Pedido pedido) {
		this.idCliente = pedido.getCliente().getId();
		this.pagamento = pedido.getPagamento();
		this.itensPedido = pedido.getItensPedido();
	}
	
	public PedidoDTO(Pedido pedido, Pagamento pagamento, Produto produto, int quantidade) {
		this.idCliente = pedido.getCliente().getId();
		this.pagamento = pagamento;
		this.produto = produto;
		this.quantidade = quantidade;
		this.itensPedido = pedido.getItensPedido();
	}

	public Set<ItemPedido> getItensPedido() {
		return itensPedido;
	}

	public void setItensPedido(Set<ItemPedido> itensPedido) {
		this.itensPedido = itensPedido;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	
}
