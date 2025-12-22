//package com.flightapp.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.TestingAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
//import com.flightapp.dto.LoginRequest;
//import com.flightapp.entity.User;
//import com.flightapp.service.AuthService;
//
//import reactor.core.publisher.Mono;
//
//@WebFluxTest(UserController.class)
//class UserControllerTest {
//
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @MockBean
//    private AuthService authService;
//
//    // ---------- USER REGISTER SUCCESS ----------
//    @Test
//    void userRegister_success() {
//        User user = new User();
//        user.setId("123");
//        user.setName("Sreenidhi");
//        user.setEmail("user@test.com");
//
//        Mockito.when(authService.userregister(any(User.class)))
//               .thenReturn(Mono.just(user));
//
//        webTestClient.post()
//            .uri("/api/user/register")
//            .contentType(MediaType.APPLICATION_JSON)
//            .bodyValue("""
//                {
//                  "name": "Sreenidhi",
//                  "email": "user@test.com",
//                  "password": "test123"
//                }
//            """)
//            .exchange()
//            .expectStatus().isCreated()
//            .expectBody(String.class)
//            .isEqualTo("user created with id: 123");
//    }
//
//    // ---------- USER LOGIN SUCCESS ----------
//    @Test
//    void userLogin_success() {
//        Mockito.when(authService.userlogin("user@test.com", "test123"))
//               .thenReturn(Mono.just("token: jwt-token"));
//
//        webTestClient.post()
//            .uri("/api/user/login")
//            .contentType(MediaType.APPLICATION_JSON)
//            .bodyValue("""
//                {
//                  "email": "user@test.com",
//                  "password": "test123"
//                }
//            """)
//            .exchange()
//            .expectStatus().isOk()
//            .expectBody(String.class)
//            .isEqualTo("token: jwt-token");
//    }
//
//    // ---------- ADMIN REGISTER SUCCESS ----------
//    @Test
//    void adminRegister_success() {
//        User admin = new User();
//        admin.setId("999");
//        admin.setName("Admin");
//        admin.setEmail("admin@test.com");
//
//        Mockito.when(authService.adminregister(any(User.class)))
//               .thenReturn(Mono.just(admin));
//
//        webTestClient.post()
//            .uri("/api/admin/register")
//            .contentType(MediaType.APPLICATION_JSON)
//            .bodyValue("""
//                {
//                  "name": "Admin",
//                  "email": "admin@test.com",
//                  "password": "admin123"
//                }
//            """)
//            .exchange()
//            .expectStatus().isCreated()
//            .expectBody(String.class)
//            .isEqualTo("user created with id: 999");
//    }
//
//    // ---------- ADMIN LOGIN SUCCESS ----------
//    @Test
//    void adminLogin_success() {
//        Mockito.when(authService.adminlogin("admin@test.com", "admin123"))
//               .thenReturn(Mono.just("token: admin-jwt"));
//
//        webTestClient.post()
//            .uri("/api/admin/login")
//            .contentType(MediaType.APPLICATION_JSON)
//            .bodyValue("""
//                {
//                  "email": "admin@test.com",
//                  "password": "admin123"
//                }
//            """)
//            .exchange()
//            .expectStatus().isOk()
//            .expectBody(String.class)
//            .isEqualTo("token: admin-jwt");
//    }
//
//    // ---------- GET PROFILE (/me) SUCCESS ----------
//    @Test
//    void getMyProfile_success() {
//        User user = new User();
//        user.setId("123");
//        user.setName("Sreenidhi");
//        user.setEmail("user@test.com");
//
//        Mockito.when(authService.getByEmail("user@test.com"))
//               .thenReturn(Mono.just(user));
//
//        Authentication auth =
//            new TestingAuthenticationToken("user@test.com", null);
//
//        webTestClient.mutateWith(
//                org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers
//                    .mockAuthentication(auth)
//            )
//            .get()
//            .uri("/api/me")
//            .exchange()
//            .expectStatus().isOk()
//            .expectBody()
//            .jsonPath("$.email").isEqualTo("user@test.com")
//            .jsonPath("$.name").isEqualTo("Sreenidhi")
//            .jsonPath("$.role").isEqualTo("USER");
//    }
//
//    // ---------- GET USER BY EMAIL SUCCESS ----------
//    @Test
//    void getByEmail_success() {
//        User user = new User();
//        user.setId("123");
//        user.setName("Sreenidhi");
//        user.setEmail("user@test.com");
//
//        Mockito.when(authService.getByEmail("user@test.com"))
//               .thenReturn(Mono.just(user));
//
//        webTestClient.get()
//            .uri("/api/user@test.com")
//            .exchange()
//            .expectStatus().isOk()
//            .expectBody()
//            .jsonPath("$.email").isEqualTo("user@test.com")
//            .jsonPath("$.name").isEqualTo("Sreenidhi");
//    }
//}
