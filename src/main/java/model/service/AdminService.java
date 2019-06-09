package model.service;

import model.dao.mysql.mapper.AbstractDAOFactory;
import model.dao.mysql.mapper.DAOFactory;
import model.dao.mysql.mapper.DataBase;
import model.entity.User;
import model.service.util.LogMessageServiceUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AdminService {
    private static final Logger LOG = LogManager.getLogger(AdminService.class);
    private static final DataBase DB = DataBase.MYSQL;
    private static final String USER_DAO = "UserDAO";
    private static final String UPDATE_USER = "updateUser()";
    private static final String DELETE_USER = "deleteUser()";
    private static final String PAGE_NUMBER = "pageNumber";
    private static final String USER_ATTRIBUTE = "user";
    private static final String USERNAME_ATTRIBUTE = "username";
    private static final String USERS_ATTRIBUTE = "users";


    private static volatile AdminService INSTANCE;
    private DAOFactory factory;

    private AdminService() {
        factory = AbstractDAOFactory.createDAOFactory(DB);
    }

    public static AdminService getInstance() {
        if (INSTANCE == null) {
            synchronized (AdminService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AdminService();
                }
            }
        }

        return INSTANCE;
    }

    /**
     * Getting all users and pagination logic
     * @param request - HTTP Servlet request
     * @return users - list of users
     *
     * */
    public List<User> getAllUsersAdmin(HttpServletRequest request) {
        User userNow = (User) request.getSession(false).getAttribute(USER_ATTRIBUTE);
        HttpSession session = request.getSession(false);

        int size = getUsersCount();
        int offset = 0;
        int limit = 5;
        int pages = 1;
        if (request.getParameter(PAGE_NUMBER) != null) {
            int requestedPage = Integer.valueOf(request.getParameter(PAGE_NUMBER));
            offset = (requestedPage - 1) * limit;

        }
        List<User> users = getUsers(limit, offset);
        session.setAttribute(USERS_ATTRIBUTE, users);
        request.setAttribute(USERNAME_ATTRIBUTE, userNow.getName());

        if (size > limit) {
            pages = size / limit;
            if (size % limit > 0) {
                pages++;
            }
        }
        LOG.debug("Total number of pages: " + pages);
        session.setAttribute("pages", pages);

        int currentPage = offset / limit + 1;
        LOG.debug("Current page number: " + currentPage);
        session.setAttribute("currentPage", currentPage);
        return users;
    }

    public int getUsersCount() {
        return factory.createUserDAO().getUsersCount();
    }

    public List<User> getUsers(int limit, int offset) {

        return factory.createUserDAO().findAll(limit, offset);
    }

    public User updateUser(User user) {
        User updatedUser = factory.createUserDAO().update(user);
        if (updatedUser == null) {
            LOG.error(LogMessageServiceUtil.createMethodError(USER_DAO, UPDATE_USER));
        }

        LOG.info(LogMessageServiceUtil.createMethodInfo(USER_DAO, UPDATE_USER));
        return updatedUser;
    }

    public void deleteUser(User user) {
        factory.createUserDAO().delete(user);
        LOG.info(LogMessageServiceUtil.createMethodInfo(USER_DAO, DELETE_USER));
    }

}
