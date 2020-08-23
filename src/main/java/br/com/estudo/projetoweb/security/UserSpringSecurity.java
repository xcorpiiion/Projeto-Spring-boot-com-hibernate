package br.com.estudo.projetoweb.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.estudo.projetoweb.domain.enums.EnumPerfil;

public class UserSpringSecurity implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String email;

	private String senha;

	/*Ele pega os perfis de autorização*/
	private Collection<? extends GrantedAuthority> autorizacoes;

	public UserSpringSecurity() {
	}

	public UserSpringSecurity(Long id, String email, String senha, Set<EnumPerfil> perfis) {
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.autorizacoes = perfis.stream().map(perfil -> new SimpleGrantedAuthority(perfil.getDescricao())).collect(Collectors.toList());
	}

	public Long getId() {
		return this.id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return autorizacoes;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public boolean hasPermissao(EnumPerfil enumPerfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(enumPerfil.getDescricao()));
	}

}
