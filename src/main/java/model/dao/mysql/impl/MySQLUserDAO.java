package model.dao.mysql.impl;

import controller.util.QueryManager;
import model.dao.mysql.connection.ConnectionPool;
import model.dao.mysql.mapper.UserDAO;
import model.dao.mysql.util.LogMessageDAOUtil;
import model.dao.mysql.util.QueryDAOUtil;
import model.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


class MySQLUserDAO implements UserDAO {

    private static final Logger LOG = LogManager.getLogger(MySQLUserDAO.class);
    private static volatile MySQLUserDAO INSTANCE = new MySQLUserDAO();

    private static final String TABLE_NAME = "user";
    private static final String LABEL_ID = "id";
    private static final String LABEL_EMAIL = "email";
    private static final String LABEL_PASSWORD = "password";
    private static final String LABEL_NAME = "name";
    private static final String LABEL_SURNAME = "surname";
    private static final String LABEL_PHONE = "phone";
    private static final String LABEL_ADMIN = "admin";
    private static final String SELECT_ALL_USERS_LIMIT_OFFSET = QueryManager.getProperty("userSelectAllLimitOffset");
    private static final String SELECT_COUNT_USERS = QueryManager.getProperty("userSelectCount");
    private String SELECT_ALL_USERS = QueryManager.getProperty("userSelectAll");
    private String CREATE_USER = QueryManager.getProperty("userCreate");
    private String UPDATE_USER = QueryManager.getProperty("userUpdate");
    private String DELETE_USER = QueryManager.getProperty("userDelete");


    private MySQLUserDAO() {
    }

    static MySQLUserDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet set = statement.executeQuery();

            while (set.next()) {
                result.add(getUser(set));
            }

            LOG.info(LogMessageDAOUtil.createInfoFindAll(TABLE_NAME));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorFindAll(TABLE_NAME));
        }

        return result;
    }

    @Override
    public int getUsersCount() {
        int result = 0;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_COUNT_USERS);
             ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                result = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            LOG.error("Error in obtaining 'number of users'", e.fillInStackTrace());
        }
        LOG.info("Number of users is :" + result);
        return result;
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        List<User> result = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS_LIMIT_OFFSET, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);

            ResultSet set = statement.executeQuery();
            while (set.next()) {
                result.add(getUser(set));
            }

            LOG.info(LogMessageDAOUtil.createInfoFindAll(TABLE_NAME));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorFindAll(TABLE_NAME));
        }

        return result;
    }

    @Override
    public User create(User user) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getPhone());
            statement.setBoolean(6, user.getAdmin());

            statement.executeUpdate();

            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                user.setId(set.getLong(1));
            }
            LOG.info(LogMessageDAOUtil.createInfoCreate(TABLE_NAME, user.getId()));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorCreate(TABLE_NAME));
        }
        return user;
    }

    @Override
    public User update(User user) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getPhone());
            statement.setBoolean(6, user.getAdmin());
            statement.setLong(7, user.getId());

            statement.executeUpdate();

            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                user.setId(set.getLong(1));
            }

            LOG.info(LogMessageDAOUtil.createInfoUpdate(TABLE_NAME, user.getId()));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createInfoUpdate(TABLE_NAME, user.getId()));
        }
        return user;
    }

    @Override
    public void delete(User user) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, user.getId());
            statement.executeUpdate();

            LOG.info(LogMessageDAOUtil.createInfoDelete(TABLE_NAME, user.getId()));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorCreate(TABLE_NAME));
            e.printStackTrace();
        }
    }

    private List<User> findByParameter(String label, Object parameter) {
        List<User> result = new ArrayList<>();
        String findByIdQuery = QueryDAOUtil.createFindByParameterQuery(TABLE_NAME, label);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(findByIdQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setObject(1, parameter);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                result.add(getUser(set));
            }

            LOG.info(LogMessageDAOUtil.createInfoFindByParameter(TABLE_NAME, label, parameter));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorFindByParameter(TABLE_NAME, label, parameter));
        }
        return result;
    }

    @Override
    public User findById(Long id) {
        List<User> result = findByParameter(LABEL_ID, id);
        if (result.size() != 1)
            return null;

        return result.get(0);
    }

    @Override
    public User findByEmail(String email) {
        List<User> result = findByParameter(LABEL_EMAIL, email);
        if (result.size() != 1)
            return null;

        return result.get(0);
    }


    private User getUser(ResultSet set) throws SQLException {
        User result = new User();

        result.setId(set.getLong(LABEL_ID));
        result.setEmail(set.getString(LABEL_EMAIL));
        result.setPassword(set.getString(LABEL_PASSWORD));
        result.setName(set.getString(LABEL_NAME));
        result.setSurname(set.getString(LABEL_SURNAME));
        result.setPhone(set.getString(LABEL_PHONE));
        result.setAdmin(set.getBoolean(LABEL_ADMIN));

        return result;
    }
}
