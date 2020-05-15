package br.com.estudo.projetoweb.domain.enums;

public enum EnumTipoCliente {

    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Jurídica");

    private int codigo;
    private String descricao;

    EnumTipoCliente(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public static EnumTipoCliente toEnum(Integer codigo){
        if(codigo == null) {
            return null;
        }
        for (EnumTipoCliente enumTipoCliente: EnumTipoCliente.values()) {
            if(codigo.equals(enumTipoCliente.getCodigo())) {
                return enumTipoCliente;
            }
        }
        throw new IllegalArgumentException(("Id inválido: " + codigo));
    }
    
}
