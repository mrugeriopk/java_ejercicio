package com.login.controller;

import com.login.model.input.Credenciales;
import com.login.model.input.Filtros;
import com.login.model.input.Usuario;
import com.login.service.Iservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {
    @Autowired
    private Iservice service;

    @GetMapping("/lista/{opcion}")
    public ResponseEntity<?> listaUsuarios(@PathVariable String opcion){
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credenciales cre){
        return service.login(cre);
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
