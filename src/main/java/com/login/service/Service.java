package com.login.service;

import com.login.model.input.Filtros;
import com.login.model.output.Usuarios;
import com.login.model.input.Credenciales;
import com.login.model.input.Usuario;
import com.login.utils.JWTUtil;
import com.login.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Repository
@Transactional
public class Service extends Utils implements Iservice {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    JWTUtil jwtUtil;
    @Override
    public ResponseEntity<?> listaUsuarios(String opcion) {
        TypedQuery<Usuarios> query= entityManager.createQuery("from com.login.model.output.Usuarios u where u.status=:status",
            Usuarios.class).setParameter("status",opcion);
        if(opcion.equals("T")){query= entityManager.createQuery("from com.login.model.output.Usuarios u ", Usuarios.class);}
        List<Usuarios> lista=query.getResultList();
        if(lista.isEmpty()){
            return new ResponseEntity<>(lista,HttpStatus.OK);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> altaUsuarios(Usuario usuario) {
        try {
            usuario.setPassword(encripta(usuario.getPassword()));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }
        entityManager.persist(usuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> bajaUsuarios(String id) {
        Usuarios usu =entityManager.find(Usuarios.class,id);
        if(usu!=null){
            String qr ="UPDATE USUARIO SET FECHABAJA=sysdate, NO_ACCESO=:acceso, STATUS=:status where LOGIN =:id ";
            Query query=entityManager.createNativeQuery(qr).setParameter("acceso",1)
                    .setParameter("status","B").setParameter("id",id);
            query.executeUpdate();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> login(Credenciales credenciales) {
        try {
            String desencripta =encripta(credenciales.getPass());
            TypedQuery<Usuario> qr = entityManager.
                createQuery("from com.login.model.input.Usuario u where u.login=:login " +
                    "and u.password=:password and no_acceso=0", Usuario.class)
                .setParameter("login",credenciales.getLogin())
                .setParameter("password",desencripta);
            List<Usuario> usu= qr.getResultList();
            if(!usu.isEmpty()){
                String token = jwtUtil.create(usu.get(0).getLogin(),usu.get(0).getEmail());
                return new ResponseEntity<>(token,HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }
        Usuarios usua =entityManager.find(Usuarios.class,credenciales.getLogin());
        if(usua!=null && usua.getLogin()!=null && usua.getStatus().equals("A")){
            float intentos =usua.getIntentos()+1;
            if(intentos>3){
                String quer ="UPDATE USUARIO SET FECHAREVOCADO=sysdate, NO_ACCESO=:acceso, STATUS=:status, INTENTOS=:intentos where LOGIN =:id ";
                Query query=entityManager.createNativeQuery(quer).setParameter("acceso",1)
                        .setParameter("status","R").setParameter("intentos",intentos)
                        .setParameter("id",credenciales.getLogin());
                query.executeUpdate();
            }else {
                String quer ="UPDATE USUARIO SET intentos=:intentos where LOGIN =:id ";
                Query query=entityManager.createNativeQuery(quer).setParameter("intentos",intentos)
                        .setParameter("id",credenciales.getLogin());
                query.executeUpdate();
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<?> actualizaDatos(Usuario usuario) {
        Usuarios usu =entityManager.find(Usuarios.class,usuario.getLogin());
        String pass="";
        if(usu!=null){
            try {
                pass=(encripta(usuario.getPassword()));
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
            }
            String qr ="UPDATE Usuario SET APELLIDO_MATERNO=:apellidom,APELLIDO_PATERNO=:apellidop," +
                "EMAIL=:email,PASSWORD=:password where LOGIN=:login";
            Query query=entityManager.createNativeQuery(qr).setParameter("login",usuario.getLogin())
                .setParameter("apellidom",usuario.getApellidom())
                .setParameter("apellidop",usuario.getApellidop())
                .setParameter("email",usuario.getEmail())
                .setParameter("password",pass);
            query.executeUpdate();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> buscaUsuario(String usuario) {
        Usuario usu =entityManager.find(Usuario.class,usuario);
        return new ResponseEntity<>(usu,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> buscaFiltro(Filtros filtros) {
        List<Usuarios> lista=new ArrayList<>();
        if(filtros.getNombre()!=null && !filtros.getNombre().isEmpty()){
            TypedQuery<Usuarios> query= entityManager.createQuery("from com.login.model.output.Usuarios u " +
                "where UPPER(u.nombre) like UPPER(:nombre)",Usuarios.class)
                .setParameter("nombre","%"+filtros.getNombre()+"%");
            lista=query.getResultList();
        }else {
            TypedQuery<Usuarios> query= entityManager.createQuery("from com.login.model.output.Usuarios u " +
                "where trunc(u.fechaalta) between  to_date(:faltaIni,'yyyy-mm-dd') and to_date(:faltaFin,'yyyy-mm-dd')",Usuarios.class)
                .setParameter("faltaIni",filtros.getFaltaIni())
                .setParameter("faltaFin",filtros.getFaltaFin());
            lista=query.getResultList();
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
