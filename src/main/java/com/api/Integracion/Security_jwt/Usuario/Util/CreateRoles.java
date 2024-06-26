package com.api.Integracion.Security_jwt.Usuario.Util;

import com.api.Integracion.Security_jwt.Usuario.Entity.Rol;
import com.api.Integracion.Security_jwt.Usuario.Login.RolNombre;
import com.api.Integracion.Security_jwt.Usuario.Service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class CreateRoles implements CommandLineRunner {

	@Autowired
	RolService rolService;
	
	@Override
	public void run(String... args) throws Exception {
		Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
		Rol rolUser = new Rol(RolNombre.ROLE_USER);
	rolService.save(rolAdmin);
	rolService.save(rolUser);
		// TODO Auto-generated method stub
		
	}
	

}
