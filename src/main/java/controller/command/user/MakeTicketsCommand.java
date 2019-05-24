package controller.command.user;

import controller.command.common.Command;
import controller.util.Configuration;
import model.dto.Ticket;
import model.dto.TrainRoute;
import model.entity.User;
import model.service.RequestService;
import model.service.TrainService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static controller.command.user.CommandUserUtil.*;

public class MakeTicketsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if(userNow == null)
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        String page = Configuration.getInstance().getConfig(Configuration.ORDER);
        Long from_id = Long.parseLong(request.getParameter(FROM_PARAMETER));
        Long to_id = Long.parseLong(request.getParameter(TO_PARAMETER));
        Integer time = Integer.parseInt(request.getParameter(TIME_PARAMETER));
        String dateString = request.getParameter(DATE_ATTRIBUTE);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        Date date = null;
        try {
            date = format.parse(dateString);
            date.setHours(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        User user = (User) request.getSession().getAttribute(USER_ATTRIBUTE);
        List<TrainRoute> trains = TrainService.getInstance().findTrainsAndRoutes(from_id, to_id, date);
        List<Ticket> tickets = new ArrayList<>();
        for (TrainRoute trainRoute : trains) {
            String parameter = request.getParameter(TRAIN_PARAMETER + trainRoute.getTrainId());

            Ticket ticket = RequestService.getInstance().makeTicket(parameter, user, trainRoute);
            if (ticket != null){
                tickets.add(ticket);
            }
        }

        if (tickets.isEmpty()){
            request.setAttribute(NO_TICKETS_ATTRIBUTE, true);
        } else{
            request.setAttribute(TICKETS_ATTRIBUTE, tickets);
            request.getSession(false).setAttribute(TICKETS_ATTRIBUTE, tickets);
        }
        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        return page;
    }
}
