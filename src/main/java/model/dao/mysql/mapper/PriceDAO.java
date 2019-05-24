package model.dao.mysql.mapper;

import model.entity.Price;

import java.util.List;

/**
 * Interface to Data Access Object for <b>PRICE</b>
 * Provides CRUD operations
 * @author Andrii Mishko
 * @version 1.0
 */
public interface PriceDAO {
    /**
     * Find all PRICES in DB
     */
    List<Price> findAll();

    /**
     * Find PRICE by ID
     */
    Price findById(Long id);

    /**
     * Insert new PRICE
     */
    Price create(Price price);

    /**
     * Update PRICE
     */
    Price update(Price price);


}
