package com.api.Integracion;

import com.api.Integracion.Controller_jwt.UsuarioController;
import com.api.Integracion.Security_jwt.Usuario.Entity.Rol;
import com.api.Integracion.Security_jwt.Usuario.Entity.Usuario;
import com.api.Integracion.Security_jwt.Usuario.Jwt.JwtProvider;
import com.api.Integracion.Security_jwt.Usuario.Login.RolNombre;
import com.api.Integracion.Security_jwt.Usuario.Service.RolService;
import com.api.Integracion.Security_jwt.Usuario.Service.UsuarioService;
import com.api.Integracion.Security_jwt.dto.JwtDto;
import com.api.Integracion.Security_jwt.dto.NuevoUsuario;
import com.api.Integracion.Security_jwt.dto.loginUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SecurityTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RolService rolService;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private UsuarioController usuarioController;

    private NuevoUsuario nuevoUsuario;
    private BindingResult bindingResult;
    private Usuario usuario;
    private Authentication auth;

    @BeforeEach
    void setUp() {
        nuevoUsuario = new NuevoUsuario();
        nuevoUsuario.setName("testName");
        nuevoUsuario.setUsername("testUsername");
        nuevoUsuario.setPassword("testPassword");
        nuevoUsuario.setRol("admin");

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setName("testName");
        usuario.setUsername("testUsername");
        usuario.setPassword("testPassword");

        bindingResult = mock(BindingResult.class);
        auth = mock(Authentication.class);
    }

    @Test
    void testNuevoUsuario() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(usuarioService.loadUserByUsername(nuevoUsuario.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(nuevoUsuario.getPassword())).thenReturn("encodedPassword");
        when(rolService.getRolByName(RolNombre.ROLE_USER)).thenReturn(Optional.of(new Rol(RolNombre.ROLE_USER)));
        when(rolService.getRolByName(RolNombre.ROLE_ADMIN)).thenReturn(Optional.of(new Rol(RolNombre.ROLE_ADMIN)));
        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<?> response = usuarioController.nuevo(nuevoUsuario, bindingResult);

        verify(usuarioService, times(1)).crearUsuario(any(Usuario.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testLogin() {
        loginUsuario login = new loginUsuario();
        login.setUsername("testUsername");
        login.setPassword("testPassword");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(mock(UserDetails.class));
        when(jwtProvider.generateToken(auth)).thenReturn("testToken");

        ResponseEntity<JwtDto> response = usuarioController.login(login, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);

        when(usuarioService.getUsuarios()).thenReturn(usuarios);

        ResponseEntity<List<Usuario>> response = usuarioController.getAllReHospitalario();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testAddUsuario() {
        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.addUsuario(usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuario.getId(), response.getBody().getId());
    }

    @Test
    void testBuscarById() {
        when(usuarioService.buscarById(usuario.getId())).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.buscarByID(usuario.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuario.getId(), response.getBody().getId());
    }

    @Test
    void testDeleteUsuario() {
        when(usuarioService.deleteUsuario(usuario.getId())).thenReturn(true);

        boolean resultado = usuarioController.deleteUsuario(usuario.getId());

        assertTrue(resultado);
        verify(usuarioService, times(1)).deleteUsuario(usuario.getId());
    }
}
