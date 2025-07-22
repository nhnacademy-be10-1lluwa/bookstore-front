package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.config.handler.CategoryControllerAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;
import wiremock.org.eclipse.jetty.security.LoginService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = LoginController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})
        })
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    private LoginService loginService;

//    @Test
//    @DisplayName("로그인 동작 테스트")
//    public void testLogin() throws Exception {
//        mockMvc.perform(get("/login"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("auth/login"));
//    }
}
