package com.polizavehiculo.service.imp;

import com.polizavehiculo.model.Cliente;
import com.polizavehiculo.repository.IClienteRepository;
import com.polizavehiculo.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    private final IClienteRepository clienteRepository;

    public List<Cliente> getAllPersons() {
        return clienteRepository.findAll();
    }

    public Cliente getPersonById(String id) {
        Optional<Cliente> optionalPerson = clienteRepository.findById(id);
        return optionalPerson.orElse(null);
    }

    public Cliente createPerson(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente updatePerson(String id, Cliente person) {
        if (clienteRepository.existsById(id)) {
            person.setId(id);
            return clienteRepository.save(person);
        }
        return null;
    }

    public void deletePerson(String id) {
        clienteRepository.deleteById(id);
    }
}