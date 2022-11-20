package io.codelex.flightplanner.flightplan;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codelex.flightplanner.flightplan.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class FlightPlanController {
    private FlightPlanService flightPlanService;

    public FlightPlanController(FlightPlanService flightPlanService) {
        this.flightPlanService = flightPlanService;
    }

    @PostMapping(value = "/testing-api/clear")
    public void clearFlights() {
        this.flightPlanService.clearFlights();
    }

    public FlightPlanService getFlightPlanService() {
        return flightPlanService;
    }

    public void setFlightPlanService(FlightPlanService flightPlanService) {
        this.flightPlanService = flightPlanService;
    }

    @PutMapping(value = "/admin-api/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody FlightRequest flightRequest) throws JsonProcessingException {

        Flight flight = this.flightPlanService.setID(flightRequest);

        return this.flightPlanService.addFlight(flight);
    }


    @GetMapping(value = "/admin-api/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight findFlight(@PathVariable int id) {
        return this.flightPlanService.fetchFlight(id);
    }

    @DeleteMapping(value = "/admin-api/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFlight(@PathVariable int id) {
        this.flightPlanService.deleteFlight(id);
    }

    @GetMapping(value = "/api/airports")
    @ResponseStatus(HttpStatus.OK)
    public Airport[] searchAirport(@RequestParam String search) {
        return this.flightPlanService.searchAirport(search);
    }

    @PostMapping(value = "/api/flights/search")
    @ResponseStatus(HttpStatus.OK)
    public PageResult searchFlights(@Valid @RequestBody SearchFlightsRequest req) throws JsonProcessingException {

        return this.flightPlanService.searchFlights(req);
    }

    @GetMapping(value = "/api/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight findFlightByID(@PathVariable int id) {
        return this.flightPlanService.fetchFlight(id);
    }
}
