package br.com.estudo.projetoweb.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.com.estudo.projetoweb.domain.enums.EnumEstadoPagamento;

//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
@Entity
@Inheritance(strategy=InheritanceType.JOINED) 
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME,include= JsonTypeInfo.As.PROPERTY, property= "@type") 
@JsonSubTypes({@JsonSubTypes.Type(value = PagamentoBoleto.class, name = "pagamentoBoleto"),
    @JsonSubTypes.Type(value = PagamentoCartao.class, name = "pagamentoCartao")
})
/*A anotação acima fala que teremos um campo adicional no banco chamado @type*/
public abstract class Pagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    private Long id;

    private Integer enumEstadoPagamento;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_pedido")
    @MapsId
    private Pedido pedido;

    public Pagamento() {
    }

    public Pagamento(EnumEstadoPagamento enumEstadoPagamento, Pedido pedido) {
        this.enumEstadoPagamento = enumEstadoPagamento.getCodigo();
        this.pedido = pedido;
    }
    
    public Pagamento(Pedido pedido) {
    	this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEnumEstadoPagamento() {
		return enumEstadoPagamento;
	}

	public void setEnumEstadoPagamento(Integer enumEstadoPagamento) {
		this.enumEstadoPagamento = enumEstadoPagamento;
	}

	public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Pagamento)) return false;

        Pagamento pagamento = (Pagamento) o;

        return new EqualsBuilder()
                .append(id, pagamento.id)
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
        return new ToStringBuilder(this)
                .append("id", id)
                .append("enumEstadoPagamento", enumEstadoPagamento)
                .append("pedido", pedido)
                .toString();
    }
}
