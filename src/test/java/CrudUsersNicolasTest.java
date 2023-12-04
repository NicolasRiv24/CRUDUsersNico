import org.json.JSONObject;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.Matchers.*;
public class CrudUsersNicolasTest {

    int id;
    @Test (priority = 0)
    void getUsers(){
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .log().all()
        ;

    }
    @Test (priority = 1)
    void createUser() {
        JSONObject data = new JSONObject();
        data.put("name", "morpheus");
        data.put("job", "Leader");
        id = given()
                .contentType("application/json")
                .body(data)

                .when()
                .post("https://reqres.in/api/users")
                .jsonPath().getInt("id");
        // .then()
        //.statusCode (201)

    }

    @Test (priority = 2)
    void updateUser() {
        JSONObject data = new JSONObject();
        data.put("name", "morpheus");
        data.put("job", "zion resident");

        given()
                .contentType("application/json")
                .body(data)
                .when()
                .put("https://reqres.in/api/users/" + id)
                .then()
                .statusCode(200)
                .log().all();
    }
    @Test (priority = 3)
    void deleteUser(){
        given()
                .when()
                .delete("https://reqres.in/api/users/"+id)
                .then()
                .statusCode (204)
                .log().all();
    }
    @Test (priority = 4)
    void getSingleUser(){
        given().when().get("https://reqres.in/api/users/2")
                .then().statusCode(200)
                .body("data.id", isA(Integer.class))
                .body("data.email", isA(String.class))
                .body("data.first_name", isA(String.class))
                .body("data.last_name", isA(String.class))
                .body("data.avatar", isA(String.class))
                .body("support.url", isA(String.class))
                .body("support.text", isA(String.class))
                .body("data.id",equalTo(2))
                .body("data.email",equalTo("janet.weaver@reqres.in"))
                .body("data.first_name", equalTo("Janet"))
                .body("data.last_name", equalTo("Weaver"))
                .body("data.avatar", equalTo("https://reqres.in/img/faces/2-image.jpg"))
                .body("support.url", equalTo("https://reqres.in/#support-heading"))
                .body("support.text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"))
                .log().all();

    }
    @Test
    void getUnexistingUser(){
        given().when().get("https://reqres.in/api/users/24").then().statusCode(404);

    }
}
