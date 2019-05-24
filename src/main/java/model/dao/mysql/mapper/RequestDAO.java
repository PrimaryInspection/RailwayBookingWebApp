package model.dao.mysql.mapper;

import model.entity.Request;
import model.exception.InvalidDataBaseOperation;

import java.util.List;

/**
 * Interface to Data Access Object for <b>REQUEST</b>
 * Provides CRUD operations
 * @author Andrii Mishko
 * @version 1.0
 */
public interface RequestDAO {
    /**
     * Find all REQUESTS in DB
     */
    List<Request> findAll();

    /**
     * Find REQUEST by ID
     */
    Request findById(Long id);

    /**
     * Insert new REQUEST
     */
    Request create(Request request);

    /**
     * Update REQUEST
     */
    Request update(Request request);




    void deleteRequests(List<Request> requests) throws InvalidDataBaseOperation;
    void approveRequests(List<Request> requests) throws InvalidDataBaseOperation;

}
