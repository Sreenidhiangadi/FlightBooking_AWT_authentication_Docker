//package com.flightapp.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//
//import java.util.List;
//import java.util.Map;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
//import com.flightapp.dto.FlightSearchRequest;
//import com.flightapp.entity.Flight;
//import com.flightapp.service.FlightService;
//
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@SpringBootTest(
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//        properties = {
//                "spring.security.enabled=false"
//        }
//)
//@AutoConfigureWebTestClient
//class FlightControllerTest {
//
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @MockBean
//    private FlightService flightService;
//
//    @Test
//    void addFlight() {
//        Flight flight = new Flight();
//        flight.setId("FL1");
//
//        Mockito.when(flightService.addFlight(any()))
//                .thenReturn(Mono.just(flight));
//
//        webTestClient
//                .post()
//                .uri("/api/flight/airline/inventory/add")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(flight)
//                .exchange()
//                .expectStatus().isCreated()
//                .expectBody(String.class)
//                .isEqualTo("Flight added successfully with id: FL1");
//    }
//
//    @Test
//    void searchFlights() {
//        Mockito.when(flightService.search(any()))
//                .thenReturn(Mono.just(List.of(new Flight())));
//
//        webTestClient
//                .post()
//                .uri("/api/flight/search")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(new FlightSearchRequest())
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(Flight.class)
//                .hasSize(1);
//    }
//
//    @Test
//    void reserveSeats() {
//        Mockito.when(flightService.reserveSeats("FL1", 2))
//                .thenReturn(Mono.just(new Flight()));
//
//        webTestClient
//                .put()
//                .uri("/api/flight/internal/FL1/reserve/2")
//                .exchange()
//                .expectStatus().isOk();
//    }
//
//    @Test
//    void releaseSeats() {
//        Mockito.when(flightService.releaseSeats("FL1", 2))
//                .thenReturn(Mono.just(new Flight()));
//
//        webTestClient
//                .put()
//                .uri("/api/flight/internal/FL1/release/2")
//                .exchange()
//                .expectStatus().isOk();
//    }
//
//    @Test
//    void getAllFlights() {
//        Mockito.when(flightService.getAllFlights())
//                .thenReturn(Flux.just(new Flight()));
//
//        webTestClient
//                .get()
//                .uri("/api/flight/getallflights")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(Flight.class)
//                .hasSize(1);
//    }
//
//    @Test
//    void searchByAirline() {
//        Mockito.when(flightService.searchFlightsByAirline("BLR", "DEL", "Indigo"))
//                .thenReturn(Flux.just(new Flight()));
//
//        webTestClient
//                .post()
//                .uri("/api/flight/search/airline")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(Map.of(
//                        "fromPlace", "BLR",
//                        "toPlace", "DEL",
//                        "airline", "Indigo"
//                ))
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(Flight.class)
//                .hasSize(1);
//    }
//
//    @Test
//    void getFlightById() {
//        Flight flight = new Flight();
//        flight.setId("FL1");
//
//        Mockito.when(flightService.searchFlightById("FL1"))
//                .thenReturn(Mono.just(flight));
//
//        webTestClient
//                .get()
//                .uri("/api/flight/FL1")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$.id").isEqualTo("FL1");
//    }
//
//    @Test
//    void updateFlight() {
//        Mockito.when(flightService.updateFlight(eq("FL1"), any()))
//                .thenReturn(Mono.just(new Flight()));
//
//        webTestClient
//                .put()
//                .uri("/api/flight/update/FL1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(new Flight())
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class)
//                .isEqualTo("Flight updated successfully");
//    }
//
//    @Test
//    void deleteFlight() {
//        Mockito.when(flightService.deleteFlight("FL1"))
//                .thenReturn(Mono.empty());
//
//        webTestClient
//                .delete()
//                .uri("/api/flight/delete/FL1")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class)
//                .isEqualTo("Flight deleted successfully");
//    }
//}
