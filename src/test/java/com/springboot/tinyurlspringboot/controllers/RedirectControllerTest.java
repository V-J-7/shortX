package com.springboot.tinyurlspringboot.controllers;

import com.springboot.tinyurlspringboot.controller.RedirectController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedirectControllerTest {
    @Autowired
    private RedirectController redirectController;

    @Test
    public void testRedirect() {
        System.out.println(redirectController.redirectUser("mini.to/r5sE14Utdx"));
    }
}
