package com.javalab.marketing.dao;

import com.javalab.marketing.models.User;
import com.javalab.marketing.services.ConnectionPool;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManagerTest {
    @Test
    public void testCreateUser() throws SQLException, ClassNotFoundException {
        UserManager userManager = new UserManager();
        userManager.create("test","test","test","ROLE_TEST");
        Assert.assertNotNull(userManager.getUser("test"));
        userManager.deleteUser("test");
    }

    @Test
    public void testRemoveUser() throws SQLException, ClassNotFoundException {
        UserManager userManager = new UserManager();
        boolean flag = false;
        userManager.create("test","test","test","ROLE_TEST");
        User user = userManager.getUser("test");
        if(user.getEmail().equals("test")){
            flag=true;
        }
        userManager.deleteUser("test");
        boolean flag2 = true;
        List<User> users = userManager.getAll();
        for(User u:users){
            if (u.getUsername().equals("test")){
                flag2=false;
            }
        }
        Assert.assertTrue(flag&&flag2);
    }

    @Test
    public void testGetAll() throws SQLException, ClassNotFoundException {
        UserManager userManager = new UserManager();
        ArrayList<User> users = (ArrayList<User>) userManager.getAll();
        Connection con = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(
                "select " +
                        "   COUNT(id) " +
                        "from " +
                        "   users "
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Assert.assertEquals(users.size(),resultSet.getInt("COUNT(id)"));
    }

    @Test
    public void testGetUserByUsername() throws SQLException, ClassNotFoundException {
        UserManager userManager = new UserManager();
        userManager.create("test","test","test","ROLE_TEST");
        User user=userManager.getUser("test");
        userManager.deleteUser("test");
        Assert.assertEquals("test",user.getUsername());
    }

    @Test
    public void testGetgetAuthorities() throws SQLException, ClassNotFoundException {
        UserManager userManager = new UserManager();
        User user = new User("test","test","test","ROLE_TEST");
        userManager.create(user.getEmail(),user.getUsername(),user.getPassword(),"ROLE_TEST");
        String authorities=userManager.getAuthorities(user);
        userManager.deleteUser("test");
        Assert.assertEquals("ROLE_TEST;",authorities);
    }

    @Test
    public void testBusyUsername() throws SQLException, ClassNotFoundException {
        boolean flag=true;
        UserManager userManager = new UserManager();
        userManager.create("test","test","test","ROLE_TEST");
        flag=userManager.busyUsername("test");
        userManager.deleteUser("test");
        Assert.assertFalse(flag);
    }
}
