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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserImplementation implements Serializable{
    private Response putUser = null;
    private Response postUser = null;
    private Response deleteUser= null;
    private Response getUser = null;

    @Before
    public void before(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    //userPost
    @Given("the following post request that add user")
    public void postUser(){
        HashMap<String, Object> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("id", 7654);
        bodyRequestMap.put("username", "FlorCh");
        bodyRequestMap.put("firstName", "Flor");
        bodyRequestMap.put("lastName", "Ch");
        bodyRequestMap.put("email", "flor@email");
        bodyRequestMap.put("password", "password");
        bodyRequestMap.put("phone", "123456789");
        bodyRequestMap.put("userStatus", 1);

        postUser = given().contentType(ContentType.JSON).body(bodyRequestMap).post("/user");
    }

    @And("the response is 200 for the user post")
    public void validateResponsePost() {
        System.out.println("STATUS CODE: " + postUser.statusCode());
        assertEquals("The response is not 200", 200, postUser.statusCode());
    }

    @Then("the body response contains the {string} of the user created")
    public void validateResponsePostBodyValueName(String valueId) {
        JsonPath jsonPathUser = new JsonPath(postUser.body().asString());
        String jsonUser = jsonPathUser.getString("message");
        System.out.println("id: " + jsonUser);
        assertEquals("The value of the id field is not what is expected",valueId,jsonUser);
    }

    //userGet
    @Given("the following get request that brings us the {string} created")
    public Response getUserCreated(String username) {
        getUser = given().log().all().get("/user/"+username);
        return getUser;
    }

    @Then("the response is 200 for user created")
    public void theResponseIsUser() {
        assertEquals("The response is not 200", 200, getUser.statusCode());
    }

    //userLogin
    @Given("the user login with username and password")
    public Response userLogin() {
        JsonPath jsonPathUser = new JsonPath(getUser.body().asString());
        String jsonUsername = jsonPathUser.getString("username");
        String jsonPassword = jsonPathUser.getString("password");
        Response responseLogin= given().log().all().param("username", jsonUsername).param("password",jsonPassword)
                .get("/user/login");
        return responseLogin;
    }

    @Then("the response is 200 for user login")
    public void theResponseIsForUserLogin() {
        assertEquals("The response is not 200", 200, userLogin().statusCode());
    }

    //logout
    @Given("the user logout")
    public Response userLogout() {
        Response responseLogout= given().log().all().get("/user/logout");
        return responseLogout;
    }

    @Then("the response is 200 for logout")
    public void theResponseIsForUserLogout() {
        assertEquals("The response is not 200", 200, userLogout().statusCode());
    }

    //userPut
    @And("the following put request that update user password")
    public void putUser(){
        JsonPath jsonPathUser = new JsonPath(getUser.body().asString());
        String jsonUsername=jsonPathUser.getString("username");

        HashMap<String, Object> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("id", 7654);
        bodyRequestMap.put("username", "FlorCh");
        bodyRequestMap.put("firstName", "Flor");
        bodyRequestMap.put("lastName", "Ch");
        bodyRequestMap.put("email", "flor@email");
        bodyRequestMap.put("password", "newpassword");
        bodyRequestMap.put("phone", "123456789");
        bodyRequestMap.put("userStatus", 1);

        putUser = given().contentType(ContentType.JSON).body(bodyRequestMap).put("/user/"+jsonUsername);
    }

    @And("the response is 200 for the user put")
    public void validateResponsePut() {
        assertEquals("The response is not 200", 200, putUser.statusCode());
    }

    //userDel
    @And("the following delete request that delete user")
    public void deleteUsers(){
        JsonPath jsonPathUser = new JsonPath(getUser.body().asString());
        String jsonUsername=jsonPathUser.getString("username");
        deleteUser = given().accept(ContentType.JSON).delete("/user/"+jsonUsername);
    }

    @Then("the response is 200 for the delete user")
    public void validateCodeResponseDelete() {
        assertEquals("The response is not 200", 200, deleteUser.statusCode());
    }

}
