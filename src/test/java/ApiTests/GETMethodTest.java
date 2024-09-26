package ApiTests;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.hamcrest.Matchers.*;

public class GETMethodTest {


    private final String base_url = "https://reqres.in/api/users";
    private final String id = String.valueOf(new Random().nextInt(1,12));


    @Test
    public void getTest(){

        Response response = get(base_url);
        given()
                .get(base_url+"?page=2")
                .then().statusCode(200)
                .body("data", isA(List.class));

    }
    @Test
    public void postTest(){


        JSONObject request = new JSONObject();
        request.put("first_name","Alberto");
        request.put("last_name","Alonso");
        request.put("email","yomellamotania@gmail.com");
        request.put("avatar","https://reqres.in/img/faces/6-image.jpg");

        Response response =
        given()
                .body(request.toJSONString())
                .when()
                    .post(base_url)
                .then()
                     .statusCode(201)
                     .extract()
                     .response();
        String id = response.jsonPath().getString("id");
        System.out.println(id);
    }



    @Test
    public void put(){

        JSONObject request = new JSONObject();
        request.put("first_name","Tomás");
        request.put("last_name","Moro");


        given()
                .body(request.toJSONString())
                .when()
                .put(base_url+"/"+id)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Response response =
        given()
                .body(request.toJSONString())
                .when().get(base_url+"?id="+id)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String respuesta =  request.toJSONString();
        if (response.statusCode() == 200) {
            System.out.println("Usuario actualizado con éxito");
            System.out.println(respuesta);
        }else{
            System.out.println("No se pudo actualizar el usuario");
        }




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


        given()
                .body(request.toJSONString())
                .when()
                .delete(base_url+"/"+id)
                .then()
                .statusCode(204)
                .log()
                .status();

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
