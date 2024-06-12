package com.api.Integracion.Security_jwt.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtDto {

	
	private String token;
	private String bearer ="Bearer";
	
	private String nombre;
	private Collection<? extends GrantedAuthority> authorities;
	
	public JwtDto(String token, String nombre, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.token = token;
		this.nombre = nombre;
		this.authorities = authorities;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getBearer() {
		return bearer;
	}
	public void setBearer(String bearer) {
		this.bearer = bearer;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	
}
