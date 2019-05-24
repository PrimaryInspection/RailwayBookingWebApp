package model.dao.mysql.mapper;

import model.dao.mysql.impl.MySQLFactory;

public abstract class AbstractDAOFactory {
    public static DAOFactory createDAOFactory(DataBase dataBase){
        DAOFactory factory = null;
        switch (dataBase){
            case MYSQL: factory = new MySQLFactory(); break;
        }

        return factory;
    }
}
