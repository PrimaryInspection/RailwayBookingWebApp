package model.service;

import model.dao.mysql.mapper.AbstractDAOFactory;
import model.dao.mysql.mapper.DAOFactory;
import model.dao.mysql.mapper.DataBase;
import model.entity.Route;
import model.entity.Station;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class StationService {
    private static final Logger LOG = LogManager.getLogger(StationService.class);
    private static final DataBase DB = DataBase.MYSQL;
    private static volatile StationService INSTANCE;

    private DAOFactory factory;

    private StationService(){
        factory = AbstractDAOFactory.createDAOFactory(DB);
    }

    public static StationService getInstance(){
        if(INSTANCE == null){
            synchronized (StationService.class){
                if (INSTANCE == null){
                    INSTANCE = new StationService();
                }
            }
        }

        return INSTANCE;
    }

    Station findFromStation(Route route){
        return factory.createStationDAO().findById(route.getFromId());
    }

    Station findToStation(Route route){
        return factory.createStationDAO().findById(route.getToId());
    }
}
