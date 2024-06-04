package com.polizavehiculo.service;

import com.polizavehiculo.model.Cliente;
import com.polizavehiculo.model.PolizaRequest;

import java.util.List;

public interface PolizaRequestService {
    List<PolizaRequest> getAllPolizaRequest();
    void createPolizaRequest(PolizaRequest PolizaRequest);
    void updatePolizaRequest(Long id, PolizaRequest PolizaRequest);
    void deletePolizaRequest(Long id);
    List<PolizaRequest> findEnergyByPerson(Cliente cliente);
    PolizaRequest getPersonRequestByProcessId(String processId);
   // PolizaRequest findFirstByOrderByRequestDateDesc();
}
