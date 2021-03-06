package controller.command.admin;



import controller.command.common.Command;
import controller.util.Configuration;
import model.entity.User;
import model.service.RequestService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.command.admin.CommandAdminUtil.TICKETS_ATTRIBUTE;
import static controller.command.admin.CommandAdminUtil.USERNAME_ATTRIBUTE;
import static controller.command.admin.CommandAdminUtil.USER_ATTRIBUTE;


public class TicketCommand implements Command {
    /**
     * Command for displaying tickets on page
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
        if(userNow == null || !userNow.isAdmin())
            return Configuration.getInstance().getConfig(Configuration.LOGIN);

        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        request.setAttribute(TICKETS_ATTRIBUTE, RequestService.getInstance().findAllTickets());
        return page;
    }
}
