package io.codelex.flightplanner.flightplan;

import io.codelex.flightplanner.flightplan.domain.Flight;
import io.codelex.flightplanner.flightplan.dto.FlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AdminController {
    private final FlightPlanService flightPlanService;

    public AdminController(FlightPlanService flightPlanService) {

        this.flightPlanService = flightPlanService;
    }

    @PutMapping(value = "/admin-api/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody FlightRequest flightRequest) {
        return this.flightPlanService.addFlight(flightRequest);
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
}
