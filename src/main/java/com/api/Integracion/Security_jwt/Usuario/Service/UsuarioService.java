package com.api.Integracion.Security_jwt.Usuario.Service;

import com.api.Integracion.Security_jwt.Usuario.Entity.Usuario;
import com.api.Integracion.Security_jwt.Usuario.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author camil
 *
 */
@Service
@Transactional
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	// public UsuarioModel getUsusarioUsername(String username);

	public List<Usuario> getUsuarios() {
		List<Usuario> listadoUser = usuarioRepository.findAll();

		return listadoUser;
	}

	public boolean deleteUsuario(Long id) {
		boolean resultado = false;
		try {
			usuarioRepository.deleteById(id);
			resultado = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultado;
	}

	public Usuario crearUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public boolean editarUsuario(Usuario user) {

		boolean resultado = false;
		if (usuarioRepository.updateUserSetStatusForNameNative(user.getName(), user.getPassword(), user.getUsername(),
				user.getId()) != 1) {
			resultado = true;
		}

		return resultado;
	}

	public void actualizarRol(Long id) {
		usuarioRepository.insert(id);

	}

	public boolean loadUserByUsername(String username) {

		boolean resultado = false;
		List<Usuario> user = usuarioRepository.findAll();
		for (Usuario lista : user) {
			if (lista.getUsername().equalsIgnoreCase(username)) {
				resultado = true;
			}
		}
		return resultado;
	}

	public boolean existByUsername(String nombre) {
		return usuarioRepository.existsByUsername(nombre);
	}

	public void save(Usuario user) {
		usuarioRepository.save(user);
	}

	public Usuario buscarById(Long id) {
		return usuarioRepository.findById(id).get();
	}

}
