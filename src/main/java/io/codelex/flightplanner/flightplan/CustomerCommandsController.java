package io.codelex.flightplanner.flightplan;

import io.codelex.flightplanner.flightplan.domain.Airport;
import io.codelex.flightplanner.flightplan.domain.Flight;
import io.codelex.flightplanner.flightplan.dto.PageResult;
import io.codelex.flightplanner.flightplan.dto.SearchFlightsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CustomerCommandsController {

    private FlightPlanService flightPlanService;

    public CustomerCommandsController(FlightPlanService flightPlanService) {
        this.flightPlanService = flightPlanService;
    }

    public FlightPlanService getFlightPlanService() {
        return flightPlanService;
    }

    public void setFlightPlanService(FlightPlanService flightPlanService) {
        this.flightPlanService = flightPlanService;
    }

    @GetMapping(value = "/api/airports")
    @ResponseStatus(HttpStatus.OK)
    public Airport[] searchAirport(@RequestParam String search) {
        return this.flightPlanService.searchAirport(search);
    }

    @PostMapping(value = "/api/flights/search")
    @ResponseStatus(HttpStatus.OK)
    public PageResult searchFlights(@Valid @RequestBody SearchFlightsRequest req) {

        return this.flightPlanService.searchFlights(req);
    }

    @GetMapping(value = "/api/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight findFlightByID(@PathVariable int id) {
        return this.flightPlanService.fetchFlight(id);
    }
}
