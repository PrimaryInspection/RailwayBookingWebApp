package model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Entity to table <b>TRAIN</b>
 *
 * @author Andrii Mishko
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
public class Train implements Serializable{
    private Long id;
    private Long routeId;

    private Long compartmentFree;
    private Long deluxeFree;
    private Long berthFree;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Train)) return false;

        Train train = (Train) o;

        if (!getId().equals(train.getId())) return false;
        if (!getRouteId().equals(train.getRouteId())) return false;
        if (!getCompartmentFree().equals(train.getCompartmentFree())) return false;
        if (!getDeluxeFree().equals(train.getDeluxeFree())) return false;
        return getBerthFree().equals(train.getBerthFree());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getRouteId().hashCode();
        result = 31 * result + getCompartmentFree().hashCode();
        result = 31 * result + getDeluxeFree().hashCode();
        result = 31 * result + getBerthFree().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", routeId=" + routeId +
                ", compartmentFree=" + compartmentFree +
                ", deluxeFree=" + deluxeFree +
                ", berthFree=" + berthFree +
                '}';
    }
}
