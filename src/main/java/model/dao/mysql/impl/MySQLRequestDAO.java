package model.dao.mysql.impl;

import controller.util.QueryManager;
import model.dao.mysql.connection.ConnectionPool;
import model.dao.mysql.mapper.RequestDAO;
import model.dao.mysql.mapper.TypePlace;
import model.dao.mysql.util.LogMessageDAOUtil;
import model.dao.mysql.util.QueryDAOUtil;
import model.entity.Request;
import model.entity.Train;
import model.exception.InvalidDataBaseOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class MySQLRequestDAO implements RequestDAO {
    private static final Logger LOG = LogManager.getLogger(MySQLRequestDAO.class);
    private static final MySQLRequestDAO INSTANCE = new MySQLRequestDAO();

    private static final String TABLE_NAME = "request";
    private static final String LABEL_ID = "id";
    private static final String LABEL_USER_ID = "userId";
    private static final String LABEL_TRAIN_ID = "trainId";
    private static final String LABEL_TYPE = "type";
    private static final String LABEL_PRICE = "price";
    private static final String LABEL_STATUS = "status";
    private String SELECT_ALL_REQUESTS = QueryManager.getProperty("requestSelectAll");
    private String CREATE_REQUEST = QueryManager.getProperty("requestCreate");
    private String UPDATE_REQUEST = QueryManager.getProperty("requestUpdate");
    private String CANCEL_REQUEST = QueryManager.getProperty("requestCancel");
    private String REQUEST_APPROVE = QueryManager.getProperty("requestApprove");


    private MySQLRequestDAO() {
    }

    static MySQLRequestDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Request> findAll() {
        List<Request> result = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REQUESTS, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                result.add(getRequest(set));
            }
            LOG.info(LogMessageDAOUtil.createInfoFindAll(TABLE_NAME));
        } catch (SQLException e) {
            e.printStackTrace();

            LOG.error(LogMessageDAOUtil.createErrorFindAll(TABLE_NAME));
        }

        return result;
    }

    @Override
    public Request findById(Long id) {
        List<Request> result = findByParameter(id, LABEL_ID);
        if (result.size() != 1)
            return null;

        return result.get(0);
    }

    @Override
    public Request create(Request request) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, request.getUserId());
            statement.setLong(2, request.getTrainId());
            statement.setString(3, request.getType().toString());
            statement.setDouble(4, request.getPrice());
            statement.setLong(5, request.getStatus());

            statement.executeUpdate();

            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                request.setId(set.getLong(1));
            }

            LOG.info(LogMessageDAOUtil.createInfoCreate(TABLE_NAME, request.getId()));
        } catch (SQLException e) {
            e.printStackTrace();

            LOG.error(LogMessageDAOUtil.createErrorCreate(TABLE_NAME));
        }

        return request;
    }

    @Override
    public Request update(Request request) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();

             PreparedStatement statement = connection.prepareStatement(UPDATE_REQUEST, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, request.getId());
            statement.setLong(2, request.getUserId());
            statement.setLong(3, request.getTrainId());
            statement.setString(4, request.getType().toString());

            statement.setDouble(5, request.getPrice());
            statement.setLong(6, request.getStatus());


            statement.executeUpdate();

            LOG.info(LogMessageDAOUtil.createInfoUpdate(TABLE_NAME, request.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error(LogMessageDAOUtil.createErrorUpdate(TABLE_NAME, request.getId()));
        }

        return request;
    }


    //TODO REWORK THIS METHOD
    @Override
    public void deleteRequests(final List<Request> requests) throws InvalidDataBaseOperation {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            for (Request request : requests) {
                switch (request.getType()) {
                    case C: {
                        Train train = MySQLTrainDAO.getInstance().findById(request.getTrainId());
                        train.setCompartmentFree(train.getCompartmentFree() + 1);
                        MySQLTrainDAO.getInstance().update(train);
                        break;
                    }
                    case L: {
                        Train train = MySQLTrainDAO.getInstance().findById(request.getTrainId());
                        train.setDeluxeFree(train.getDeluxeFree() + 1);
                        MySQLTrainDAO.getInstance().update(train);
                        break;
                    }
                    case B: {
                        Train train = MySQLTrainDAO.getInstance().findById(request.getTrainId());
                        train.setBerthFree(train.getBerthFree() + 1);
                        MySQLTrainDAO.getInstance().update(train);
                        break;
                    }
                }
                statement = connection.prepareStatement(CANCEL_REQUEST);

                statement.setLong(1, request.getUserId());
                statement.setLong(2, request.getTrainId());
                statement.setString(3, request.getType().toString());
                statement.setDouble(4, request.getPrice());


                statement.setLong(5, 1);

                statement.setLong(6, request.getId());

                statement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                LOG.error("something wrong with transaction");
                connection.rollback();
            } catch (SQLException e1) {
                LOG.error("something wrong with rollback");
                e1.printStackTrace();
            }
        }
    }

    //TODO REWORK THIS METHOD
    @Override
    public void approveRequests(final List<Request> requests) throws InvalidDataBaseOperation {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            for (Request request : requests) {
                statement = connection.prepareStatement(REQUEST_APPROVE);

                statement.setLong(1, request.getUserId());
                statement.setLong(2, request.getTrainId());
                statement.setString(3, request.getType().toString());
                statement.setDouble(4, request.getPrice());
                if (findById(request.getId()).getStatus() == 2) {
                    throw new InvalidDataBaseOperation("Already approved " + request.getId());
                }
                statement.setLong(5, 2);

                statement.setLong(6, request.getId());


                statement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                e.printStackTrace();

                LOG.error("something wrong with transaction");
                connection.rollback();
            } catch (SQLException e1) {
                LOG.error("something wrong with rollback");
                e1.printStackTrace();
            }
        }
    }

    private List<Request> findByParameter(Long id, String parameterLabel) {
        List<Request> result = new ArrayList<>();
        String findByIdQuery = QueryDAOUtil.createFindByParameterQuery(TABLE_NAME, parameterLabel);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(findByIdQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                result.add(getRequest(set));
            }
            LOG.info(LogMessageDAOUtil.createInfoFindByParameter(TABLE_NAME, parameterLabel, id));
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error(LogMessageDAOUtil.createErrorFindByParameter(TABLE_NAME, parameterLabel, id));
        }

        return result;
    }


    private Request getRequest(ResultSet set) throws SQLException {
        Request request = new Request();
        request.setId(set.getLong(LABEL_ID));
        request.setTrainId(set.getLong(LABEL_TRAIN_ID));
        request.setUserId(set.getLong(LABEL_USER_ID));
        request.setType(TypePlace.valueOf(set.getString(LABEL_TYPE)));
        request.setPrice(set.getDouble(LABEL_PRICE));
        request.setStatus(set.getLong(LABEL_STATUS));
        return request;
    }


}