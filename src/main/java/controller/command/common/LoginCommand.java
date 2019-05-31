package controller.command.common;

import controller.util.Configuration;
import model.entity.User;
import model.service.AdminService;
import model.service.LoginService;
import model.service.RouteService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static controller.command.common.CommandUtil.*;

public class LoginCommand implements Command {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    public static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    /**
     * Command for log in
     *
     *@param request - HTTP Servlet request
     *@param response - HTTP Servlet response
     *@throws ServletException,IOException
     *@return page
     * */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        String email = request.getParameter(EMAIL).trim();
        String password = request.getParameter(PASSWORD).trim();

        User user = LoginService.getInstance().isPresentLogin(email);

        if(user == null){
            page = redirectToErrorPage(request);
        } else {
            page = checkIfCorrectPassword(user, request, password);
        }
        return page;
    }

    /**
     * Method for checking password input
     *
     *@param request - HTTP Servlet request
     *@param user - instance of User's entity
     *@param inputPassword - user's password
     *@throws ServletException,IOException
     *@return page
     * */
    private String checkIfCorrectPassword(User user, HttpServletRequest request, String inputPassword){
        String page = null;
        if (LoginService.getInstance().checkPassword(user, inputPassword)) {
            page = checkIfAdmin(user, request);
            LOG.info("Succsesfull checking password.");
        } else {
            page = redirectToErrorPage(request);
            LOG.error("Exception during checking password: " + inputPassword);
        }

        return page;
    }

    /**
     * Method for authorization
     *
     *@param request - HTTP Servlet request
     *@param user - instance of User's entity
     *@throws ServletException,IOException
     *@return page
     * */
    private String checkIfAdmin(User user, HttpServletRequest request){
        String page = null;
        if(user.isAdmin()){
            page = redirectToAdminPage(request, user);
            LOG.info("User is: "+ user.getName() + ", redirect to Admin's page.");
        } else {
            page = redirectToUserPage(request, user);
            LOG.info("User is: "+ user.getName() + ", redirect to User's page.");

        }
        return page;
    }

    private String redirectToAdminPage(HttpServletRequest request, User user){
        HttpSession session = request.getSession(false);
        session.setAttribute(USER_ATTRIBUTE, user);
        session.setAttribute(USERS_ATTRIBUTE, AdminService.getInstance().getAllUsersAdmin(request));
        request.setAttribute(USERNAME_ATTRIBUTE,user.getName());
        return Configuration.getInstance().getConfig(Configuration.ADMIN);
    }

    private String redirectToUserPage(HttpServletRequest request, User user){
        HttpSession session = request.getSession(false);
        session.setAttribute(USER_ATTRIBUTE, user);

        request.setAttribute(CITIES_FROM_ATTRIBUTE, RouteService.getInstance().findAvailableFromStations());
        request.setAttribute(CITIES_TO_ATTRIBUTE, RouteService.getInstance().findAvailableToStations());
        request.setAttribute(TRAINS_ATTRIBUTE, null);

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        request.setAttribute(DATE_NOW_ATTRIBUTE, format.format(new Date()));

        request.setAttribute(USERNAME_ATTRIBUTE, user.getName());
        return Configuration.getInstance().getConfig(Configuration.DATE);
    }

    private String redirectToErrorPage(HttpServletRequest request){
        request.setAttribute(ERROR_MESSAGE, true);
        return Configuration.getInstance().getConfig(Configuration.LOGIN);
    }
}
