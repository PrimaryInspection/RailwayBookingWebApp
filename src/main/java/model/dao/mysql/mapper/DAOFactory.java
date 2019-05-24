package model.dao.mysql.mapper;

/**
 * Interface for different DAO Factories
 * @author Andrii Mishko
 * @version 1.0
 */
public interface DAOFactory {
    PriceDAO createPriceDAO();
    RequestDAO createRequestDAO();
    RouteDAO createRouteDAO();
    TrainDAO createTrainDAO();
    UserDAO createUserDAO();
    StationDAO createStationDAO();
}
