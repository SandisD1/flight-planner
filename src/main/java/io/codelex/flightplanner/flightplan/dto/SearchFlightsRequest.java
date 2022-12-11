package io.codelex.flightplanner.flightplan.dto;

import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;

public class SearchFlightsRequest {
    @NotBlank
    private String from;

    @NotBlank
    private String to;
    @NotBlank
    private String departureDate;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public SearchFlightsRequest(String from, String to, String departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }


}
