package model.service;


import model.dao.mysql.mapper.AbstractDAOFactory;
import model.dao.mysql.mapper.DAOFactory;
import model.dao.mysql.mapper.DataBase;
import model.entity.Price;
import model.entity.Route;
import model.entity.Station;
import model.entity.Train;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RouteService {
    private static final Logger LOG = LogManager.getLogger(RouteService.class);
    private static final DataBase DB = DataBase.MYSQL;
    private static volatile RouteService INSTANCE;

    private DAOFactory factory;

    private RouteService(){
        factory = AbstractDAOFactory.createDAOFactory(DB);
    }

    public static RouteService getInstance(){
        if(INSTANCE == null){
            synchronized (RouteService.class){
                if (INSTANCE == null){
                    INSTANCE = new RouteService();
                }
            }
        }

        return INSTANCE;
    }

    Route findRouteById(Long id){
        return factory.createRouteDAO().findById(id);
    }

    Route findRouteByTrain(Train train){
        return factory.createRouteDAO().findById(train.getRouteId());
    }

    public List<Station> findAvailableFromStations(){
        List<Route> routes = factory.createRouteDAO().findAll();
        Set<Station> stations = new HashSet<>();

        for (Route route: routes){
            Station station = factory.createStationDAO().findById(route.getFromId());
            stations.add(station);
        }

        List<Station> result = new ArrayList<>(stations);
        result.sort(new Comparator<Station>() {
            @Override
            public int compare(Station o1, Station o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        LOG.info("Find All Available FROM Stations");
        return result;
    }

    public List<Station> findAvailableToStations(){
        List<Route> routes = factory.createRouteDAO().findAll();
        Set<Station> stations = new HashSet<>();

        for (Route route: routes){
            Station station = factory.createStationDAO().findById(route.getToId());
            stations.add(station);
        }

        List<Station> result = new ArrayList<>(stations);
        result.sort(new Comparator<Station>() {
            @Override
            public int compare(Station o1, Station o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        LOG.info("Find All Available TO Stations");
        return result;
    }

    List<Route> findRouteByStations(Station from, Station to){
        List<Route> routes = factory.createRouteDAO().findByFromId(from.getId());
        List<Route> result = new ArrayList<>();

        for(Route route: routes){
            if (route.getToId().equals(to.getId())){
                result.add(route);
            }
        }

        LOG.info(String.format("Find Route by Stations: FROM %s --- TO %s", from.getName(), to.getName()));
        return result;
    }

    public List<Route> findRoutesFromTime(List<Route> routes, Date date){
        long time = date.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        List<Route> result = new ArrayList<>();
        try {
            for (Route route : routes) {
                if (format.parse(route.getFromTime()).getTime() > time) {
                    result.add(route);
                }
            }
        }catch (ParseException e){
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        LOG.info("Find Routes from TIME - " + date);
        return result;
    }

    Double findCompartmentPrice(Route route){
        Price compartment = factory.createPriceDAO().findById(route.getPriceId());
        return compartment.getCompartmentFactor() * route.getDistance();
    }


    Double findBerthPrice(Route route){
        Price berth = factory.createPriceDAO().findById(route.getPriceId());
        return berth.getBerthFactor() * route.getDistance();
    }


    Double findDeluxePrice(Route route){
        Price deluxe = factory.createPriceDAO().findById(route.getPriceId());
        return deluxe.getDeluxeFactor() * route.getDistance();
    }
}
