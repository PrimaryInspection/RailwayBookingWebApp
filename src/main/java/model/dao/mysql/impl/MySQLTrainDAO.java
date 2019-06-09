package model.dao.mysql.impl;

import controller.util.QueryManager;
import model.dao.mysql.connection.ConnectionPool;
import model.dao.mysql.mapper.TrainDAO;
import model.dao.mysql.util.LogMessageDAOUtil;
import model.dao.mysql.util.QueryDAOUtil;
import model.entity.Train;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class MySQLTrainDAO implements TrainDAO {
    private static final Logger LOG = LogManager.getLogger(MySQLTrainDAO.class);
    private static volatile MySQLTrainDAO INSTANCE = new MySQLTrainDAO();

    private static final String TABLE_NAME = "train";
    private static final String LABEL_ID = "id";
    private static final String LABEL_ROUTE_ID = "routeId";
    private static final String LABEL_COMPARTMENT_FREE = "compartmentFree";
    private static final String LABEL_BERTH_FREE = "berthFree";
    private static final String LABEL_DELUXE_FREE = "deluxeFree";
    private String SELECT_ALL_TRAINS = QueryManager.getProperty("trainSelectAll");
    private String CREATE_TRAIN = QueryManager.getProperty("trainCreate");
    private String UPDATE_TRAIN = QueryManager.getProperty("trainUpdate");

    private MySQLTrainDAO() {
    }

    static MySQLTrainDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Train> findAll() {
        List<Train> result = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TRAINS, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet set = statement.executeQuery();

            while (set.next()) {
                result.add(getTrain(set));
            }

            LOG.info(LogMessageDAOUtil.createInfoFindAll(TABLE_NAME));
        } catch (SQLException e) {
            e.printStackTrace();

            LOG.error(LogMessageDAOUtil.createErrorFindAll(TABLE_NAME));
        }

        return result;
    }

    @Override
    public List<Train> findByRoute(Long route_id) {

        return findByParameter(LABEL_ROUTE_ID, route_id);
    }

    @Override
    public Train findById(Long id) {
        List<Train> result = findByParameter(LABEL_ID, id);
        if (result.size() != 1)
            return null;

        return result.get(0);
    }

    @Override
    public Train create(Train train) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_TRAIN, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, train.getRouteId());
            statement.setLong(2, train.getBerthFree());
            statement.setLong(3, train.getCompartmentFree());
            statement.setLong(4, train.getDeluxeFree());

            statement.executeUpdate();

            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                train.setId(set.getLong(1));
            }

            LOG.info(LogMessageDAOUtil.createInfoCreate(TABLE_NAME, train.getId()));
        } catch (SQLException e) {
            e.printStackTrace();

            LOG.error(LogMessageDAOUtil.createErrorCreate(TABLE_NAME));
        }
        return train;
    }

    @Override
    public Train update(Train train) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TRAIN, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, train.getId());
            statement.setLong(2, train.getRouteId());
            statement.setLong(3, train.getCompartmentFree());
            statement.setLong(4, train.getDeluxeFree());
            statement.setLong(5, train.getBerthFree());

            statement.executeUpdate();

            LOG.info(LogMessageDAOUtil.createInfoUpdate(TABLE_NAME, train.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error(LogMessageDAOUtil.createErrorUpdate(TABLE_NAME, train.getId()));
        }

        return train;
    }


    private List<Train> findByParameter(String label, Object parameter) {
        List<Train> result = new ArrayList<>();
        String findByIdQuery = QueryDAOUtil.createFindByParameterQuery(TABLE_NAME, label);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(findByIdQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, parameter);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                result.add(getTrain(set));
            }

            LOG.info(LogMessageDAOUtil.createInfoFindByParameter(TABLE_NAME, label, parameter));
        } catch (SQLException e) {
            e.printStackTrace();

            LOG.error(LogMessageDAOUtil.createErrorFindByParameter(TABLE_NAME, label, parameter));
        }

        return result;
    }

    private Train getTrain(ResultSet set) throws SQLException {
        Train result = new Train();

        result.setId(set.getLong(LABEL_ID));
        result.setRouteId(set.getLong(LABEL_ROUTE_ID));
        result.setBerthFree(set.getLong(LABEL_BERTH_FREE));
        result.setCompartmentFree(set.getLong(LABEL_COMPARTMENT_FREE));
        result.setDeluxeFree(set.getLong(LABEL_DELUXE_FREE));

        return result;
    }


}
