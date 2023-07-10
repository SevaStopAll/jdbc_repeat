package ru.sevastopall.jdbcrepeat;

import ru.sevastopall.jdbcrepeat.dao.TicketDao;
import ru.sevastopall.jdbcrepeat.dto.TicketFilter;
import ru.sevastopall.jdbcrepeat.enitity.Ticket;

import java.math.BigDecimal;
import java.util.List;

public class DaoRunner {
    public static void main(String[] args) {
        TicketFilter ticketFilter = new TicketFilter(3, 2, null, null);
        TicketDao dao = TicketDao.getInstance();
        List<Ticket> tickets = dao.findAll(ticketFilter);
        System.out.println(tickets);
    }

    private static void findAllTest() {
        List<Ticket> tickets = TicketDao.getInstance().findAll();
        System.out.println(tickets);
    }

    private static void findByIdUpdateTest() {
        TicketDao dao = TicketDao.getInstance();
        var ticket = dao.findById(2L);
        System.out.println(ticket);

        ticket.ifPresent(ticket1 ->  {
            ticket1.setCost(BigDecimal.valueOf(188.99));
            dao.update(ticket1);
        });
    }

    private static void deleteTest() {
        var ticketDao = TicketDao.getInstance();
        var deleteResult = ticketDao.delete(56L);
        System.out.println(deleteResult);
    }

    private static void saveTest() {
        var ticketDao = TicketDao.getInstance();
        var ticket = new Ticket();
        ticket.setPassengerNo("123456");
        ticket.setPassengerName("Test");
        ticket.setFlightId(3L);
        ticket.setSeatNo("B3");
        ticket.setCost(BigDecimal.TEN);
        var savedTicket = ticketDao.save(ticket);
        System.out.println(savedTicket);
    }
}
