package com.javalab.marketing.dao;

import com.javalab.marketing.models.User;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class CustomUserDetailsTest {

    @Test
    public void testCreate() throws SQLException, ClassNotFoundException {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        Assert.assertNotNull(customUserDetails.create());
    }

    @Test
    public void testLoadByUsername() throws SQLException, ClassNotFoundException {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        UserManager userManager = new UserManager();
        userManager.create("test","test","test","test");
        User user = (User) customUserDetails.loadUserByUsername("test");
        userManager.deleteUser(user.getUsername());
        Assert.assertEquals("test",user.getUsername());
    }
}
