package model.dao.mysql.impl;


import controller.util.QueryManager;
import model.dao.mysql.connection.ConnectionPool;
import model.dao.mysql.mapper.RouteDAO;
import model.dao.mysql.util.LogMessageDAOUtil;
import model.dao.mysql.util.QueryDAOUtil;
import model.entity.Route;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class MySQLRouteDAO implements RouteDAO {
    private static final Logger LOG = LogManager.getLogger(MySQLRouteDAO.class);
    private static final MySQLRouteDAO INSTANCE = new MySQLRouteDAO();

    private static final String TABLE_NAME = "route";
    private static final String LABEL_ID = "id";
    private static final String LABEL_PRICE_ID = "priceId";
    private static final String LABEL_FROM_ID = "fromId";
    private static final String LABEL_TO_ID = "toId";
    private static final String LABEL_FROM_TIME = "fromTime";
    private static final String LABEL_TO_TIME = "toTime";
    private static final String LABEL_DISTANCE = "distance";
    private String SELECT_ALL_ROUTES = QueryManager.getProperty("routeSelectAll");
    private String CREATE_ROUTE = QueryManager.getProperty("routeCreate");
    private String UPDATE_ROUTE = QueryManager.getProperty("routeUpdate");

    private MySQLRouteDAO() {
    }

    static MySQLRouteDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Route> findAll() {
        List<Route> result = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ROUTES, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                result.add(getRoute(set));
            }

            LOG.info(LogMessageDAOUtil.createInfoFindAll(TABLE_NAME));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorFindAll(TABLE_NAME));
        }

        return result;
    }

    @Override
    public Route findById(Long id) {
        List<Route> result = findByParameter(LABEL_ID, id);
        if (result.size() != 1)
            return null;

        return result.get(0);
    }

    @Override
    public List<Route> findByFromId(Long id) {

        return findByParameter(LABEL_FROM_ID, id);
    }

    @Override
    public Route create(Route route) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ROUTE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, route.getPriceId());
            statement.setLong(2, route.getFromId());
            statement.setLong(3, route.getToId());
            statement.setString(4, route.getFromTime());
            statement.setString(5, route.getToTime());
            statement.setDouble(6, route.getDistance());

            statement.executeUpdate();

            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                route.setId(set.getLong(1));
            }

            LOG.info(LogMessageDAOUtil.createInfoCreate(TABLE_NAME, route.getId()));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorCreate(TABLE_NAME));
        }

        return route;
    }


    @Override
    public Route update(Route route) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ROUTE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, route.getPriceId());
            statement.setLong(2, route.getFromId());
            statement.setLong(3, route.getToId());
            statement.setString(4, route.getFromTime());
            statement.setString(5, route.getToTime());
            statement.setDouble(6, route.getDistance());
            statement.setLong(7, route.getId());

            statement.executeUpdate();

            LOG.info(LogMessageDAOUtil.createInfoUpdate(TABLE_NAME, route.getId()));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createInfoUpdate(TABLE_NAME, route.getId()));
        }

        return route;
    }


    private Route getRoute(ResultSet set) throws SQLException {
        Route result = new Route();
        result.setId(set.getLong(LABEL_ID));

        result.setPriceId(set.getLong(LABEL_PRICE_ID));

        result.setFromId(set.getLong(LABEL_FROM_ID));
        result.setToId(set.getLong(LABEL_TO_ID));

        result.setFromTime(set.getString(LABEL_FROM_TIME));
        result.setToTime(set.getString(LABEL_TO_TIME));

        result.setDistance(set.getDouble(LABEL_DISTANCE));

        return result;
    }

    private List<Route> findByParameter(String label, Long parameter) {
        List<Route> result = new ArrayList<>();
        String findByIdQuery = QueryDAOUtil.createFindByParameterQuery(TABLE_NAME, label);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(findByIdQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, parameter);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                result.add(getRoute(set));
            }

            LOG.info(LogMessageDAOUtil.createInfoFindByParameter(TABLE_NAME, label, parameter));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorFindByParameter(TABLE_NAME, label, parameter));
            result = null;
        }

        return result;
    }


}