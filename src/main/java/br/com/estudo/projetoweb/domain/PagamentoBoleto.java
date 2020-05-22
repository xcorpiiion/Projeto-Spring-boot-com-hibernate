package br.com.estudo.projetoweb.domain;

import br.com.estudo.projetoweb.domain.Pagamento;
import br.com.estudo.projetoweb.domain.enums.EnumEstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class PagamentoBoleto extends Pagamento {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataVencimento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataPagamento;

    public PagamentoBoleto() {
    }

    public PagamentoBoleto(EnumEstadoPagamento enumEstadoPagamento, Pedido pedido, Date dataVencimento, Date dataPagamento) {
        super(enumEstadoPagamento, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PagamentoBoleto)) return false;

        PagamentoBoleto that = (PagamentoBoleto) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(dataVencimento, that.dataVencimento)
                .append(dataPagamento, that.dataPagamento)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(dataVencimento)
                .append(dataPagamento)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dataVencimento", dataVencimento)
                .append("dataPagamento", dataPagamento)
                .toString();
    }
}
