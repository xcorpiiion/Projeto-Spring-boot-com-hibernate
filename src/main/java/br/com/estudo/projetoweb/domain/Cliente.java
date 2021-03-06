package br.com.estudo.projetoweb.domain;

import br.com.estudo.projetoweb.domain.enums.EnumPerfil;
import br.com.estudo.projetoweb.domain.enums.EnumTipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@JsonIgnoreType(value = true)
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    
    @Column(unique = true)
    private String email;
    
    @JsonIgnore
    private String senha;

    private String cpfOuCnpj;

    private Integer enumTipoCliente;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecos;

    @ElementCollection
    @CollectionTable(name = "Telefone")
    private Set<String> telefones;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Perfis")
    private Set<Integer> perfis;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    public Cliente() {
    	this.perfis = new HashSet<>();
    	addPerfis(EnumPerfil.CLIENTE);
    }

    public Cliente(Long id, String nome, String email) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.perfis = new HashSet<>();
		addPerfis(EnumPerfil.CLIENTE);
	}
    
	public Cliente(String nome, String email, String cpfOuCnpj, EnumTipoCliente enumTipoCliente) {
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.enumTipoCliente = enumTipoCliente.getCodigo();
		this.perfis = new HashSet<>();
		addPerfis(EnumPerfil.CLIENTE);
	}
	
	public Cliente(String nome, String email, String cpfOuCnpj, EnumTipoCliente enumTipoCliente, String senha) {
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.enumTipoCliente = enumTipoCliente.getCodigo();
		this.senha = senha;
		this.perfis = new HashSet<>();
		addPerfis(EnumPerfil.CLIENTE);
	}

	public Cliente(String nome, String email, String cpfOuCnpj, EnumTipoCliente enumTipoCliente, List<Endereco> enderecos, Set<String> telefones, List<Pedido> pedidos) {
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.enumTipoCliente = enumTipoCliente.getCodigo();
        this.enderecos = enderecos;
        this.telefones = telefones;
        this.pedidos = pedidos;
        this.perfis = new HashSet<>();
        addPerfis(EnumPerfil.CLIENTE);
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
    
    public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public Integer getEnumTipoCliente() {
		return enumTipoCliente;
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
    
    public Set<EnumPerfil> getPerfis() {
		return perfis.stream().map(perfil -> EnumPerfil.toEnum(perfil)).collect(Collectors.toSet());
	}

	public void addPerfis(EnumPerfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	public void setTelefones(Set<String> telefones) {
        this.telefones = telefones;
    }

    public void setEnumTipoCliente(Integer enumTipoCliente) {
        this.enumTipoCliente = enumTipoCliente;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
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
