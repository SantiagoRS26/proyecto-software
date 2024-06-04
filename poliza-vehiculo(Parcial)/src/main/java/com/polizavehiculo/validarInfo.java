package com.polizavehiculo;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class validarInfo implements JavaDelegate {
    private final String nombreEsperado = "Juan Perez"; // Valor esperado para comparar
    private final String numCCEsperado = "12345";
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Obtener el nombre del cliente desde las variables del modelo BPMN
        String nombreCliente = (String) execution.getVariable("NombreCliente");
        String numCC = (String) execution.getVariable("NumeroCC");
        // Comprobar si el nombre obtenido coincide con el nombre esperado
        if (nombreCliente.equals(nombreEsperado)&&numCC.equals(numCCEsperado)) {
            // Establecer una variable de proceso para confirmar que los nombres coinciden
            execution.setVariable("estadoConfirmacion", true);
        } else {
            // Establecer una variable de proceso para indicar que los nombres no coinciden
            execution.setVariable("estadoConfirmacion", false);
        }
    }
}
