package io.codelex.flightplanner.flightplan;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingCommandsController {
    private FlightPlanService flightPlanService;

    public TestingCommandsController(FlightPlanService flightPlanService) {
        this.flightPlanService = flightPlanService;
    }

    public FlightPlanService getFlightPlanService() {
        return flightPlanService;
    }

    public void setFlightPlanService(FlightPlanService flightPlanService) {
        this.flightPlanService = flightPlanService;
    }

    @PostMapping(value = "/testing-api/clear")
    public void clearFlights() {
        this.flightPlanService.clearFlights();
    }
}
