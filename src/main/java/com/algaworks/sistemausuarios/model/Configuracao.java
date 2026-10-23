package com.algaworks.sistemausuarios.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Configuracao {

    @Id
    private Integer id;

    @OneToOne
    private Usuario usuario;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
}
