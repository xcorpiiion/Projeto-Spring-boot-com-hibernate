package br.com.estudo.projetoweb.domain;

import br.com.estudo.projetoweb.domain.enums.EnumEstadoPagamento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pagamento implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    private Long id;

    private Integer enumEstadoPagamento;

    @JsonBackReference
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumEstadoPagamento getEnumEstadoPagamento() {
        return EnumEstadoPagamento.toEnum(enumEstadoPagamento);
    }

    public void setEnumEstadoPagamento(EnumEstadoPagamento enumEstadoPagamento) {
        this.enumEstadoPagamento = enumEstadoPagamento.getCodigo();
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
