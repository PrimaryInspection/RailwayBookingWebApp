package model.service;

import model.dao.mysql.mapper.AbstractDAOFactory;
import model.dao.mysql.mapper.DAOFactory;
import model.dao.mysql.mapper.DataBase;
import model.dao.mysql.mapper.TypePlace;
import model.dto.Ticket;
import model.dto.TrainRoute;
import model.entity.Request;
import model.entity.Train;
import model.entity.User;
import model.exception.InvalidDataBaseOperation;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RequestServiceTest {

    @Test(expected = InvalidDataBaseOperation.class)
    public void addRequestTest() throws InvalidDataBaseOperation {
        DAOFactory factory = AbstractDAOFactory.createDAOFactory(DataBase.MYSQL);
        Train train = factory.createTrainDAO().findAll().get(0);
        train.setBerthFree(0L);
        factory.createTrainDAO().update(train);

        Request request = new Request();
        request.setStatus(0L);
        request.setPrice(200d);
        request.setType(TypePlace.B);
        request.setTrainId(train.getId());
        request.setUserId(1L);

        RequestService.getInstance().addRequest(request);

    }

    @Test
    public void addRequestTestRight() throws InvalidDataBaseOperation {
        DAOFactory factory = AbstractDAOFactory.createDAOFactory(DataBase.MYSQL);
        Train train = factory.createTrainDAO().findAll().get(0);
        train.setBerthFree(1L);
        factory.createTrainDAO().update(train);

        Request request = new Request();
        request.setStatus(0L);
        request.setPrice(200d);
        request.setType(TypePlace.B);
        request.setTrainId(train.getId());
        request.setUserId(1L);

        RequestService.getInstance().addRequest(request);
    }

    @Test
    public void findAllTicketsNotNull() {
        DAOFactory factory = AbstractDAOFactory.createDAOFactory(DataBase.MYSQL);
        List<Ticket> tickets = RequestService.getInstance().findAllTickets();

        assertNotNull(tickets);
    }

    @Test
    public void makeTicketWithNoneParameter() {
        TrainRoute route = new TrainRoute(1l, 1l, 1l, 1l,
                1l, "test", "test", "test", "test",
                1d, 1d, 1d, 1d);
        User user = new User(1l, "test", "test", "test", "test",
                "test", true);

        Ticket ticket = RequestService.getInstance().makeTicket("none", user, route);
        assertNull(ticket);
    }

    @Test
    public void makeTicketWithNormalParameter() {
        TrainRoute route = new TrainRoute(1l, 1l, 1l, 1l,
                1l, "test", "test", "test", "test",
                1d, 1d, 1d, 1d);
        User user = new User(1l, "test", "test", "test", "test",
                "test", true);

        Ticket ticket = RequestService.getInstance().makeTicket("C", user, route);
        assertNotNull(ticket);
    }

    @Test
    public void makeTicketAndCheckParameter() {
        TrainRoute route = new TrainRoute(1l, 1l, 1l, 1l,
                1l, "test", "test", "test", "test",
                1d, 1d, 1d, 1d);
        User user = new User(1l, "test", "test", "test", "test",
                "test", true);

        Ticket ticket = RequestService.getInstance().makeTicket("B", user, route);

        assertEquals(ticket.getTypePlace(), "B");
    }

}