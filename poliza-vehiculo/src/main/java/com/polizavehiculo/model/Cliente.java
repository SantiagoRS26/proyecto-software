package com.polizavehiculo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "CLIENT")
@Data
public class Cliente {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "edadCliente")
    private Integer edadCliente;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdadCliente() {
        return edadCliente;
    }

    public void setEdadCliente(Integer edadCliente) {
        this.edadCliente = edadCliente;
    }
}
