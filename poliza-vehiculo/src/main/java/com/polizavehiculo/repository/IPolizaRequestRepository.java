package com.polizavehiculo.repository;

import com.polizavehiculo.model.Cliente;
import com.polizavehiculo.model.PolizaRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IPolizaRequestRepository extends JpaRepository<PolizaRequest, Long> {
    List<PolizaRequest> findByApplicant(Cliente applicant);
    PolizaRequest findByProcessId(String processId);
    PolizaRequest findByCodRequestIsNotNull();
}
