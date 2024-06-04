import { Client, Variables, logger } from "camunda-external-task-client-js";

// configuration for the Client:
//  - 'baseUrl': url to the Process Engine
//  - 'logger': utility to automatically log important events
const config = { baseUrl: "http://localhost:8080/engine-rest", use: logger };

// create a Client instance with custom configuration
const client = new Client(config);


client.subscribe("sendStatusCanceled", async function({ task, taskService }) {
  console.log(task, taskService)
  const processVariables = new Variables();
  processVariables.set("cancel", "La solicitud ha sido rechazada porque no se validó el método de pago")
  await taskService.complete(task, processVariables);
});