package com.api.Integracion.Security_jwt.Usuario.Repository;

import com.api.Integracion.Security_jwt.Usuario.Entity.Rol;
import com.api.Integracion.Security_jwt.Usuario.Login.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("rolRepository")
public interface RolRepository extends JpaRepository<Rol, Long> {

	Optional<Rol> findByRolNombre(RolNombre rolNombre);

	// @Query(value = "SELECT new com.Model.RolModel (r.id AS id, "
	// 		+ " r.name AS name ) "
	// 		+ " FROM Rol r INNER JOIN Usuario u ON   r.id = u.rol.id")
	// List<RolModel> getAllRol();
}
