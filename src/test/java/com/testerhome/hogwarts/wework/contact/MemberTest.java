package com.testerhome.hogwarts.wework.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

class MemberTest {

    static Member member;
    @BeforeEach
    void setUp() {
        member=new Member();
    }

    @ParameterizedTest
    @ValueSource(strings = {"tony_", "kevin_", "lily_"} )
    void create(String name) {
        String nameNew=name+member.random;
        //下面的random保证每个用例的数据不重复
        String random=String.valueOf(System.currentTimeMillis()).substring(5+0, 5+8);
        HashMap<String, Object> map=new HashMap<>();
        map.put("userid", nameNew + random);
        map.put("name", nameNew + random);
        map.put("department", Arrays.asList(1, 2));
        map.put("mobile", "138"+ random);
        map.put("email", random + "@qq.com");

        member.create(map).then().statusCode(200).body("errcode", equalTo(0));
    }
}