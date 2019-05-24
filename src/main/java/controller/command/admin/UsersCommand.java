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

import static controller.command.admin.CommandAdminUtil.USERNAME_ATTRIBUTE;
import static controller.command.admin.CommandAdminUtil.USERS_ATTRIBUTE;
import static controller.command.admin.CommandAdminUtil.USER_ATTRIBUTE;

public class UsersCommand implements Command {
    @SuppressWarnings("all")
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page =  Configuration.getInstance().getConfig(Configuration.ADMIN);
        HttpSession session = request.getSession();
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        request.getSession(false).getAttribute(USERS_ATTRIBUTE);

        if (userNow == null || !userNow.isAdmin()){
            return Configuration.getInstance().getConfig(Configuration.LOGIN);
        }
        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());
        session.setAttribute(USERS_ATTRIBUTE, AdminService.getInstance().getAllUsersAdmin(request));
        return page;
    }
}
