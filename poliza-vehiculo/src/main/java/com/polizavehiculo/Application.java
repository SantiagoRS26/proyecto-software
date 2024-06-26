package com.polizavehiculo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  public static void main(String... args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.setAdditionalProfiles("debug");
    app.run(args);
  }

}