package ru.sevastopall.jdbcrepeat.dto;

public record TicketFilter(int limit,
                           int offset,
                           String passengerName,
                           String seatNo) {

}
