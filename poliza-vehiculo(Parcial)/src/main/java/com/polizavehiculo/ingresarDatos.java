package com.polizavehiculo;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ingresarDatos  implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Obtener la información del modelo BPMN
        String nombreCliente = (String) execution.getVariable("nombre");
        Integer edadCliente = (Integer) execution.getVariable("edadCliente");
        String marcaVehiculo = (String) execution.getVariable("marcaVehiculo");
        String refrenciaVehiculo = (String) execution.getVariable("referenciaVehiculo");
        Integer  cantSiniestros = (Integer) execution.getVariable("cantidadSiniestros");
        LocalDate fechaRegistro = LocalDate.now();

        // Crear un mapa para almacenar la información de la visita técnica
        Map<String, Object> informacionAlmacenada = new HashMap<>();
        informacionAlmacenada.put("Fecha de registro: ",fechaRegistro);
        informacionAlmacenada.put("Nombre del Cliente: ", nombreCliente);
        informacionAlmacenada.put("Edad deL Cliente: ", edadCliente);
        informacionAlmacenada.put("Marca del Vehiculo: ", marcaVehiculo);
        informacionAlmacenada.put("Referencia del Vehiculo: ", refrenciaVehiculo);
        informacionAlmacenada.put("Cantidad de Siniestros: ", cantSiniestros);

        // Recorrer el mapa e imprimir cada clave y valor
        for (Map.Entry<String, Object> entry : informacionAlmacenada.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
