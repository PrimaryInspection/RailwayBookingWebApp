package model.dao.mysql.mapper;

import model.entity.Route;

import java.util.List;

/**
 * Interface to Data Access Object for <b>ROUTE</b>
 * Provides CRUD operations
 * @author Andrii Mishko
 * @version 1.0
 */
public interface RouteDAO {

    /**
     * Find all ROUTES in DB
     *
     */
    List<Route> findAll();

    /**
     * Find ROUTE by ID
     *
     */
    Route findById(Long id);

    /**
     * Find ROUTES by given ID of STATION
     *
     */
    List<Route> findByFromId(Long id);

    /**
     * Insert new ROUTE
     *
     */
    Route create(Route route);

    /**
     * Update ROUTE
     *
     */
    Route update(Route route);

}
