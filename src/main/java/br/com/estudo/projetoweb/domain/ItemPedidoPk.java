package br.com.estudo.projetoweb.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class ItemPedidoPk implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    public Pedido getPedido() {
        return pedido;
    }
    
    public void setPedido(Pedido pedido) {
    	this.pedido = pedido;
    }
    
    public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ItemPedidoPk that = (ItemPedidoPk) o;

        return new EqualsBuilder()
                .append(pedido, that.pedido)
                .append(produto, that.produto)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(pedido)
                .append(produto)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pedido", pedido)
                .append("produto", produto)
                .toString();
    }
}
