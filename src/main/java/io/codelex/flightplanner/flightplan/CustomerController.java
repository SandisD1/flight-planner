package io.codelex.flightplanner.flightplan;

import io.codelex.flightplanner.flightplan.domain.Airport;
import io.codelex.flightplanner.flightplan.domain.Flight;
import io.codelex.flightplanner.flightplan.dto.PageResult;
import io.codelex.flightplanner.flightplan.dto.SearchFlightsRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {

    private final FlightPlanService flightPlanService;

    public CustomerController(FlightPlanService flightPlanService) {
        this.flightPlanService = flightPlanService;
    }

    @GetMapping(value = "/api/airports")
    public List<Airport> searchAirport(@RequestParam String search) {
        return this.flightPlanService.searchAirport(search);
    }

    @PostMapping(value = "/api/flights/search")
    public PageResult searchFlights(@Valid @RequestBody SearchFlightsRequest req) {

        return this.flightPlanService.searchFlights(req);
    }

    @GetMapping(value = "/api/flights/{id}")
    public Flight findFlightByID(@PathVariable int id) {
        return this.flightPlanService.fetchFlight(id);
    }
}
