package com.api.Integracion.Security_jwt.Usuario.Repository;

import com.api.Integracion.Security_jwt.Usuario.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	
	 Optional<Usuario> findByUsername(String nombre);
	
	  boolean existsByUsername(String nombre);
	
// 	  @Modifying
// 	  @Query(value = "  UPDATE usuario SET usuario.nombre = :name , usuario.password = :password, usuario.username =  :username WHERE usuario.id = :id",
// //			  update Users u set u.status = ? where u.name = )
// 	    nativeQuery = true)
// 	  int updateUserSetStatusForNameNative(String nombre, String password, String username, Long id);


    @Modifying
    @Query("UPDATE Usuario u SET u.name = :name, u.password = :password, u.username = :username WHERE u.id = :id")
    int updateUserSetStatusForNameNative(@Param("name") String name, @Param("password") String password, @Param("username") String username, @Param("id") Long id);

	  
// 	  @Modifying
// 	  @Query(value = "  INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (:idUser, '1')",
// //			  update Users u set u.status = ? where u.name = )
// 	    nativeQuery = true)
// 	  int insert(Long idUser);

	  @Modifying
	  @Query(value = "INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (idUser, '1')", nativeQuery = true)
	  int insert(@Param("idUser") Long idUser);
  
	  
	  
}
