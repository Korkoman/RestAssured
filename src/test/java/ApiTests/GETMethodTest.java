package ApiTests;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class GETMethodTest {

    @Test
    public void getTest(){

        Response response = get("https://reqres.in/api/users?page=2");
        System.out.println(response.getHeader("Date").toString());
        System.out.println(response.getHeader("Transfer-Encoding").toString());
        given()
                .get("https://reqres.in/api/users?page=2")
                .then().statusCode(200)
                .body("data[4].first_name",equalTo("George")).body("data.first_name",hasItems("George","Byron","Rachel"));

    }
    @Test
    public void postTest(){

        /*RestAssured post = new RestAssured();
        Map<String,Object> map = new HashMap<String,Object>();
        post.post("https://reqres.in/api/users",map,null);
        map.put("name","Alberto");
        map.put("job","Automation QA");*/

        JSONObject request = new JSONObject();
        request.put("name","Alberto");
        request.put("job","Automation QA");


        given()
                .body(request.toJSONString())
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .log()
                .all();
    }

    @Test
    public void put(){

        JSONObject request = new JSONObject();
        request.put("name","Tomás");
        request.put("job","Scrum Master");


        given()
                .body(request.toJSONString())
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .log()
                .all();
    }

    @Test
    public void patch(){
        JSONObject request = new JSONObject();
        request.put("name","Pedro");
        request.put("job","Producto Owner");


        given()
                .body(request.toJSONString())
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .log()
                .all();
    }

    @Test
    public void delete(){

        JSONObject request = new JSONObject();
        request.put("name","Pedro");
        request.put("job","Producto Owner");


        given()
                .body(request.toJSONString())
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204)
                .log()
                .all();

    }

    @Test
    public void postLocal(){

        JSONObject request = new JSONObject();

        request.put("nombre","Perrino");
        request.put("apellidos","García Ladrillo");
        request.put("edad","11 millones de años");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post("http://localhost:3000/personas")
                .then()
                .statusCode(201).log().body();

    }

    @Test
    public void getLocal(){

        JSONObject request = new JSONObject();
        request.put("id","756");
        request.put("title","Probando ando");
        request.put("author","Don Alberto");


        System.out.print( given()
                .body(request.toJSONString())
                .when()
                .get("http://localhost:3000/personas")
                .then()
                .statusCode(200)
                .log()
                .all());

    }

    @Test
    public void putLocal(){

        JSONObject request = new JSONObject();

        request.put("nombre","Perrino");
        request.put("apellidos","García Ladrillo");
        request.put("edad","18.5 millones de años");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("http://localhost:3000/personas/6")
                .then()
                .statusCode(200)
                .log()
                .body();

    }

    @Test
    public void deleteLocal(){

        JSONObject request = new JSONObject();

        request.put("nombre","Perrino");
        request.put("apellidos","García Ladrillo");
        request.put("edad","18.5 millones de años");

        given()
                .delete("http://localhost:3000/personas/5")
                .then()
                .statusCode(Matchers.oneOf(204,200))
                .log()
                .body();

    }

}
