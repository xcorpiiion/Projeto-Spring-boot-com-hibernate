package br.com.estudo.projetoweb.domain.enums;

public enum EnumPerfil {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private int codigo;
    private String descricao;

    EnumPerfil(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EnumPerfil toEnum(Integer codigo){
        if(codigo == null) {
            return null;
        }
        for (EnumPerfil enumEstadoPagamento: EnumPerfil.values()) {
            if(codigo.equals(enumEstadoPagamento.getCodigo())) {
                return enumEstadoPagamento;
            }
        }
        throw new IllegalArgumentException("Id invalido: " + codigo);
    }

}
