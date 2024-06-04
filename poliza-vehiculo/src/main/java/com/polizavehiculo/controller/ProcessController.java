package com.polizavehiculo.controller;

import com.polizavehiculo.dto.PolizaDTO;
import com.polizavehiculo.dto.TaskInfo;
import com.polizavehiculo.model.Cliente;
import com.polizavehiculo.model.PolizaRequest;
import com.polizavehiculo.service.imp.ClienteServiceImpl;
import com.polizavehiculo.service.imp.InsuranceAgent_RecDatSol;
import com.polizavehiculo.service.imp.PolizaRequestServiceImpl;
import com.polizavehiculo.util.RequestStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.List;

@Controller
public class ProcessController {
    private final InsuranceAgent_RecDatSol _insuranceAgent;
    private final ClienteServiceImpl _clienteService;
    private final PolizaRequestServiceImpl _polizaRequestService;

    public ProcessController(InsuranceAgent_RecDatSol insuranceAgent, ClienteServiceImpl clienteService, PolizaRequestServiceImpl polizaRequestService) {
        _insuranceAgent = insuranceAgent;
        _clienteService = clienteService;
        _polizaRequestService = polizaRequestService;
    }

    @PostMapping("/startProcess")
    public RedirectView startProcessInstance(@ModelAttribute PolizaDTO polizaDTO, RedirectAttributes redirectAttributes) {
        Cliente cliente = polizaDTO.getCliente();

        if (cliente == null) {
            throw new RuntimeException("Cliente nulo");
        }

        _clienteService.createPerson(cliente);
        PolizaRequest polizaRequest = new PolizaRequest();
        polizaRequest.setCodRequest(polizaDTO.getCodRequest());
        polizaRequest.setProcessId(polizaDTO.getProcessId());
        polizaRequest.setStatus(RequestStatus.DRAFT.toString());
        polizaRequest.setMarcaVehiculo(polizaDTO.getMarcaVehiculo());
        polizaRequest.setReferenciaVehiculo(polizaDTO.getReferenciaVehiculo());
        polizaRequest.setCountReviewCR(0L);
        polizaRequest.setApplicant(cliente);
        _polizaRequestService.createPolizaRequest(polizaRequest);
        polizaDTO.setApplicantClienteId(cliente.getId());
        redirectAttributes.addAttribute("id", cliente.getId());

        List<PolizaRequest> updatePoliza = _polizaRequestService.findEnergyByPerson(cliente);
        polizaDTO.setCodRequest(updatePoliza.get(0).getCodRequest());
        String processId = _insuranceAgent.startProcessInstance(polizaDTO);

        for (PolizaRequest request : updatePoliza) {
            if ("DRAFT".equals(request.getStatus())) {
                request.setProcessId(processId);
                _polizaRequestService.updatePolizaRequest(request.getCodRequest(), request);
            }
        }
        return new RedirectView("/verPolizas");
    }

    @GetMapping("/complete")
    public RedirectView completeTask(@RequestParam(name = "taskId") String processId) {
        _insuranceAgent.completeTask(processId);
        return new RedirectView("/");
    }

}
