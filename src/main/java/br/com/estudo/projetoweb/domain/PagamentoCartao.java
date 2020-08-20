package br.com.estudo.projetoweb.domain;

import br.com.estudo.projetoweb.domain.enums.EnumEstadoPagamento;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;

@Entity
public class PagamentoCartao extends Pagamento {

	private static final long serialVersionUID = 1L;
	
	private Integer numeroParcelas;

    public PagamentoCartao() {
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public PagamentoCartao(EnumEstadoPagamento enumEstadoPagamento, Pedido pedido, Integer numeroParcelas) {
        super(enumEstadoPagamento, pedido);
        this.numeroParcelas = numeroParcelas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PagamentoCartao)) return false;

        PagamentoCartao that = (PagamentoCartao) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(numeroParcelas, that.numeroParcelas)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(numeroParcelas)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("numeroParcelas", numeroParcelas)
                .toString();
    }
}
