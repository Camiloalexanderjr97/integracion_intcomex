package com.api.Integracion.Security_jwt.Usuario.Service;

import com.api.Integracion.Security_jwt.Usuario.Entity.Rol;
import com.api.Integracion.Security_jwt.Usuario.Login.RolNombre;
import com.api.Integracion.Security_jwt.Usuario.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService {
    
	@Autowired
	RolRepository rolRepository;
    // public abstract UsuarioModel getUsusarioUsername(String username);

   

    public Optional<Rol> getRolByName(RolNombre nombre){
    	return rolRepository.findByRolNombre(nombre);
    }
    
    public void save(Rol rol)
    {
    	rolRepository.save(rol);
    }
    
}
