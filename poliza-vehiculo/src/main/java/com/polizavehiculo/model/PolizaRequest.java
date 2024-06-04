package com.polizavehiculo.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "POLIZA_REQUEST")
@Data
public class PolizaRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codRequest")
    private Long codRequest;

    @Column(name = "processId")
    private String processId;

    @Column(name = "status")
    private String status;

    @Column(name = "marcaVehiculo")
    private String marcaVehiculo;

    @Column(name = "referenciaVehiculo")
    private String referenciaVehiculo;

    @Column(name = "countReviewCR")
    private Long countReviewCR;

    @OneToOne
    @JoinColumn(name = "FK_PERSON", referencedColumnName = "id")
    private Cliente applicant;

}
