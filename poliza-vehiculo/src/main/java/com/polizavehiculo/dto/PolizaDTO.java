package com.polizavehiculo.dto;

import com.polizavehiculo.model.Cliente;
import lombok.Data;

@Data
public class PolizaDTO {
    private Long codRequest;
    private Cliente cliente;
    private String ApplicantClienteId;
    private String processId;
    private String marcaVehiculo;
    private String referenciaVehiculo;
    private Integer cantidadSiniestros;
    private String status;
    private TaskInfo taskInfo;
    private Long countReviewCR;
}
