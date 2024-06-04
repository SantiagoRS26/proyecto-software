package com.polizavehiculo.service;

import com.polizavehiculo.dto.PolizaDTO;
import com.polizavehiculo.dto.TaskInfo;
import com.polizavehiculo.model.PolizaRequest;

import java.sql.SQLException;

public interface ClientePolizaService {
    String startProcessInstance(PolizaDTO polizaDTO);
    void setAssignee(String taskId, String userId);
    TaskInfo getTaskInfoByProcessId(String processId);
    TaskInfo getTaskInfoByProcessIdWithApi(String processId);
    String updateProcessVariables(String processId, PolizaRequest polizaRequest);
    String completeTask(String processId);
    void messageEvent(String processId);
    Long getCountReview(String processId);
    void updateReviewAndStatus(String processId, String status) throws SQLException;
}
