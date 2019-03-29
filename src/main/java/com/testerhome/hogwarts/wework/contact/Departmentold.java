package com.testerhome.hogwarts.wework.contact;

import com.jayway.jsonpath.JsonPath;
import com.testerhome.hogwarts.wework.Wework;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Departmentold extends Contact{

    public Response list(String id){
        return given().log().all()
                .param("access_token", Wework.getToken())
                .param("id", id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then().log().all()
                .statusCode(200).extract().response();
    }

    public Response create1(String name, int parentid){
        return given().log().all()
                //POST请求应该用queryParam，用param会跟GET混淆
                .queryParam("access_token", Wework.getToken())
                .body("{\n" +
                        "   \"name\": \"" +name+ "\",\n" +
                        "   \"parentid\": " +parentid+ ",\n" +
                        "}")
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all()
                .statusCode(200).extract().response();
    }

    public Response create(String name, int parentid){

        //用的不是restassured自带的jsonpath，用的https://github.com/json-path/JsonPath
        String body=JsonPath.parse(this.getClass()
                .getResourceAsStream("/data/create.json"))
                .set("$.name", name)
                .set("$.parentid", parentid)
                .jsonString();
        return given().log().all().contentType(ContentType.JSON) //如果要支持中文，ContentType.JSON
                //POST请求应该用queryParam，用param会跟GET混淆
                .queryParam("access_token", Wework.getToken())
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all()
                .statusCode(200).extract().response();
    }

    public Response update(String id, String name, String parentid){
        String body=JsonPath.parse(this.getClass()
                .getResourceAsStream("/data/update.json"))
                .set("$.id", id)
                .set("$.name", name)
                .set("$.parentid", parentid)
                .jsonString();

        return given().log().all()
                .queryParam("access_token", Wework.getToken())
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/update")
                .then().log().all()
                .statusCode(200).extract().response();
    }

    public Response delete(int id){

        return given().log().all()
                .param("access_token", Wework.getToken())
                .param("id",id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then().log().all()
                .statusCode(200).extract().response();
    }
}
