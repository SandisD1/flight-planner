package io.codelex.flightplanner.flightplan;

import io.codelex.flightplanner.flightplan.domain.Airport;
import io.codelex.flightplanner.flightplan.domain.Flight;
import io.codelex.flightplanner.flightplan.dto.FlightRequest;
import io.codelex.flightplanner.flightplan.dto.PageResult;
import io.codelex.flightplanner.flightplan.dto.SearchFlightsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightPlanService {

    private FlightPlanRepository flightPlanRepository;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public FlightPlanService(FlightPlanRepository flightPlanRepository) {
        this.flightPlanRepository = flightPlanRepository;

    }

    public FlightPlanRepository getFlightPlanRepository() {
        return flightPlanRepository;
    }

    public void setFlightPlanRepository(FlightPlanRepository flightPlanRepository) {
        this.flightPlanRepository = flightPlanRepository;
    }


    public void clearFlights() {
        this.flightPlanRepository.setIdsGiven(0);
        this.flightPlanRepository.setPlan(new ArrayList<>());
    }

    public Flight setID(FlightRequest flightRequest) {
        int newID = this.flightPlanRepository.getIdsGiven().incrementAndGet();
        return new Flight(newID,
                flightRequest.getFrom(),
                flightRequest.getTo(),
                flightRequest.getCarrier(),
                flightRequest.getDepartureTime(),
                flightRequest.getArrivalTime());
    }

    public Flight addFlight(Flight flight) {

        if (flightAlreadyAdded(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can't add same flight twice");
        } else if (sameAirport(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Same Airports");
        } else if (!validDatesFlight(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arrival after departure");
        } else {
            this.flightPlanRepository.saveFlight(flight);
            return flight;
        }
    }

    public boolean flightAlreadyAdded(Flight flight) {
        return this.flightPlanRepository.getPlan().stream()
                .anyMatch(storedFlight -> storedFlight.equals(flight));
    }


    public boolean sameAirport(Flight flight) {
        return flight.getFrom().equals(flight.getTo());
    }

    public boolean validDatesFlight(Flight flight) {
        return LocalDateTime
                .parse(flight.getDepartureTime(), dateTimeFormatter)
                .isBefore(LocalDateTime.parse(flight.getArrivalTime(), dateTimeFormatter));

    }

    public Flight fetchFlight(int id) {
        Flight found = this.flightPlanRepository.getFlight(id);
        if (found == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return found;
    }

    public void deleteFlight(int id) {
        this.flightPlanRepository.deleteFlight(id);
    }

    public Airport[] searchAirport(String phrase) {

        String input = phrase.trim().toUpperCase();
        List<Airport> matches = new ArrayList<>();

        for (Flight flight : this.flightPlanRepository.getPlan()) {
            Airport from = flight.getFrom();
            Airport to = flight.getTo();
            Airport found = searchInAirport(from, input);
            if (found != null) {
                matches.add(found);
                return matches.toArray(new Airport[1]);
            } else {
                found = searchInAirport(to, input);
            }
            if (found != null) {
                matches.add(found);
                return matches.toArray(new Airport[1]);
            }
        }
        return matches.toArray(new Airport[0]);
    }

    public Airport searchInAirport(Airport airport, String phrase) {
        if (airport.getAirport().toUpperCase().contains(phrase)) {
            return airport;
        } else if (airport.getCity().toUpperCase().contains(phrase)) {
            return airport;
        } else if (airport.getCountry().toUpperCase().contains(phrase)) {
            return airport;
        } else {
            return null;
        }
    }

    public PageResult searchFlights(SearchFlightsRequest req) {
        if (req.getFrom().equals(req.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Same Airports");
        } else {
            List<Flight> foundFlights =
                    this.flightPlanRepository.getPlan()
                            .stream()
                            .filter(storedFlight -> storedFlight.getFrom()
                                    .getAirport()
                                    .equals(req.getFrom()) &&
                                    storedFlight.getTo()
                                            .getAirport()
                                            .equals(req.getTo()) &&
                                    LocalDateTime
                                            .parse(storedFlight.getDepartureTime(), dateTimeFormatter)
                                            .toLocalDate()
                                            .equals(LocalDate.parse(req.getDepartureDate())))
                            .toList();

            return new PageResult(foundFlights);
        }

    }
}

