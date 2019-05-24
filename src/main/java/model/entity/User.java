package model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Entity to table <b>USER</b>
 *
 * @author Andrii Mishko
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private Long id;
    private String name;
    private String surname;
    private String phone;

    private String email;
    private String password;

    private Boolean admin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public boolean isAdmin(){
        return admin;
    }

    public void makeAdmin(){
        admin = true;
    }

    public void makeUser(){
        admin = false;
    }

    public static class Builder{
        private User user;

        public Builder(){
            user = new User();
        }

        public Builder setName(String name){
            user.setName(name);
            return this;
        }

        public Builder setSurname(String surname){
            user.setSurname(surname);
            return this;
        }

        public Builder setPhone(String phone){
            user.setPhone(phone);
            return this;
        }

        public Builder setEmail(String email){
            user.setEmail(email);
            return this;
        }

        public Builder setPassword(String password){
            user.setPassword(password);
            return this;
        }

        public Builder isAdmin(boolean admin){
            user.setAdmin(admin);
            return this;
        }

        public User build(){
            return user;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!getId().equals(user.getId())) return false;
        if (!getName().equals(user.getName())) return false;
        if (!getSurname().equals(user.getSurname())) return false;
        if (!getPhone().equals(user.getPhone())) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        return getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getPhone().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }
}
