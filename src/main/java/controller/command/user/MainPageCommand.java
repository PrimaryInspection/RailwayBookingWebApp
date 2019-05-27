package controller.command.user;

import controller.command.common.Command;
import controller.util.Configuration;
import model.entity.User;
import model.service.RouteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static controller.command.user.CommandUserUtil.*;

public class MainPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if(userNow == null)
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        request.setAttribute(CITIES_FROM_ATTRIBUTE, RouteService.getInstance().findAvailableFromStations());
        request.setAttribute(CITIES_TO_ATTRIBUTE, RouteService.getInstance().findAvailableToStations());
        request.setAttribute(TRAINS_ATTRIBUTE, null);
        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(DATE_NOW_ATTRIBUTE, format.format(new Date()));
        return Configuration.getInstance().getConfig(Configuration.DATE);
    }
}