package com.polizavehiculo.controller;

import com.polizavehiculo.dto.ClienteDTO;
import com.polizavehiculo.dto.PolizaDTO;
import com.polizavehiculo.model.Cliente;
import com.polizavehiculo.model.PolizaRequest;
import com.polizavehiculo.service.imp.ClienteServiceImpl;
import com.polizavehiculo.service.imp.PolizaRequestServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PolizaRequestServiceImpl _polizaRequestService;
    private final ClienteServiceImpl _clienteService;

    @GetMapping("/")
    public String defaultView(Model model) {
        return "Index";
    }

    @GetMapping("/formularioPoliza")
    public String formularioPoliza(Model model) {
        ClienteDTO clienteDTO = new ClienteDTO();
        PolizaDTO polizaDTO = new PolizaDTO();
        model.addAttribute("cliente", clienteDTO);
        model.addAttribute("poliza", polizaDTO);
        return "informacionCliente";
    }

    @GetMapping("/verPolizas")
    public String verPolizas(@RequestParam(name = "id", required = false) String id, Model model) {
        if (id == null) {
            PolizaRequest polizaRequest = _polizaRequestService.findByCodRequestIsNotNull();
            id = polizaRequest.getApplicant().getId();
        }
        Cliente person = _clienteService.getPersonById(id);
        List<PolizaRequest> polizaRequests = _polizaRequestService.findEnergyByPerson(person);

        model.addAttribute("person", person);
        model.addAttribute("polizaRequests", polizaRequests);

        return "listaPolizas";
    }
}
