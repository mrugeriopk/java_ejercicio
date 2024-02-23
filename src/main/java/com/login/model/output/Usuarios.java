package com.login.model.output;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name="Usuario")
public class Usuarios {
    @Id
    @Column(name="login")
    private String login;
    private String password;
    private String nombre;
    private float cliente;
    private String email;
    private String fechaalta;
    private String fechabaja;
    private String status;
    private float intentos;
    private String fecharevocado;
    private String fecha_vigencia;
    private Integer no_acceso;
    @Column(name="APELLIDO_PATERNO")
    private String apellidop;
    @Column(name="APELLIDO_MATERNO")
    private String apellidom;
    private Integer area;
    private String fechamodificacion;
}
