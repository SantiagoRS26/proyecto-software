package com.polizavehiculo.controller;

import com.polizavehiculo.dto.PolizaDTO;
import com.polizavehiculo.model.Cliente;
import com.polizavehiculo.model.PolizaRequest;
import com.polizavehiculo.service.imp.ClienteServiceImpl;
import com.polizavehiculo.service.imp.InsuranceAgent_RecDatSol;
import com.polizavehiculo.service.imp.PolizaRequestServiceImpl;
import com.polizavehiculo.util.RequestStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@RestController

public class PolizaController {
    private final PolizaRequestServiceImpl _polizaRequestService;
    private final ClienteServiceImpl _clienteService;
    private final InsuranceAgent_RecDatSol _insuranceAgent;

    public PolizaController(PolizaRequestServiceImpl clientePolizaService, ClienteServiceImpl clienteService, InsuranceAgent_RecDatSol insuranceAgent) {
        _polizaRequestService = clientePolizaService;
        _clienteService = clienteService;
        _insuranceAgent = insuranceAgent;
    }


}