package model.dao.mysql.mapper;

import model.entity.User;

import java.util.List;

/**
 * Interface to Data Access Object for <b>USER</b>
 * Provides CRUD operations
 * @author Andrii Mishko
 * @version 1.0
 */
public interface UserDAO {
    /**
     * Find all USERS in DB
     *
     */
    List<User> findAll();
    List<User> findAll(int limit, int offset);
    int getUsersCount();

    /**
     * Find USER by ID
     *
     */
    User findById(Long id);

    /**
     * Find USER by login
     *
     */
    User findByEmail(String login);

    /**
     * Insert new USER
     *
     */
    User create(User user);

    /**
     * Update USER
     *
     */
    User update(User user);

    /**
     * Delete USER
     *
     */
    void delete(User user);
}
