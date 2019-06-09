package model.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrainRoute {
    private Long trainId;
    private Long routeId;

    private Long compartmentFree;
    private Long deluxeFree;
    private Long berthFree;

    private String fromDate;
    private String toDate;

    private String fromCity;
    private String toCity;

    private Double compartmentPrice;
    private Double deluxePrice;
    private Double berthPrice;

    private Double distance;

    public TrainRoute(long l, long l1, long l2, long l3, long l4, String test, String test1, String test2, String test3, double v, double v1, double v2, double v3) {
    }

    public TrainRoute() {

    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getCompartmentFree() {
        return compartmentFree;
    }

    public void setCompartmentFree(Long compartmentFree) {
        this.compartmentFree = compartmentFree;
    }

    public Long getDeluxeFree() {
        return deluxeFree;
    }

    public void setDeluxeFree(Long deluxeFree) {
        this.deluxeFree = deluxeFree;
    }

    public Long getBerthFree() {
        return berthFree;
    }

    public void setBerthFree(Long berthFree) {
        this.berthFree = berthFree;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public Double getCompartmentPrice() {
        return compartmentPrice;
    }

    public void setCompartmentPrice(Double compartmentPrice) {
        this.compartmentPrice = compartmentPrice;
    }

    public Double getDeluxePrice() {
        return deluxePrice;
    }

    public void setDeluxePrice(Double deluxePrice) {
        this.deluxePrice = deluxePrice;
    }

    public Double getBerthPrice() {
        return berthPrice;
    }

    public void setBerthPrice(Double berthPrice) {
        this.berthPrice = berthPrice;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainRoute)) return false;

        TrainRoute that = (TrainRoute) o;

        if (!getTrainId().equals(that.getTrainId())) return false;
        if (!getRouteId().equals(that.getRouteId())) return false;
        if (!getCompartmentFree().equals(that.getCompartmentFree())) return false;
        if (!getDeluxeFree().equals(that.getDeluxeFree())) return false;
        if (!getBerthFree().equals(that.getBerthFree())) return false;
        if (!getFromDate().equals(that.getFromDate())) return false;
        if (!getToDate().equals(that.getToDate())) return false;
        if (!getFromCity().equals(that.getFromCity())) return false;
        if (!getToCity().equals(that.getToCity())) return false;
        if (!getCompartmentPrice().equals(that.getCompartmentPrice())) return false;
        if (!getDeluxePrice().equals(that.getDeluxePrice())) return false;
        if (!getBerthPrice().equals(that.getBerthPrice())) return false;
        return getDistance().equals(that.getDistance());
    }

    @Override
    public int hashCode() {
        int result = getTrainId().hashCode();
        result = 31 * result + getRouteId().hashCode();
        result = 31 * result + getCompartmentFree().hashCode();
        result = 31 * result + getDeluxeFree().hashCode();
        result = 31 * result + getBerthFree().hashCode();
        result = 31 * result + getFromDate().hashCode();
        result = 31 * result + getToDate().hashCode();
        result = 31 * result + getFromCity().hashCode();
        result = 31 * result + getToCity().hashCode();
        result = 31 * result + getCompartmentPrice().hashCode();
        result = 31 * result + getDeluxePrice().hashCode();
        result = 31 * result + getBerthPrice().hashCode();
        result = 31 * result + getDistance().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TrainRoute{" +
                "trainId=" + trainId +
                ", routeId=" + routeId +
                ", compartmentFree=" + compartmentFree +
                ", deluxeFree=" + deluxeFree +
                ", berthFree=" + berthFree +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", fromCity='" + fromCity + '\'' +
                ", toCity='" + toCity + '\'' +
                ", compartmentPrice=" + compartmentPrice +
                ", deluxePrice=" + deluxePrice +
                ", berthPrice=" + berthPrice +
                ", distance=" + distance +
                '}';
    }
}
