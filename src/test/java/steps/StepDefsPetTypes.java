package steps;

import io.cucumber.java.DocStringType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.core.Serenity;

import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.*;

import static net.serenitybdd.rest.SerenityRest.rest;
import static org.hamcrest.Matchers.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StepDefsPetTypes {

    private final String URI_BASE = "https://petstore3.swagger.io/api/v3";

    @Given("el cliente configura la URI base")
    public void elClienteConfiguraLaURIBase() {
        rest().baseUri(URI_BASE);
    }

    @When("el cliente realiza una peticion GET a {string}")
    public void el_cliente_realiza_una_peticion_get_a(String path) {
        when().get(URI_BASE.concat(path)).andReturn();
    }

    @Then("el servidor debe de responder con status {int}")
    public void el_servidor_debe_de_responder_con_un_status(Integer statusCode) {
        then().statusCode(statusCode);
    }

    @And("el cuerpo de la respuesta contiene la propiedad status con el valor {string}")
    public void elCuerpoDeLaRespuestaContieneLaPropiedadStatusConElValor(String petStatus) {
        then().body("status", is(petStatus));
    }

    @And("el cuerpo de la respuesta contiene la propiedad id con el valor {int}")
    public void elCuerpoDeLaRespuestaContieneLaPropiedadIdConElValor(Integer petId) {
        then().body("id", is(petId));
    }

    @Given("el cliente tiene los datos de la nueva mascota")
    public void el_cliente_tiene_los_datos_de_la_nueva_mascota(String newPet) {
        Serenity.setSessionVariable("newPet").to(newPet);
    }

    @When("el cliente realiza una peticion POST a {string} con los detalles de la nueva mascota")
    public void el_cliente_realiza_una_peticion_post_a_con_los_detalles_del_nuevo_tipo_de_mascota(String path) {
        String newPet = Serenity.sessionVariableCalled("newPet");
        given().contentType(ContentType.JSON)
                .body(newPet)
                .post(URI_BASE.concat(path))
                .andReturn();
    }

    @And("el cuerpo de la respuesta debe contener los detalles del nuevo tipo de mascota registrado")
    public void elCuerpoDeLaRespuestaDebeContenerLosDetallesDelNuevoTipoDeMascotaRegistrado() throws JsonProcessingException {
        String docString = Serenity.sessionVariableCalled("newPet");
        Map<String, Object> jsonMap = new ObjectMapper().readValue(docString, new TypeReference<>() {
        });
        then().body(notNullValue());
        then().body("id", notNullValue());
        then().body("id", instanceOf(Number.class));
        then().body("name", notNullValue());
        then().body("name", is(jsonMap.get("name")));
        then().body("status", notNullValue());
        then().body("status", is(jsonMap.get("status")));
    }

    @Given("el cliente tiene los datos de la mascota a actualizar")
    public void elClienteTieneLosDatosDeLaMascotaAActualizar(String actPet) {
        Serenity.setSessionVariable("actPet").to(actPet);
    }

    @When("el cliente realiza una peticion PUT a {string} con los detalles de la mascota actualizada")
    public void elClienteRealizaUnaPeticionPUTAConLosDetallesDeLaMascotaActualizada(String path) {
        String newPet = Serenity.sessionVariableCalled("actPet");
        given().contentType(ContentType.JSON)
                .body(newPet)
                .put(URI_BASE.concat(path))
                .andReturn();
    }

    @And("el cuerpo de la respuesta debe contener los detalles de la mascota actualizada")
    public void elCuerpoDeLaRespuestaDebeContenerLosDetallesDeLaMascotaActualizada() throws JsonProcessingException {
        String docString = Serenity.sessionVariableCalled("actPet");
        Map<String, Object> jsonMap = new ObjectMapper().readValue(docString, new TypeReference<>() {
        });
        then().body(notNullValue());
    }

    @When("el cliente realiza una peticion DELETE a {string} con id tipo de mascota eliminado {int} y apiKey {int}")
    public void elClienteRealizaUnaPeticionDELETEAConIdTipoDeMascotaEliminadoYApiKey(String path, int id, int apiKey) {
        given().pathParam("id", id)
                .header("api_key", apiKey)
                .delete(URI_BASE.concat(path))
                .andReturn();
    }

}
