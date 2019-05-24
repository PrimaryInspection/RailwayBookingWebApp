package controller.command.admin;

import controller.command.common.Command;
import controller.util.Configuration;
import model.entity.User;
import model.service.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static controller.command.admin.CommandAdminUtil.USERNAME_ATTRIBUTE;
import static controller.command.admin.CommandAdminUtil.USERS_ATTRIBUTE;
import static controller.command.admin.CommandAdminUtil.USER_ATTRIBUTE;

public class ToNPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ToNPageCommand.class);


    /**
     * Move to the next page in the list of users
     *
     * @return next page path
     */
    @SuppressWarnings("all")
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
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
