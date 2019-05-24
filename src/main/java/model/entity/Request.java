package model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.dao.mysql.mapper.TypePlace;

import java.io.Serializable;

/**
 * Entity to table <b>REQUEST</b>
 *
 * @author Andrii Mishko
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Serializable {
    private Long id;
    private Long userId;
    private Long trainId;

    private TypePlace type;

    private Double price;

    private Long status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public TypePlace getType() {
        return type;
    }

    public void setType(TypePlace type) {
        this.type = type;
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


    public static class RequestBuilder{
        private Request request;

        public RequestBuilder(){
            request = new Request();
        }

        public RequestBuilder setUserId(Long id){
            request.setUserId(id);
            return this;
        }

        public RequestBuilder setTrainId(Long id){
            request.setTrainId(id);
            return this;
        }

        public RequestBuilder setPrice(Double price){
            request.setPrice(price);
            return this;
        }

        public RequestBuilder setType(TypePlace type){
            request.setType(type);
            return this;
        }

        public RequestBuilder setStatus(Long status){
            request.setStatus(status);
            return this;
        }

        public Request build(){
            return request;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;

        Request request = (Request) o;

        if (!getId().equals(request.getId())) return false;
        if (!getUserId().equals(request.getUserId())) return false;
        if (!getTrainId().equals(request.getTrainId())) return false;
        if (getType() != request.getType()) return false;
        if (!getPrice().equals(request.getPrice())) return false;
        return getStatus().equals(request.getStatus());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getUserId().hashCode();
        result = 31 * result + getTrainId().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getPrice().hashCode();
        result = 31 * result + getStatus().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", userId=" + userId +
                ", trainId=" + trainId +
                ", type=" + type +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
