package com.flightapp.service;

import java.time.LocalDateTime;
import java.util.List;

import com.flightapp.dto.FlightSearchRequest;
import com.flightapp.entity.Flight;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FlightService {

	Mono<Flight> addFlight(Flight flight);

	Flux<Flight> getAllFlights();
	
	Mono<Flight> updateFlight(String flightId, Flight updatedFlight);

    Mono<Void> deleteFlight(String flightId);

	Mono<Flight> searchFlightById(String id);

	Mono<List<Flight>> search(FlightSearchRequest FlightSearchRequest);

	Flux<Flight> searchFlightsByAirline(String fromPlace, String toPlace, String airline);

	Mono<Flight> reserveSeats(String flightId, int seatCount);

	Mono<Flight> releaseSeats(String flightId, int seatCount);
}
