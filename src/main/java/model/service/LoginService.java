package model.service;

import model.dao.mysql.mapper.AbstractDAOFactory;
import model.dao.mysql.mapper.DAOFactory;
import model.dao.mysql.mapper.DataBase;
import model.entity.User;
import model.service.util.LogMessageServiceUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class LoginService {
    private static final Logger LOG = LogManager.getLogger(LoginService.class);
    private static final DataBase DB = DataBase.MYSQL;
    private static final String USER_DAO = "UserDAO";
    private static final String ADD_USER = "addUser()";
    private static final String IS_PRESENT_LOGIN = "isPresentUser()";
    private static LoginService INSTANCE;
    private DAOFactory factory;

    private LoginService() {
        factory = AbstractDAOFactory.createDAOFactory(DB);
    }

    public static LoginService getInstance() {
        if (INSTANCE == null) {
            synchronized (LoginService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoginService();
                }
            }
        }

        return INSTANCE;
    }

    public User isPresentLogin(String login) {
        User user = factory.createUserDAO().findByEmail(login);
        return user;
    }

    public User addUser(User user) {
        user = securePassword(user);
        User createdUser = factory.createUserDAO().create(user);
        if (createdUser == null) {
            LOG.error(LogMessageServiceUtil.createMethodError(USER_DAO, ADD_USER));
        }

        LOG.info(LogMessageServiceUtil.createMethodInfo(USER_DAO, ADD_USER));
        return createdUser;
    }

    public boolean checkPassword(User user, String password) {
        String securePassword = DigestUtils.md5Hex(password);
        return securePassword.equals(user.getPassword());
    }

    User securePassword(final User user) {
        String securePassword = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(securePassword);
        return user;
    }
}
