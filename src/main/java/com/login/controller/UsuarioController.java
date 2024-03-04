package com.login.controller;

import com.login.model.input.Credenciales;
import com.login.model.input.Filtros;
import com.login.model.input.Usuario;
import com.login.service.Iservice;
import com.login.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {
    @Autowired
    private Iservice service;
    @Autowired
    JWTUtil jwtUtil;

    @GetMapping("/lista/{opcion}")
    public ResponseEntity<?> listaUsuarios(@RequestHeader(value = "Authorization") String token, @PathVariable String opcion){
        String tokenUser =jwtUtil.getKey(token);
        if(tokenUser == null){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return service.listaUsuarios(opcion);
    }

    @PostMapping("/alta")
    public ResponseEntity<?> altaUsuarios (@RequestBody Usuario usuario){
        return service.altaUsuarios(usuario);
    }

    @PatchMapping("/baja/{id}")
    public ResponseEntity<?> bajaUsuarios(@PathVariable String id){
        return service.bajaUsuarios(id);
    }

    @PostMapping("/actualiza")
    public ResponseEntity<?> actualiza(@RequestBody Usuario usu){return service.actualizaDatos(usu);}

    @GetMapping("/busca/{usuario}")
    public ResponseEntity<?> buscaUsuario(@PathVariable String usuario){
        return service.buscaUsuario(usuario);
    }

    @PostMapping("/filtros")
    public ResponseEntity<?> listaUsuarios(@RequestBody Filtros filtros){
        return service.buscaFiltro(filtros);
    }

}
