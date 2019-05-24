package model.dao.mysql.mapper;

import model.entity.Station;

import java.util.List;

/**
 * Interface to Data Access Object for <b>STATION</b>
 * Provides CRUD operations
 * @author Andrii Mishko
 * @version 1.0
 */
public interface StationDAO {
    /**
     * Find all STATIONS in DB
     *
     */
    List<Station> findAll();

    /**
     * Find STATION by ID
     *
     */
    Station findById(Long id);

    /**
     * Insert new STATION
     *
     */
    Station create(Station station);

    /**
     * Update STATION
     *
     */
    Station update(Station station);


}
