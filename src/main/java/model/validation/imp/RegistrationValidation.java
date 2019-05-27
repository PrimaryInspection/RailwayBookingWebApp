package model.validation.imp;

import controller.util.RegexManager;
import model.validation.IRegistationValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class RegistrationValidation implements IRegistationValidation {

    private static final String PHONE_REG_EXP = "(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){10,12}(\\s*)?";
    private static RegistrationValidation instance = new RegistrationValidation();

    public RegistrationValidation() {
    }

    public static RegistrationValidation getInstance() {
        return instance;
    }


    @Override
    public boolean isFirsNameUAValid(String firstName) {
        if (firstName == null){return false;}
        Pattern p = Pattern.compile(RegexManager.getProperty("uname.ukr"), CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher m = p.matcher(firstName);
        return m.matches();
    }

    @Override
    public boolean isFirsNameENValid(String firstName) {
        if (firstName == null){return false;}
        Pattern p = Pattern.compile(RegexManager.getProperty("uname.en"), CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher m = p.matcher(firstName);
        return m.matches();
    }

    @Override
    public boolean isSecondNameUAValid(String secondName) {
        if (secondName == null){return false;}
        Pattern p = Pattern.compile(RegexManager.getProperty("uname.ukr"), CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher m = p.matcher(secondName);
        return m.matches();
    }

    @Override
    public boolean isSecondNameENValid(String secondName) {
        if (secondName == null){return false;}
        Pattern p = Pattern.compile(RegexManager.getProperty("uname.en"), CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher m = p.matcher(secondName);
        return m.matches();
    }


    @Override
    public boolean isPasswordValid(String password) {
        if (password == null){return false;}
        Pattern p = Pattern.compile(RegexManager.getProperty("upassword"), CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    @Override
    public boolean isEmailValid(String email) {
        if (email == null){return false;}
        Pattern p = Pattern.compile(RegexManager.getProperty("uemail"), CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public boolean isPhoneValid(String phone) {
        if (phone == null){return false;}
        Pattern p = Pattern.compile(PHONE_REG_EXP);
        Matcher m = p.matcher(phone);
        return m.matches();
    }


}
