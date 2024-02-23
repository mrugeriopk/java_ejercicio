package com.login.model.input;

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
public class Usuario {
    @Id
    @Column(name="login")
    private String login ;
    private String password;
    private String nombre ;
    private String email;
    private float cliente=2;
    @Column(name="APELLIDO_PATERNO")
    private String apellidop;
    @Column(name="APELLIDO_MATERNO")
    private String apellidom;
}
