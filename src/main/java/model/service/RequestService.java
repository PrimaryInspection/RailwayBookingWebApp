package model.service;


import model.dao.mysql.mapper.AbstractDAOFactory;
import model.dao.mysql.mapper.DAOFactory;
import model.dao.mysql.mapper.DataBase;
import model.dao.mysql.mapper.TypePlace;
import model.dto.Ticket;
import model.dto.TrainRoute;
import model.entity.Request;
import model.entity.Route;
import model.entity.Train;
import model.entity.User;
import model.exception.InvalidDataBaseOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class RequestService {
    private static final Logger LOG = LogManager.getLogger(RequestService.class);
    private static final DataBase DB = DataBase.MYSQL;
    private static volatile RequestService INSTANCE;

    private DAOFactory factory;

    private RequestService() {
        factory = AbstractDAOFactory.createDAOFactory(DB);
    }

    public static RequestService getInstance() {
        if (INSTANCE == null) {
            synchronized (RequestService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RequestService();
                }
            }
        }

        return INSTANCE;
    }

    /**
     * Create user's request in database
     * @param request - HTTP Servlet request
     * @return factory.createRequestDAO().create(request) - Request request
     *
     * */
    public Request addRequest(Request request) throws InvalidDataBaseOperation {
        TypePlace place = request.getType();
        Train train = factory.createTrainDAO().findById(request.getTrainId());

        switch (place) {
            case B: {
                if (train.getBerthFree() == 0) {
                    throw new InvalidDataBaseOperation("Someone booked all tickets to this train." +
                            " Please, go to the main page and select other train");
                }
                TrainService.getInstance().reserveBerthPlace(train);
                break;
            }
            case C: {
                if (train.getCompartmentFree() == 0) {
                    throw new InvalidDataBaseOperation("Someone booked all tickets to this train." +
                            " Please, go to the main page and select other train");
                }
                TrainService.getInstance().reserveCompartmentPlace(train);
                break;
            }
            case L: {
                if (train.getDeluxeFree() == 0) {
                    throw new InvalidDataBaseOperation("Someone booked all tickets to this train." +
                            " Please, go to the main page and select other train");
                }
                TrainService.getInstance().reserveDeluxePlace(train);
                break;
            }
        }

        return factory.createRequestDAO().create(request);
    }

    /**
     * Creating pre-sale tickets(reserving)
     * @param tickets - reserved tickets for user
     * @throws InvalidDataBaseOperation
     *
     * */
    public void reserveTickets(final List<Ticket> tickets) throws InvalidDataBaseOperation {
        for (Ticket ticket : tickets) {
            Request request = new Request.RequestBuilder()
                    .setPrice(ticket.getPrice())
                    .setType(TypePlace.valueOf(ticket.getTypePlace()))
                    .setUserId(ticket.getUserId())
                    .setTrainId(ticket.getTrainId())
                    .setStatus(ticket.getStatus())
                    .build();

            ticket.setRequestId(addRequest(request).getId());
        }
    }

    /**
     * Creating tickets
     * @param parameter - type of ticket(C,B,L)
     * @param user - instance of User entity
     * @param trainRoute - info about train for creating tickets
     * @return ticket
     *
     * */
    public Ticket makeTicket(String parameter, User user, TrainRoute trainRoute) {
        if (!parameter.equals("none")) {
            Ticket ticket = new Ticket();
            ticket.setTrainId(trainRoute.getTrainId());

            ticket.setFromCity(trainRoute.getFromCity());
            ticket.setToCity(trainRoute.getToCity());

            ticket.setFromDate(trainRoute.getFromDate());
            ticket.setToDate(trainRoute.getToDate());

            ticket.setName(user.getName());
            ticket.setSurname(user.getSurname());

            Double price;
            Long max;
            Route route = RouteService.getInstance().findRouteById(trainRoute.getRouteId());
            switch (parameter) {
                case "C": {
                    max = trainRoute.getCompartmentFree();
                    price = RouteService.getInstance().findCompartmentPrice(route);
                    break;
                }
                case "L": {
                    max = trainRoute.getDeluxeFree();
                    price = RouteService.getInstance().findDeluxePrice(route);
                    break;
                }
                default: {
                    max = trainRoute.getBerthFree();
                    price = RouteService.getInstance().findBerthPrice(route);
                    break;
                }
            }
            ticket.setMax(max);
            ticket.setTypePlace(parameter);
            ticket.setPrice(price);
            ticket.setUserId(user.getId());
            ticket.setStatus(0L);
            LOG.info("Add Ticket for USER ID = " + user.getId());
            return ticket;
        }
        return null;
    }

    /**
     * Getting all tickets from database
     *
     * @return List<Ticket> result
     *
     * */
    public List<Ticket> findAllTickets() {
        List<Request> requests = factory.createRequestDAO().findAll();
        List<Ticket> result = new ArrayList<>();
        for (Request request : requests) {
            Train train = factory.createTrainDAO().findById(request.getTrainId());
            Route route = factory.createRouteDAO().findById(train.getRouteId());
            User user = factory.createUserDAO().findById(request.getUserId());

            Ticket ticket = new Ticket();
            ticket.setTrainId(train.getId());
            ticket.setRequestId(request.getId());
            ticket.setUserId(request.getUserId());

            ticket.setFromCity(factory.createStationDAO().findById(route.getFromId()).getName());
            ticket.setToCity(factory.createStationDAO().findById(route.getToId()).getName());

            ticket.setFromDate(TrainService.getInstance().formatDate(route.getFromTime()));
            ticket.setToDate(TrainService.getInstance().formatDate(route.getToTime()));

            ticket.setName(user.getName());
            ticket.setSurname(user.getSurname());

            ticket.setTypePlace(request.getType().toString());
            ticket.setPrice(request.getPrice());
            ticket.setUserId(user.getId());
            ticket.setStatus(request.getStatus());
            result.add(ticket);
        }

        result.sort(new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                if (o1.getRequestId() > o2.getRequestId()) return -1;
                else if (o1.getRequestId() < o2.getRequestId()) return 1;
                else return 0;
            }
        });
        LOG.info("Find All Tickets");
        return result;
    }

    public List<Ticket> addTickets(Ticket ticket, Integer count) {
        List<Ticket> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Ticket ticket1 = new Ticket();
            ticket1.setRequestId(ticket.getRequestId());
            ticket1.setUserId(ticket.getUserId());
            ticket1.setPrice(ticket.getPrice());
            ticket1.setTypePlace(ticket.getTypePlace());
            ticket1.setSurname(ticket.getSurname());
            ticket1.setName(ticket.getName());
            ticket1.setFromDate(ticket.getFromDate());
            ticket1.setToDate(ticket.getToDate());
            ticket1.setFromCity(ticket.getFromCity());
            ticket1.setToCity(ticket.getToCity());
            ticket1.setMax(ticket.getMax());
            ticket1.setTrainId(ticket.getTrainId());
            ticket1.setStatus(0L);
            result.add(ticket1);
        }

        LOG.info("Add Ticket to REQUEST");
        return result;
    }
    /**
     * Change user's ticket status to 'canceled'
     * @param tickets - list of tickets
     * @throws  InvalidDataBaseOperation
     * */
    public void cancelRequest(List<Ticket> tickets) throws InvalidDataBaseOperation {
        List<Request> requests = new ArrayList<>();
        for (Ticket ticket1 : tickets) {
            requests.add(factory.createRequestDAO().findById(ticket1.getRequestId()));
        }
        factory.createRequestDAO().deleteRequests(requests);
    }

    /**
     * Change user's ticket status to 'approved'
     * @param tickets - list of tickets
     * @throws  InvalidDataBaseOperation
     * */
    public void approveRequest(List<Ticket> tickets) throws InvalidDataBaseOperation {
        List<Request> requests = new ArrayList<>();
        for (Ticket ticket1 : tickets) {
            requests.add(factory.createRequestDAO().findById(ticket1.getRequestId()));
        }
        factory.createRequestDAO().approveRequests(requests);
    }
}
