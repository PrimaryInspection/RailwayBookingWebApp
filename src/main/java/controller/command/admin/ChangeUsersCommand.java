package controller.command.admin;

import controller.command.common.Command;
import controller.util.Configuration;
import model.entity.User;
import model.service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static controller.command.admin.CommandAdminUtil.*;


public class ChangeUsersCommand implements Command {
    /**
     * Command for changing users status on admin's page
     *
     *@param request - HTTP Servlet request
     *@param response - HTTP Servlet response
     *@throws ServletException,IOException
     *@return page - /WEB-INF/admin/users.jsp
     * */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = Configuration.getInstance().getConfig(Configuration.ADMIN);
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        HttpSession session = request.getSession();
        if (userNow == null || !userNow.isAdmin())
            return Configuration.getInstance().getConfig(Configuration.LOGIN);
        List<User> users = (List<User>) request.getSession(false).getAttribute(USERS_ATTRIBUTE);
        for (User user : users) {
            switch (request.getParameter(user.getId().toString())) {
                case DELETE:
                    AdminService.getInstance().deleteUser(user);
                    break;
                case ADMIN:
                    user.makeAdmin();
                    AdminService.getInstance().updateUser(user);
                    break;
                case USER:
                    user.makeUser();
                    AdminService.getInstance().updateUser(user);
                    break;
                default:
                    break;

            }
        }

        session.setAttribute(USERS_ATTRIBUTE,users);
        request.setAttribute(USERNAME_ATTRIBUTE,userNow.getName());
        session.setAttribute(USER_ATTRIBUTE,userNow);

        return page;
    }
}
