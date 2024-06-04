package com.polizavehiculo.service.imp;

import com.polizavehiculo.model.Cliente;
import com.polizavehiculo.model.PolizaRequest;
import com.polizavehiculo.repository.IPolizaRequestRepository;
import com.polizavehiculo.service.PolizaRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PolizaRequestServiceImpl implements PolizaRequestService {

    private final IPolizaRequestRepository PolizaRequestRepository;

    public List<PolizaRequest> getAllPolizaRequest() {
        return PolizaRequestRepository.findAll();
    }

    public void createPolizaRequest(PolizaRequest PolizaRequest) {
        PolizaRequestRepository.save(PolizaRequest);
    }

    public void updatePolizaRequest(Long id, PolizaRequest PolizaRequest) {
        if (PolizaRequestRepository.existsById(id)) {
            PolizaRequest.setCodRequest(id);
            PolizaRequestRepository.save(PolizaRequest);
        }
    }

    public void deletePolizaRequest(Long id) {
        PolizaRequestRepository.deleteById(id);
    }

    public List<PolizaRequest> findEnergyByPerson(Cliente cliente) {
        return PolizaRequestRepository.findByApplicant(cliente);
    }

    public PolizaRequest getPolizaRequestByProcessId(String processId) {
        return PolizaRequestRepository.findByProcessId(processId);
    }

    public PolizaRequest findByCodRequestIsNotNull() {
        return PolizaRequestRepository.findByCodRequestIsNotNull();
    }

    public PolizaRequest getPersonRequestByProcessId(String processId) {
        return PolizaRequestRepository.findByProcessId(processId);
    }

}
