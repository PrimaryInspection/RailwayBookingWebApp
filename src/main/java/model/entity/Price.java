package model.entity;

import java.io.Serializable;

/**
 * Entity to table <b>PRICE</b>
 *
 * @author Andrii Mishko
 * @version 1.0
 */

public class Price implements Serializable{
    private Long id;
    private Double compartmentFactor;
    private Double deluxeFactor;
    private Double berthFactor;

    public Price(Double compartmentFactor, Double deluxeFactor, Double berthFactor) {
        this.compartmentFactor = compartmentFactor;
        this.deluxeFactor = deluxeFactor;
        this.berthFactor = berthFactor;
    }

    public Price(Long id, Double compartmentFactor, Double deluxeFactor, Double berthFactor) {
        this.id = id;
        this.compartmentFactor = compartmentFactor;
        this.deluxeFactor = deluxeFactor;
        this.berthFactor = berthFactor;
    }

    public Price() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCompartmentFactor() {
        return compartmentFactor;
    }

    public void setCompartmentFactor(Double compartmentFactor) {
        this.compartmentFactor = compartmentFactor;
    }

    public Double getDeluxeFactor() {
        return deluxeFactor;
    }

    public void setDeluxeFactor(Double deluxeFactor) {
        this.deluxeFactor = deluxeFactor;
    }

    public Double getBerthFactor() {
        return berthFactor;
    }

    public void setBerthFactor(Double berthFactor) {
        this.berthFactor = berthFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;

        Price price = (Price) o;

        if (!getId().equals(price.getId())) return false;
        if (!getCompartmentFactor().equals(price.getCompartmentFactor())) return false;
        if (!getDeluxeFactor().equals(price.getDeluxeFactor())) return false;
        return getBerthFactor().equals(price.getBerthFactor());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getCompartmentFactor().hashCode();
        result = 31 * result + getDeluxeFactor().hashCode();
        result = 31 * result + getBerthFactor().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", compartmentFactor=" + compartmentFactor +
                ", deluxeFactor=" + deluxeFactor +
                ", berthFactor=" + berthFactor +
                '}';
    }
}
