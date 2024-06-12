package com.api.Integracion.Security_jwt.Usuario.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)                                                                                                                                     
    private Long id;

    @NotNull
    @Column(name = "nombre")
    private String name;

    @NotNull
    @Column(name = "username", unique = true)
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull 
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns= @JoinColumn(name= "usuario_id"),
    inverseJoinColumns = @JoinColumn(name ="rol_id"))
    private  Set<Rol> roles = new HashSet<>();


    
    
    


	public Set<Rol> getRoles() {
		return roles;
	}


	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}



    
	public Usuario(@NotNull String name, @NotNull String username, @NotNull String password) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
	}
	public Usuario(@NotNull Long id,@NotNull String name, @NotNull String username, @NotNull String password) {
		super();
		this.id=id;
		this.name = name;
		this.username = username;
		this.password = password;
	}


	public Usuario() {
    	
    }


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", roles="
				+ roles + "]";
	}
	
	
}