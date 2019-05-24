package controller.command.common;

import controller.util.Configuration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sun.rmi.log.LogOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements Command {
    public static final Logger LOG = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = Configuration.getInstance().getConfig(Configuration.LOGIN);
        HttpSession session = request.getSession(false);
        if(session.getAttribute("user") != null){
            session.removeAttribute("user");
            session.invalidate();
            LOG.info("Logged out. Session" + session.getId() + "was destroyed.");

        }
        return page;
    }
}
