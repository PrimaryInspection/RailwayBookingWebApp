package controller.command.admin;



import controller.command.common.Command;
import controller.util.Configuration;
import model.dto.Ticket;
import model.entity.User;
import model.exception.InvalidDataBaseOperation;
import model.service.RequestService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static controller.command.admin.CommandAdminUtil.*;


public class CancelAllTicketsCommand implements Command {
    public static final Logger LOG = LogManager.getLogger(ApproveCommand.class);

    /**
     * Command for canceling all of user's tickets on admin's page
     *
     *@param request - HTTP Servlet request
     *@param response - HTTP Servlet response
     *@throws ServletException,IOException
     *@return page - /WEB-INF/admin/tickets.jsp
     * */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = Configuration.getInstance().getConfig(Configuration.TICKETS_ADMIN);
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        if (userNow == null || !userNow.isAdmin())
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        List<Ticket> tickets = RequestService.getInstance().findAllTickets();
        try {
            RequestService.getInstance().cancelRequest(tickets);
            LOG.info("Succesfull canceled all tickets.");
        } catch (InvalidDataBaseOperation e) {
            request.setAttribute(MESSAGE_ERROR, e.getMessage());
            return Configuration.getInstance().getConfig(Configuration.ERROR);
        }

        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(TICKETS_ATTRIBUTE, RequestService.getInstance().findAllTickets());

        return page;
    }
}
