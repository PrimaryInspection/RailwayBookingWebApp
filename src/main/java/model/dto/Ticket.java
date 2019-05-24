package model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private Long requestId;
    private Long trainId;
    private Long userId;

    private String name;
    private String surname;

    private String fromDate;
    private String toDate;
    private String fromCity;
    private String toCity;

    private String typePlace;
    private Long max;

    private Double price;
    private Long status;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(String typePlace) {
        this.typePlace = typePlace;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;

        Ticket ticket = (Ticket) o;

        if (!getRequestId().equals(ticket.getRequestId())) return false;
        if (!getTrainId().equals(ticket.getTrainId())) return false;
        if (!getUserId().equals(ticket.getUserId())) return false;
        if (!getName().equals(ticket.getName())) return false;
        if (!getSurname().equals(ticket.getSurname())) return false;
        if (!getFromDate().equals(ticket.getFromDate())) return false;
        if (!getToDate().equals(ticket.getToDate())) return false;
        if (!getFromCity().equals(ticket.getFromCity())) return false;
        if (!getToCity().equals(ticket.getToCity())) return false;
        if (!getTypePlace().equals(ticket.getTypePlace())) return false;
        if (!getMax().equals(ticket.getMax())) return false;
        if (!getPrice().equals(ticket.getPrice())) return false;
        return getStatus().equals(ticket.getStatus());
    }

    @Override
    public int hashCode() {
        int result = getRequestId().hashCode();
        result = 31 * result + getTrainId().hashCode();
        result = 31 * result + getUserId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getFromDate().hashCode();
        result = 31 * result + getToDate().hashCode();
        result = 31 * result + getFromCity().hashCode();
        result = 31 * result + getToCity().hashCode();
        result = 31 * result + getTypePlace().hashCode();
        result = 31 * result + getMax().hashCode();
        result = 31 * result + getPrice().hashCode();
        result = 31 * result + getStatus().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "requestId=" + requestId +
                ", trainId=" + trainId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", fromCity='" + fromCity + '\'' +
                ", toCity='" + toCity + '\'' +
                ", typePlace='" + typePlace + '\'' +
                ", max=" + max +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
