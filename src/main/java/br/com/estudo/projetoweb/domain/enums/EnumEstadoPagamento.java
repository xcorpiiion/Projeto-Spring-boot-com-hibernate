package br.com.estudo.projetoweb.domain.enums;

public enum EnumEstadoPagamento {

    PEDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private int codigo;
    private String descricao;

    EnumEstadoPagamento(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EnumEstadoPagamento toEnum(Integer codigo){
        if(codigo == null) {
            return null;
        }
        for (EnumEstadoPagamento enumEstadoPagamento: EnumEstadoPagamento.values()) {
            if(codigo.equals(enumEstadoPagamento.getCodigo())) {
                return enumEstadoPagamento;
            }
        }
        throw new IllegalArgumentException("Id invalido: " + codigo);
    }

}
