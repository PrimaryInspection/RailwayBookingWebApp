package model.dao.mysql.impl;

import model.dao.mysql.mapper.*;

public class MySQLFactory implements DAOFactory {
    @Override
    public PriceDAO createPriceDAO() {
        return MySQLPriceDAO.getInstance();
    }

    @Override
    public RequestDAO createRequestDAO() {
        return MySQLRequestDAO.getInstance();
    }

    @Override
    public RouteDAO createRouteDAO() {
        return MySQLRouteDAO.getInstance();
    }

    @Override
    public TrainDAO createTrainDAO() {
        return MySQLTrainDAO.getInstance();
    }

    @Override
    public UserDAO createUserDAO() {
        return MySQLUserDAO.getInstance();
    }

    @Override
    public StationDAO createStationDAO() {
        return MySQLStationDAO.getInstance();
    }
}
