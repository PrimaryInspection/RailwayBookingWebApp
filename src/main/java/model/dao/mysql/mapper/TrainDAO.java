package model.dao.mysql.mapper;

import model.entity.Train;

import java.util.List;

/**
 * Interface to Data Access Object for <b>TRAIN</b>
 * Provides CRUD operations
 * @author Andrii Mishko
 * @version 1.0
 */
public interface TrainDAO {
    /**
     * Find all TRAINS in DB
     *
     */
    List<Train> findAll();

    /**
     * Find TRAINS by given ID of ROUTE
     *
     */
    List<Train> findByRoute(Long route_id);

    /**
     * Find TRAIN by ID
     *
     */
    Train findById(Long id);

    /**
     * Insert new TRAIN
     *
     */
    Train create(Train train);

    /**
     * Update TRAIN
     *
     */
    Train update(Train train);



}
