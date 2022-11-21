package io.codelex.flightplanner.flightplan.dto;

import io.codelex.flightplanner.flightplan.domain.Flight;

import java.util.List;

public class PageResult {
    private int page;
    private int totalItems;
    private Flight[] items;

    public PageResult(List<Flight> foundFlights) {
        this.page = 0;
        this.totalItems = foundFlights.size();
        this.items = foundFlights.toArray(new Flight[totalItems]);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public Flight[] getItems() {
        return items;
    }

    public void setItems(Flight[] items) {
        this.items = items;
    }
}
