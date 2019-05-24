package model.entity;

import java.io.Serializable;

/**
 * Entity to table <b>ROUTE</b>
 *
 * @author Andrii Mishko
 * @version 1.0
 */

public class Route implements Serializable{
    private Long id;
    private Long priceId;

    private Long fromId;
    private Long toId;

    private String fromTime;
    private String toTime;

    private Double distance;

    public Route(Long id, Long priceId, Long fromId, Long toId, String fromTime, String toTime, Double distance) {
        this.id = id;
        this.priceId = priceId;
        this.fromId = fromId;
        this.toId = toId;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.distance = distance;
    }

    public Route() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
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
        if (!(o instanceof Route)) return false;

        Route route = (Route) o;

        if (!getId().equals(route.getId())) return false;
        if (!getPriceId().equals(route.getPriceId())) return false;
        if (!getFromId().equals(route.getFromId())) return false;
        if (!getToId().equals(route.getToId())) return false;
        if (!getFromTime().equals(route.getFromTime())) return false;
        if (!getToTime().equals(route.getToTime())) return false;
        return getDistance().equals(route.getDistance());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getPriceId().hashCode();
        result = 31 * result + getFromId().hashCode();
        result = 31 * result + getToId().hashCode();
        result = 31 * result + getFromTime().hashCode();
        result = 31 * result + getToTime().hashCode();
        result = 31 * result + getDistance().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", priceId=" + priceId +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", fromTime='" + fromTime + '\'' +
                ", toTime='" + toTime + '\'' +
                ", distance=" + distance +
                '}';
    }
}
