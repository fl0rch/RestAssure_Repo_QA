package stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class PetImplementation implements Serializable{
    private Response putPet = null;
    private Response postPet = null;
    private Response deletePet= null;

    private List<String> photoList = new ArrayList<>();
    private List<HashMap> tagsList = new ArrayList<>();


    @Before
    public void before(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    //pets
    @Given("the following get request that brings us the all pets per status")
    public  Response getPets(){
        Response responseGetPets= given().log().all().param("status","available")
                .param("status","pending").param("status","sold").get("/pet/findByStatus");
        return responseGetPets;
    }

    @Then("the response is 200 for pet")
    public void validateResponse(){
        System.out.println(getPets().statusCode());
        assertEquals("The response is not 200", 200, getPets().statusCode());
    }

    //petPost
    @Given("the following post request that add pet")
    public void postPet(){
        photoList.add("string");
        HashMap<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("id", 1);
        categoryMap.put("name", "dogg");
        HashMap<String, Object> tagsMap = new HashMap<>();
        tagsMap.put("id", 1);
        tagsMap.put("name", "dogg");
        tagsList.add(tagsMap);
        HashMap<String, Object> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("id", 2345);
        bodyRequestMap.put("category", categoryMap);
        bodyRequestMap.put("name", "Charly");
        bodyRequestMap.put("photoUrls", photoList);
        bodyRequestMap.put("tags", tagsList);
        bodyRequestMap.put("status", "pending");

        postPet = given().contentType(ContentType.JSON).body(bodyRequestMap).post("/pet");    }

    @And("the response is 200 for the pet post")
    public void validateResponsePost() {
        System.out.println("STATUS CODE: " + postPet.statusCode());
        assertEquals("The response is not 200", 200, postPet.statusCode());
    }

    @Then("the body response contains the {string} of the pet created")
    public void validateResponsePostBodyValueName(String valueName) {
        JsonPath jsonPathPet = new JsonPath(postPet.body().asString());
        String jsonPets=jsonPathPet.getString("name");
        assertEquals("The value of the name field is not what is expected",valueName,jsonPets);
    }

    //petGet
    @Given("the following get request that brings us the pet created")
    public Response getPetCreated() {
        postPet();
        JsonPath jsonPathPet = new JsonPath(postPet.body().asString());
        String jsonIdCreate=jsonPathPet.getString("id");

        Response responseGetPet= given().log().all().get("/pet/"+jsonIdCreate);
        return responseGetPet;
    }

    @Then("the response is 200 for pet created")
    public void theResponseIsOrder() {
        assertEquals("The response is not 200", 200, getPetCreated().statusCode());
    }

    //petPut
    @Given("the following put request that update pet status")
    public void putPets(){
        HashMap<String, Object> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("id", 2345);
        bodyRequestMap.put("name", "Charly");
        bodyRequestMap.put("status", "sold");

        putPet = given().contentType(ContentType.JSON).body(bodyRequestMap).put("/pet");
    }

    @And("the response is 200 for the pet put")
    public void validateResponsePut() {
        assertEquals("The response is not 200", 200, putPet.statusCode());
    }

    @Then("the body response contains update {string} pet")
    public void validateResponsePutBodyUpdatedValueName(String updatedStatus) {
        JsonPath jsonPathPets = new JsonPath(putPet.body().asString());
        String jsonPetsStatus=jsonPathPets.getString("status");
        assertEquals("The value of the status field is not what is expected",updatedStatus,jsonPetsStatus);
    }

    //PetDel
    @And("the following delete request that delete pet")
    public void deletePets(){
        JsonPath jsonPathPet = new JsonPath(postPet.body().asString());
        String jsonIdCreate=jsonPathPet.getString("id");
        deletePet = given().accept(ContentType.JSON).delete("/pet/"+jsonIdCreate);
    }

    @Then("the response is 200 for the delete pet")
    public void validateCodeResponseDelete() {
        assertEquals("The response is not 200", 200, deletePet.statusCode());
    }
}
