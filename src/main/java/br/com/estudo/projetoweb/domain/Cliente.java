package br.com.estudo.projetoweb.domain;

import br.com.estudo.projetoweb.Endereco;
import br.com.estudo.projetoweb.domain.enums.EnumTipoCliente;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
public class Cliente implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String cpfOuCnpj;

    private Integer enumTipoCliente;

    @JsonManagedReference
    @OneToMany(mappedBy = "cliente")
    private List<Endereco> enderecos;

    @ElementCollection
    @CollectionTable(name = "Telefone")
    private Set<String> telefones;

    public Cliente() {
    }

    public Cliente(String nome, String email, String cpfOuCnpj, EnumTipoCliente enumTipoCliente, List<Endereco> enderecos, Set<String> telefones) {
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.enumTipoCliente = enumTipoCliente.getCodigo();
        this.enderecos = enderecos;
        this.telefones = telefones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public EnumTipoCliente getEnumTipoCliente() {
        return EnumTipoCliente.toEnum(enumTipoCliente);
    }

    public void setEnumTipoCliente(EnumTipoCliente enumTipoCliente) {
        this.enumTipoCliente = enumTipoCliente.getCodigo();
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Set<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<String> telefones) {
        this.telefones = telefones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Cliente)) return false;

        Cliente cliente = (Cliente) o;

        return new EqualsBuilder()
                .append(id, cliente.id)
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
                .append("nome", nome)
                .append("email", email)
                .append("cpfOuCnpj", cpfOuCnpj)
                .append("enumTipoCliente", enumTipoCliente)
                .append("enderecos", enderecos)
                .append("telefones", telefones)
                .toString();
    }
}
