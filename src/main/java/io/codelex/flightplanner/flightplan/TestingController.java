package io.codelex.flightplanner.flightplan;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingController {
    private final FlightPlanService flightPlanService;

    public TestingController(FlightPlanService flightPlanService) {
        this.flightPlanService = flightPlanService;
    }

    @PostMapping(value = "/testing-api/clear")
    public void clearFlights() {
        this.flightPlanService.clearFlights();
    }
}
