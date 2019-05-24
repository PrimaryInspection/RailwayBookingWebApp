package model.service;

import model.entity.User;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class LoginServiceTest {

    @Test
    public void testSecurePassword(){
        String password = "root";
        User user = new User();
        user.setPassword(password);

        LoginService.getInstance().securePassword(user);

        assertFalse(user.getPassword().equals(password));
    }

}