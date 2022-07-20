package br.com.henrique.rest.controller;

import br.com.henrique.domain.entity.Usuario;
import br.com.henrique.domain.sucurity.jwt.JwtService;
import br.com.henrique.exception.SenhaInvalidaException;
import br.com.henrique.rest.dto.CredenciaisDTO;
import br.com.henrique.rest.dto.TokenDTO;
import br.com.henrique.service.imple.UserServiceImple;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UserServiceImple userServiceImple;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
     public Usuario salvar(@RequestBody @Valid Usuario usuario){
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return userServiceImple.salvar(usuario);

     }
     @PostMapping("/auth")
     public TokenDTO autentificar(@RequestBody CredenciaisDTO credenciais){
        try {
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = userServiceImple.autentificar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        }catch (UsernameNotFoundException  | SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
     }
}
