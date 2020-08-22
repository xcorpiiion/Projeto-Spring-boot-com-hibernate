package br.com.estudo.projetoweb.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.estudo.projetoweb.domain.enums.EnumEstadoPagamento;

@Entity
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instant;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_endereco")
    private Endereco enderecoEntrega;

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itensPedido;

    public Pedido() {
    }

    public Pedido(Date instant, Cliente cliente, Endereco enderecoEntrega) {
        this.instant = instant;
        this.cliente = cliente;
        this.enderecoEntrega = enderecoEntrega;
    }
    
    public Pedido(Cliente cliente, Endereco enderecoEntrega, Pagamento pagamento, Set<ItemPedido> itemPedidos) {
    	this.cliente = cliente;
    	this.enderecoEntrega = enderecoEntrega;
    	this.pagamento = pagamento;
    	this.itensPedido = itemPedidos;
    }
    
    public BigDecimal getValorTotal() {
    	BigDecimal soma = BigDecimal.ZERO;
    	for(ItemPedido itemPedido : this.itensPedido) {
    		soma = soma.add(itemPedido.getSubTotal());
    	}
    	return soma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInstant() {
        return instant;
    }

    public void setInstant(Date instant) {
        this.instant = instant;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Set<ItemPedido> getItensPedido() {
		return itensPedido;
	}

	public void setItensPedido(Set<ItemPedido> itensPedido) {
		this.itensPedido = itensPedido;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Pedido)) return false;

        Pedido pedido = (Pedido) o;

        return new EqualsBuilder()
                .append(id, pedido.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

	@Override
	public String toString() {
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		StringBuilder builder = new StringBuilder();
		builder.append("Pedido número: ");
		builder.append(getId());
		builder.append(", Instante: ");
		builder.append(simpleDateFormat.format(getInstant()));
		builder.append(", Cliente: ");
		builder.append(getCliente().getNome());
		builder.append(", Situação do pagamento: ");
		builder.append(EnumEstadoPagamento.toEnum(getPagamento().getEnumEstadoPagamento()));
		builder.append("\nDetalhes:\n ");
		for(ItemPedido itemPedido : getItensPedido()) {
			builder.append(itemPedido.toString());
		}
		builder.append(", Valor total: ");
		builder.append(numberFormat.format(getValorTotal()));
		builder.append("\n");
		return builder.toString();
	}
    
}
