package com.login.service;

import com.login.model.input.Credenciales;
import com.login.model.input.Filtros;
import com.login.model.input.Usuario;
import org.springframework.http.ResponseEntity;

public interface Iservice {
    ResponseEntity<?> listaUsuarios(String opcion);
    ResponseEntity<?> altaUsuarios(Usuario usuario);
    ResponseEntity<?> bajaUsuarios(String usuario);
    ResponseEntity<?> login(Credenciales credenciales);
    ResponseEntity<?> actualizaDatos(Usuario usu);
    ResponseEntity<?> buscaUsuario(String usuario);
    ResponseEntity<?> buscaFiltro(Filtros filtros);

}
