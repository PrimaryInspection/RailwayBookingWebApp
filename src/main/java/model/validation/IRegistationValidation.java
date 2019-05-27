package model.validation;

public interface IRegistationValidation {

    boolean isFirsNameUAValid(String firstName);
    boolean isFirsNameENValid(String firstName);

    boolean isSecondNameUAValid(String secondName);
    boolean isSecondNameENValid(String secondName);

    boolean isPasswordValid(String password);

    boolean isEmailValid(String email);

    boolean isPhoneValid(String phone);


}
