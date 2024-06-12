package com.api.Integracion.Security_jwt.Usuario.Entity;

import com.api.Integracion.Security_jwt.Usuario.Login.RolNombre;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class Rol {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rolNombre")
    private RolNombre rolNombre;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
	public RolNombre getRolNombre() { 
		return rolNombre;
	}
	public void setRolNombre(RolNombre rolNombre) {
		this.rolNombre = rolNombre;
	}
	public Rol(RolNombre rolNombre) {
		super();
		this.rolNombre = rolNombre;
	}
	public Rol() {
		super();
	}


    
}
