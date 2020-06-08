package com.example.libfilm;

import com.example.libfilm.controllers.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = "/clean-DB-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/add-test-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class IntegrationSurfaceTesting {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController controller;

    @Test
    @WithUserDetails("admin")
    void MainPageTest() throws Exception { //тест на авторизацию
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='Auth']").string("Reg/Log")); //здесь должен быть элемент созданый после авторизации
    }


    @Test // тест на добавления фильма
    @WithUserDetails("admin")
    void addFilmTest() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/admin").file("file", "12345".getBytes())
                .param("filmName", "как приручить дракона")
                .param("genre", "фантастика")
                .with(csrf());
        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated());
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(xpath("//div[@id='MainContent']/div[@data-id=1]").exists());

    }

    @Test //проверка конроля доступа
    void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/gegg/g"))
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test // тест роботоспособности логина
    void isCorrectLogin() throws Exception { // проверка роботы логина
        this.mockMvc.perform(formLogin().user("admin").password("123456"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void badCredentials() throws Exception { //проверка пост запроса без токена
        this.mockMvc.perform(post("/login").param("username", "admin").param("password","123456"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
