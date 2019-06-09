package model.dao.mysql.impl;

import controller.util.QueryManager;
import model.dao.mysql.connection.ConnectionPool;
import model.dao.mysql.mapper.PriceDAO;
import model.dao.mysql.util.LogMessageDAOUtil;
import model.entity.Price;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class MySQLPriceDAO implements PriceDAO {
    private static final Logger LOG = LogManager.getLogger(MySQLPriceDAO.class);
    private static volatile MySQLPriceDAO INSTANCE = new MySQLPriceDAO();
    private static final String TABLE_NAME = "price";
    private static final String LABEL_ID = "id";
    private static final String LABEL_BERTH_FACTOR = "berthFactor";
    private static final String LABEL_COMPARTMENT_FACTOR = "compartmentFactor";

    private static final String LABEL_DELUXE_FACTOR = "deluxeFactor";
    private String SELECT_ALL_PRICES = QueryManager.getProperty("priceSelectAll");
    private String CREATE_PRICE = QueryManager.getProperty("priceCreate");
    private String UPDATE_PRICE = QueryManager.getProperty("priceUpdate");
    private String SELECT_BY_ID = QueryManager.getProperty("priceSelectById");


    private MySQLPriceDAO() {
    }

    static MySQLPriceDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Price> findAll() {
        List<Price> result = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PRICES, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                result.add(getPrice(set));
            }
            LOG.info(LogMessageDAOUtil.createInfoFindAll(TABLE_NAME));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorFindAll(TABLE_NAME));
        }

        return result;
    }

    @Override
    public Price findById(Long id) {
        Price result = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                result = getPrice(set);
            }
            LOG.info(LogMessageDAOUtil.createInfoFindByParameter(TABLE_NAME, LABEL_ID, id));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorFindByParameter(TABLE_NAME, LABEL_ID, id));
        }


        return result;
    }

    @Override
    public Price create(Price price) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_PRICE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setDouble(1, price.getBerthFactor());
            statement.setDouble(2, price.getCompartmentFactor());
            statement.setDouble(3, price.getDeluxeFactor());
            statement.executeUpdate();

            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                price.setId(set.getLong(1));
            }

            LOG.info(LogMessageDAOUtil.createInfoCreate(TABLE_NAME, price.getId()));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorCreate(TABLE_NAME));
        }

        return price;
    }

    @Override
    public Price update(Price price) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRICE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, price.getBerthFactor());
            statement.setDouble(2, price.getCompartmentFactor());
            statement.setDouble(3, price.getDeluxeFactor());
            statement.setLong(4, price.getId());

            statement.executeUpdate();

            LOG.info(LogMessageDAOUtil.createInfoUpdate(TABLE_NAME, price.getId()));
        } catch (SQLException e) {
            LOG.error(LogMessageDAOUtil.createErrorUpdate(TABLE_NAME, price.getId()));
        }

        return price;
    }


    private Price getPrice(ResultSet set) throws SQLException {
        Price result = new Price();
        result.setId(set.getLong(LABEL_ID));
        result.setBerthFactor(set.getDouble(LABEL_BERTH_FACTOR));
        result.setCompartmentFactor(set.getDouble(LABEL_COMPARTMENT_FACTOR));
        result.setDeluxeFactor(set.getDouble(LABEL_DELUXE_FACTOR));

        return result;
    }

}