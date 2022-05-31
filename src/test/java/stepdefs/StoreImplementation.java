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
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class StoreImplementation implements Serializable{
    private Response postStore = null;
    private Response deleteOrder = null;


    @Before
    public void before(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    //store
    @Given("the following get request that brings us the all inventory")
    public  Response getStores(){
        Response responseGetStores= given().log().all().get("/store/inventory");
        return responseGetStores;
    }

    @Then("the response is 200 store")
    public void validateResponse(){
        assertEquals("The response is not 200", 200, getStores().statusCode());
    }

    //storePost
    @Given("the following post request that add order")
    public void postStore(){
        HashMap<String, Object> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("id", 98754);
        bodyRequestMap.put("petId", 2345);
        bodyRequestMap.put("quantity", 0);
        bodyRequestMap.put("shipDate", "2022-05-25T16:52:17.052Z");
        bodyRequestMap.put("status", "placed");
        bodyRequestMap.put("complete", true);
        postStore = given().contentType(ContentType.JSON).body(bodyRequestMap).post("/store/order");    }

    @And("the response is 200 for the post store")
    public void validateResponsePost() {
        assertEquals("The response is not 200", 200, postStore.statusCode());
    }

    @Then("the body response contains the {string} of the order created")
    public void validateResponsePostBodyValueName(String valueComplete) {
        JsonPath jsonPathOrders = new JsonPath(postStore.body().asString());
        String jsonOrder=jsonPathOrders.getString("complete");
        assertEquals("The value of the name field is not what is expected",valueComplete,jsonOrder);
    }

    //orderGet
    @Given("the following get request that brings us the order created")
    public Response getOrderCreated() {
        postStore();
        JsonPath jsonPathOrder = new JsonPath(postStore.body().asString());
        String jsonIdCreate=jsonPathOrder.getString("id");

        Response responseGetOrder= given().log().all().get("/store/order/"+jsonIdCreate);
        return responseGetOrder;
    }

    @Then("the response is 200 order")
    public void theResponseIsOrder() {
        assertEquals("The response is not 200", 200, getOrderCreated().statusCode());
    }

    //orderDel
    @And("the following delete request that delete order")
    public void deleteOrder(){
        JsonPath jsonPathOrder = new JsonPath(postStore.body().asString());
        String jsonIdCreate=jsonPathOrder.getString("id");
        deleteOrder = given().accept(ContentType.JSON).delete("/store/order/"+jsonIdCreate);
    }

    @Then("the response is 200 for the delete order")
    public void validateCodeResponseDelete() {
        assertEquals("The response is not 200", 200, deleteOrder.statusCode());
    }
}
