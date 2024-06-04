package com.polizavehiculo.service.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polizavehiculo.dto.PolizaDTO;
import com.polizavehiculo.dto.TaskInfo;
import com.polizavehiculo.model.PolizaRequest;
import com.polizavehiculo.service.ClientePolizaService;
import com.polizavehiculo.service.PolizaRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InsuranceAgent_RecDatSol implements ClientePolizaService {
    private final RestTemplate restTemplate;
    private final PolizaRequestServiceImpl polizaRequestService;

    @Value("${camunda.url:http://localhost:8080/engine-rest/}")
    private String camundaUrl;

    private List<TaskInfo> tasksList = new ArrayList<>();

    public String startProcessInstance(PolizaDTO polizaDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> variables = new HashMap<>();
        System.out.println("polizaDTO.getCodRequest()"+polizaDTO.getCodRequest());
        System.out.println("PolizaDTO.getApplicantClienteId()"+polizaDTO.getApplicantClienteId());
        variables.put("codRequest", Map.of("value", polizaDTO.getCodRequest(), "type", "Long"));
        variables.put("applicant", Map.of("value", polizaDTO.getApplicantClienteId(), "type", "Long"));
        variables.put("nombre", Map.of("value", polizaDTO.getCliente().getNombre(), "type", "String"));
        variables.put("edadCliente", Map.of("value", polizaDTO.getCliente().getEdadCliente(), "type", "Integer"));
        variables.put("marcaVehiculo", Map.of("value", polizaDTO.getMarcaVehiculo(), "type", "String"));
        variables.put("referenciaVehiculo", Map.of("value", polizaDTO.getReferenciaVehiculo(), "type", "String"));
        variables.put("cantidadSiniestros", Map.of("value", polizaDTO.getCantidadSiniestros(), "type", "Integer"));
        variables.put("countReviewsBpm", Map.of("value", 0, "type", "Long"));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("variables", variables);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(camundaUrl + "process-definition/key/Process_0lufe9s/start",
                    requestEntity, Map.class);
            String processId = String.valueOf(response.getBody().get("id"));
            TaskInfo taskInfo = getTaskInfoByProcessIdWithApi(processId);
            setAssignee(taskInfo.getTaskId(), "Agente de seguros");
            taskInfo.setProcessId(processId);

            return processId;

        } catch (HttpClientErrorException e) {
            return e.getResponseBodyAsString();
        }
    }
    public Long getCountReview(String processId) {
        PolizaRequest polizaRequest = polizaRequestService.getPolizaRequestByProcessId(processId);
        return polizaRequest.getCountReviewCR();

    }
    public void setAssignee(String taskId, String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", userId);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(camundaUrl + "/task/" + taskId + "/assignee",
                    HttpMethod.POST, requestEntity, String.class);
            System.out.println("Assignee set successfully");
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            System.err.println("Error in the Camunda request: " + errorMessage);
        }
    }
    public TaskInfo getTaskInfoByProcessId(String processId) {

        try {
            ResponseEntity<List<Map>> response = restTemplate.exchange(
                    camundaUrl + "/task?task?processInstanceId=" + processId, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Map>>() {
                    });

            List<Map> tasks = response.getBody();
            if (tasks != null && !tasks.isEmpty()) {
                Map<String, String> taskInfoMap = new HashMap<>();
                taskInfoMap.put("taskId", String.valueOf(tasks.get(0).get("id")));
                taskInfoMap.put("taskName", String.valueOf(tasks.get(0).get("name")));
                taskInfoMap.put("assignee", String.valueOf(tasks.get(0).get("assignee")));

                System.out.println("Task Info for Process ID " + processId + ":");
                System.out.println("Task ID: " + taskInfoMap.get("taskId"));
                System.out.println("Task Name: " + taskInfoMap.get("taskName"));
                System.out.println("Assignee: " + taskInfoMap.get("assignee"));

                TaskInfo taskInfo = new TaskInfo();
                taskInfo.setProcessId(processId);
                taskInfo.setTaskId(taskInfoMap.get("taskId"));
                taskInfo.setTaskName(taskInfoMap.get("taskName"));
                taskInfo.setTaskAssignee(taskInfoMap.get("assignee"));

                tasksList.add(taskInfo);
                return taskInfo;
            } else {
                System.err.println("No tasks found for Process ID " + processId);
                return null;
            }
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            System.err.println("Error with Camunda request: " + errorMessage);
            return null;
        }
    }
    public TaskInfo getTaskInfoByProcessIdWithApi(String processId) {

        try {
            ResponseEntity<List<Map>> response = restTemplate.exchange(
                    camundaUrl + "task?processInstanceId=" + processId, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Map>>() {
                    });
            List<Map> tasks = response.getBody();

            if (tasks != null && !tasks.isEmpty()) {
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.setTaskId(String.valueOf(tasks.get(0).get("id")));
                taskInfo.setTaskName(String.valueOf(tasks.get(0).get("name")));
                taskInfo.setTaskAssignee(String.valueOf(tasks.get(0).get("assignee")));

                return taskInfo;
            } else {
                System.err.println("No tasks found for Process ID " + processId);
                return null;
            }
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            System.err.println("Error with Camunda request: " + errorMessage);
            return null;
        }
    }
    public String updateProcessVariables(String processId, PolizaRequest polizaRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> modifications = new HashMap<>();
        modifications.put("nombre", Map.of("value", polizaRequest.getApplicant().getNombre(), "type", "String"));
        modifications.put("edadCliente", Map.of("value", polizaRequest.getApplicant().getEdadCliente(), "type", "Integer"));
        modifications.put("codRequest", Map.of("value", polizaRequest.getCodRequest(), "type", "Long"));
        modifications.put("marcaVehiculo", Map.of("value", polizaRequest.getMarcaVehiculo(), "type", "String"));
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("modifications", modifications);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    camundaUrl + "process-instance/" + processId + "/variables", HttpMethod.POST, requestEntity,
                    String.class);
            System.out.println("Variables updated successfully: " + response.getBody());
            return String.valueOf(polizaRequest.getApplicant().getId());
        } catch (Exception e) {
            System.err.println("Error while updating variables: " + e.getMessage());
        }
        return "";
    }
    public void updateReviewAndStatus(String processId, String status) throws SQLException {
        try {
            // Establecer la conexión JDBC con la base de datos H2
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

            // Consulta SQL para actualizar el estado en la tabla de solicitud de energía
            String updateQuery = "UPDATE POLIZA_REQUEST SET status = ? WHERE process_id = ?";

            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, status);
                updateStatement.setString(2, processId);

                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Estado actualizado, y contador de revisión incrementado.");
                } else {
                    System.out.println("No se encontraron registros para el processId dado: " + processId);
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el controlador JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al establecer la conexión JDBC o ejecutar la consulta SQL: " + e.getMessage());
        }
    }
    public void messageEvent(String processId) {
        String messageName = "hayIncosistencias";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        Map<String, Object> processVariables = new HashMap<>();
        processVariables.put("inconsistenciasSubsanadas", Map.of("value", true, "type", "boolean"));

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("messageName", messageName);
        requestBodyMap.put("businessKey", processId);
        requestBodyMap.put("processVariables", processVariables);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyMap);
        } catch (JsonProcessingException e) {
            System.err.println("error converting request body to JSON");
            return;
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(camundaUrl+"/message", HttpMethod.POST, requestEntity, String.class);
            System.out.println("Message event done. BusinessID: "+processId);
            updateReviewAndStatus(processId,"Revisar detalles de solicitud");
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            System.err.println("Error with Camunda request: " + errorMessage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public String completeTask(String processId) {
        TaskInfo taskInfo = getTaskInfoByProcessId(processId);
        if (taskInfo != null) {
            String taskId = taskInfo.getTaskId();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            try {
                ResponseEntity<Map> response = restTemplate.postForEntity(camundaUrl+"/task/"+taskId+"/complete", requestEntity, Map.class);
                System.out.println("Antes del getTaskInfoByProcessIdWithApi");
                TaskInfo taskInfo1 = getTaskInfoByProcessIdWithApi(processId);
                System.out.println("Despues del getTaskInfoByProcessIdWithApi");
                setAssignee(taskInfo1.getTaskId(), "Servicio al cliente");
                updateReviewAndStatus(processId,"Revisar detalles de solicitud");/* Preguntar*/
                PolizaRequest polizaRequest = polizaRequestService.getPolizaRequestByProcessId(processId);
                polizaRequest.setStatus(taskInfo1.getTaskName());
              //  LocalDateTime currentDate = LocalDateTime.now();
              //  polizaRequest.setRequestDate(currentDate);
               polizaRequestService.updatePolizaRequest(polizaRequest.getCodRequest(), polizaRequest);
                return String.valueOf(polizaRequest.getApplicant().getId());
            } catch (HttpClientErrorException e) {
                String errorMessage = e.getResponseBodyAsString();
                System.err.println("Error with Camunda request: " + errorMessage);
                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("Could not get task information for process ID: " + processId);
            return null;
        }
    }

}
