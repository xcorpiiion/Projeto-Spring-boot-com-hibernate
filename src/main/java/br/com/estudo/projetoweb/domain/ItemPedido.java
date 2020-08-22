package br.com.estudo.projetoweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
    @EmbeddedId
    private ItemPedidoPk id = new ItemPedidoPk();

    private BigDecimal desconto;

    private BigDecimal preco;
    
    private int quantidade;
    
    public ItemPedido() {
    }

    public ItemPedido(Pedido pedido, Produto produto, BigDecimal desconto, BigDecimal preco, int quantidade) {
        this.id.setPedido(pedido);
        this.id.setProduto(produto);
        this.desconto = desconto;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    @JsonIgnore
    public Pedido getPedido() {
        return id.getPedido();
    }
    
    public void setPedido(Pedido pedido) {
    	id.setPedido(pedido);
    }

    @JsonIgnore
    public Produto getProduto() {
        return id.getProduto();
    }
    
    public void setProduto(Produto produto) {
    	id.setProduto(produto);
    }
    
    public BigDecimal getSubTotal() {
    	BigDecimal subTotal = preco.subtract(desconto);
		return subTotal.multiply(BigDecimal.valueOf(quantidade));
    }
    
    public ItemPedidoPk getId() {
        return id;
    }

    public void setId(ItemPedidoPk id) {
        this.id = id;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
    
    public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ItemPedido that = (ItemPedido) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }
}
