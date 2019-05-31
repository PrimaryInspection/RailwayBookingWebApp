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
import java.util.ArrayList;
import java.util.List;

import static controller.command.admin.CommandAdminUtil.*;


public class CancelTicketsCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(ApproveCommand.class);

    /**
     * Command for canceling one single user's ticket on admin's page
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

        List<Ticket> result = new ArrayList<>();
        List<Ticket> tickets = RequestService.getInstance().findAllTickets();
        for (Ticket ticket : tickets) {
            if (request.getParameter(ticket.getRequestId().toString()).equals(CANCEL))
                result.add(ticket);
        }

        try {
            RequestService.getInstance().cancelRequest(result);
            LOG.info("Succesfull canceled ticket.");
        } catch (InvalidDataBaseOperation e) {
            request.setAttribute(MESSAGE_ERROR, e.getMessage());
            return Configuration.getInstance().getConfig(Configuration.ERROR);
        }

        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(TICKETS_ATTRIBUTE, RequestService.getInstance().findAllTickets());

        return page;
    }
}
