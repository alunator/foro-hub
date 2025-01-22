package com.ForoHub.Controller;

import com.ForoHub.Entity.Usuario;
import com.ForoHub.JWT.JwtUtil;
import com.ForoHub.Repository.UsuarioRepository;
import com.ForoHub.Request.AuthResponse;
import com.ForoHub.Request.LoginRequest;
import com.ForoHub.Request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public AuthResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getCorreoElectronico(),
                        loginRequest.getContrasena()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);
        return new AuthResponse(jwt);
    }

    @PostMapping("/register")
    public Usuario registerUser(@RequestBody RegisterRequest registerRequest) {
        Usuario usuario = new Usuario();
        usuario.setNombre(registerRequest.getNombre());
        usuario.setCorreoElectronico(registerRequest.getCorreoElectronico());
        usuario.setContrasena(passwordEncoder.encode(registerRequest.getContrasena()));
        return usuarioRepository.save(usuario);
    }
}
