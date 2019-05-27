package controller.command.common;

import controller.util.Configuration;
import model.entity.User;
import model.service.LoginService;
import model.validation.IRegistationValidation;
import model.validation.imp.RegistrationValidation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements Command {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PHONE = "phone";
    public static final Logger LOG = LogManager.getLogger(RegisterCommand.class);
    private IRegistationValidation validation = new RegistrationValidation();


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = Configuration.getInstance().getConfig(Configuration.REGISTER);
        String email = request.getParameter(EMAIL).trim();
        String password = request.getParameter(PASSWORD).trim();
        String name = request.getParameter(NAME).trim();
        String surname = request.getParameter(SURNAME).trim();
        String phone = request.getParameter(PHONE).trim();

        if(!isEmailInpValid(request)){
            request.setAttribute("errorMessage", true);
            LOG.info("Uncorrect EMAIL");
            return page;
        }

        if(!isPassInpValid(request)){
            request.setAttribute("errorMessage", true);
            LOG.info("Uncorrect PASS");
            return page;
        }

        if (!isFirstNameInpValid(request)) {
            request.setAttribute("errorMessage", true);
            LOG.info("Uncorrect FIRST_NAME");
            return page;
        }

        if (!isSecondNameInpValid(request)) {
            request.setAttribute("errorMessage", true);
            LOG.info("Uncorrect SURNAME");
            return page;
        }

        if(!isPhoneInpValid(request)){
            request.setAttribute("errorMessage", true);
            LOG.info("Uncorrect PHONE");
            return page;
        }

        if(LoginService.getInstance().isPresentLogin(email) == null){
            User user = new User.Builder()
                    .setEmail(email)
                    .setPassword(password)
                    .setName(name)
                    .setPhone(phone)
                    .setSurname(surname)
                    .isAdmin(false)
                    .build();

            user = LoginService.getInstance().addUser(user);
            if(user.getId() == null) {
                page = Configuration.getInstance().getConfig(Configuration.REGISTER);
                LOG.error("User id =" + user.getId() + ", user was NOT created.");
            }
            else
                page =  Configuration.getInstance().getConfig(Configuration.LOGIN);
                 LOG.info("User id =" + user.getId() + ", user was created.");


        } else {
            request.setAttribute("errorMessage", true);
        }
        return page;
    }

    private boolean isEmailInpValid(HttpServletRequest request) {
        if (validation.isEmailValid(request.getParameter(EMAIL))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isFirstNameInpValid(HttpServletRequest request) {
        if (validation.isFirsNameUAValid(request.getParameter(NAME)) ||
                validation.isFirsNameENValid(request.getParameter(NAME))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isSecondNameInpValid(HttpServletRequest request) {
        if (validation.isSecondNameUAValid(request.getParameter(SURNAME)) ||
                validation.isSecondNameENValid(request.getParameter(SURNAME))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isPassInpValid(HttpServletRequest request) {
        if (validation.isPasswordValid(request.getParameter(PASSWORD))) {
            return true;
        } else{
        return false;
        }
    }


    private boolean isPhoneInpValid(HttpServletRequest request) {
        if (validation.isPhoneValid(request.getParameter(PHONE))) {
            return true;
        } else {
            return false;
        }
    }
}
