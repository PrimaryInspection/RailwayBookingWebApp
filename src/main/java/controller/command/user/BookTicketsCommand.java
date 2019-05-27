package controller.command.user;



import controller.command.common.Command;
import controller.util.Configuration;
import model.dto.Ticket;
import model.entity.User;
import model.exception.InvalidDataBaseOperation;
import model.service.RequestService;
import model.service.RouteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static controller.command.user.CommandUserUtil.*;


public class BookTicketsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if(userNow == null)
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        String page = Configuration.getInstance().getConfig(Configuration.TICKET);

        List<Ticket> tickets = (List<Ticket>) request.getSession(false).getAttribute(TICKETS_ATTRIBUTE);
        if(tickets == null){
            request.setAttribute(CITIES_FROM_ATTRIBUTE, RouteService.getInstance().findAvailableFromStations());
            request.setAttribute(CITIES_TO_ATTRIBUTE, RouteService.getInstance().findAvailableToStations());
            request.setAttribute(TRAINS_ATTRIBUTE, null);

            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            request.setAttribute(DATE_NOW_ATTRIBUTE, format.format(new Date()));
            page = Configuration.getInstance().getConfig(Configuration.DATE);
        } else {
            List<Ticket> resultTickets = new ArrayList<>();

            for (Ticket ticket : tickets) {
                Integer count = Integer.parseInt(request.getParameter(ticket.getTrainId().toString()));
                resultTickets.addAll(RequestService.getInstance().addTickets(ticket, count));
            }

            try {
                RequestService.getInstance().reserveTickets(resultTickets);
                request.setAttribute(TICKETS_ATTRIBUTE, resultTickets);
            } catch (InvalidDataBaseOperation e){
                request.setAttribute(MESSAGE_ERROR_ATTRIBUTE, e.getMessage());
                page = Configuration.getInstance().getConfig(Configuration.ERROR);
            }

        }
        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        return page;
    }
}