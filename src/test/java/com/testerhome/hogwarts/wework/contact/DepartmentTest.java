package com.testerhome.hogwarts.wework.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    Department department;
    String random=String.valueOf(System.currentTimeMillis());
    @BeforeEach
    void setUp() {
        if (department==null){
            department=new Department();
        }
    }

    @Test
    void list() {

        department.list("1").then().statusCode(200).body("department.name[1]", equalTo("tony"));
    }

    @Test
    void create(){

        HashMap<String, Object> map=new HashMap<String, Object>(){{
            put("name","tonychild"+random);
            put("parentid", 2);
        }};
        department.create(map).then().statusCode(200).body("errmsg", equalTo("created"));
    }

    @Test
    void createwithChinese(){

        department.create("魁星department"+random,"2").then().statusCode(200).body("errmsg", equalTo("created"));
    }

    @Test
    void update(){
        String nameOld="seveniruby_d1"+random;
        department.create(nameOld, "1");
        Integer idInt=department.list("").path("department.find{ it.name=='"+ nameOld +"' }.id");
        System.out.println(idInt);
        String id=String.valueOf(idInt);
        department.update("seveniruby_d2"+random,  id).then().body("errcode", equalTo(0));
    }

    @Test
    void delete(){
        String nameOld="seveniruby_d1"+random;
        department.create(nameOld, "1");
        Integer idInt=department.list("").path("department.find{ it.name=='"+ nameOld +"' }.id");
        System.out.println(idInt);
        String id=String.valueOf(idInt);
        department.delete(id).then().body("errcode", equalTo(0));
    }
}