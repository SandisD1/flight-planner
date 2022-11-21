package io.codelex.flightplanner.flightplan;

import io.codelex.flightplanner.flightplan.domain.Flight;
import io.codelex.flightplanner.flightplan.dto.FlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AdminCommandsController {
    private FlightPlanService flightPlanService;

    public AdminCommandsController(FlightPlanService flightPlanService) {

        this.flightPlanService = flightPlanService;
    }


    public FlightPlanService getFlightPlanService() {
        return flightPlanService;
    }

    public void setFlightPlanService(FlightPlanService flightPlanService) {
        this.flightPlanService = flightPlanService;
    }

    @PutMapping(value = "/admin-api/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody FlightRequest flightRequest) {
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
}
