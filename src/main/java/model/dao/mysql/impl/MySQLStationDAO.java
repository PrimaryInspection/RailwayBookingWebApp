package model.dao.mysql.impl;

import controller.util.QueryManager;
import model.dao.mysql.connection.ConnectionPool;
import model.dao.mysql.mapper.StationDAO;
import model.dao.mysql.util.LogMessageDAOUtil;
import model.dao.mysql.util.QueryDAOUtil;
import model.entity.Station;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class MySQLStationDAO implements StationDAO {
    private static final Logger LOG = LogManager.getLogger(MySQLStationDAO.class);
    private static final MySQLStationDAO INSTANCE = new MySQLStationDAO();

    private static final String TABLE_NAME = "station";
    private static final String LABEL_ID = "id";
    private static final String LABEL_NAME = "name";
    private String SELECT_ALL_STATIONS = QueryManager.getProperty("stationSelectAll");
    private String CREATE_STATION = QueryManager.getProperty("stationCreate");
    private String UPDATE_STATION = QueryManager.getProperty("stationUpdate");

    private MySQLStationDAO() {
    }

    static MySQLStationDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Station> findAll() {
        List<Station> result = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STATIONS, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet set = statement.executeQuery();

            while (set.next()) {
                result.add(getStation(set));
            }

            LOG.info(LogMessageDAOUtil.createInfoFindAll(TABLE_NAME));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorFindAll(TABLE_NAME));
        }

        return result;
    }

    @Override
    public Station findById(Long id) {
        Station result = null;
        String findByIdQuery = QueryDAOUtil.createFindByParameterQuery(TABLE_NAME, LABEL_ID);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(findByIdQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                result = getStation(set);
            }

            LOG.info(LogMessageDAOUtil.createInfoFindByParameter(TABLE_NAME, LABEL_ID, id));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorFindByParameter(TABLE_NAME, LABEL_ID, id));
        }

        return result;
    }


    @Override
    public Station create(Station station) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_STATION, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, station.getName());

            statement.executeUpdate();

            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                station.setId(set.getLong(1));
            }

            LOG.info(LogMessageDAOUtil.createInfoCreate(TABLE_NAME, station.getId()));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorCreate(TABLE_NAME));
        }

        return station;
    }

    @Override
    public Station update(Station station) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATION, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, station.getName());
            statement.setLong(2, station.getId());

            statement.executeUpdate();

            LOG.info(LogMessageDAOUtil.createInfoUpdate(TABLE_NAME, station.getId()));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorUpdate(TABLE_NAME, station.getId()));
        }

        return station;
    }


    private Station getStation(ResultSet set) throws SQLException {
        Station result = new Station();

        result.setId(set.getLong(LABEL_ID));
        result.setName(set.getString(LABEL_NAME));

        return result;
    }


}