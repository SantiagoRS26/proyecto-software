package com.polizavehiculo.service;

import com.polizavehiculo.model.Cliente;

import java.util.List;

public interface ClienteService {
    public List<Cliente> getAllPersons();
    public Cliente getPersonById(String id);
    Cliente createPerson(Cliente cliente);
    Cliente updatePerson(String id, Cliente cliente);
    void deletePerson(String id);
}
