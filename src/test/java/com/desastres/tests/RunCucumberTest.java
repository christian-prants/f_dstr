package com.desastres.tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features", // Localização dos arquivos .feature
    glue = "com.desastres.tests.steps",      // Pacote com as definições dos steps
    plugin = {
        "pretty",                            // Formatação legível no console
        "html:target/cucumber-reports.html", // Relatório HTML
        "json:target/cucumber.json",         // Relatório JSON (para integração)
        "junit:target/cucumber-reports.xml"  // Relatório JUnit
    },
    monochrome = true,                       // Saída limpa no console
    tags = "@regression"                      // Filtro de tags (opcional)
)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RunCucumberTest {
    // Classe vazia - serve apenas como configurador
}